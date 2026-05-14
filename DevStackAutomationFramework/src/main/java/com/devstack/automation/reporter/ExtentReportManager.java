package com.devstack.automation.reporter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.util.Objects;

public class ExtentReportManager {

    private static ExtentReports extentReports;

    // ✅ Single ThreadLocal enough
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    public static void initReport() {

        extentReports = new ExtentReports();

        ExtentSparkReporter sparkReporter =
                new ExtentSparkReporter("ExtentReports/ExtentReports.html");

        sparkReporter.config().setTheme(Theme.STANDARD);
        sparkReporter.config().setDocumentTitle("Automation Report");
        sparkReporter.config().setReportName("Test Automation Results");

        extentReports.attachReporter(sparkReporter);
    }

    public static void createTest(String testName) {

        if (extentReports == null) {
            initReport();
        }

        ExtentTest test = extentReports.createTest(testName);

        extentTest.set(test);
    }

    public static void logPass(String message) {

        if (Objects.nonNull(extentTest.get())) {

            extentTest.get().log(Status.PASS, message);

        } else {

            System.out.println("PASS: " + message);
        }
    }

    public static void logFail(String message) {

        if (Objects.nonNull(extentTest.get())) {

            extentTest.get().log(Status.FAIL, message);

        } else {

            System.out.println("FAIL: " + message);
        }
    }

    public static void logSkip(String message) {

        if (Objects.nonNull(extentTest.get())) {

            extentTest.get().log(Status.SKIP, message);

        } else {

            System.out.println("SKIP: " + message);
        }
    }

    public static void writeToReport(String message) {

        if (Objects.nonNull(extentTest.get())) {

            extentTest.get().info(message);

        } else {

            System.out.println("INFO: " + message);
        }
    }

    public static void logInfo(String message) {

        if (Objects.nonNull(extentTest.get())) {

            extentTest.get().log(Status.INFO, message);

        } else {

            System.out.println("INFO: " + message);
        }
    }

    public static void logFailWithScreenShot(String message, String base64Screenshot) {

        if (Objects.nonNull(extentTest.get())) {

            extentTest.get().fail(
                    message,
                    MediaEntityBuilder
                            .createScreenCaptureFromBase64String(base64Screenshot)
                            .build()
            );

        } else {

            System.out.println("FAIL WITH SCREENSHOT: " + message);
        }
    }

    public static void flushReport() {

        if (Objects.nonNull(extentReports)) {

            extentReports.flush();
        }
    }
}