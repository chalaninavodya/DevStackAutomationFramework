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

    // Use ThreadLocal for thread safety if running tests in parallel
    private static ThreadLocal<ExtentTest> testThread = new ThreadLocal<>();

    public static void initReport() {
        extentReports = new ExtentReports();
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("ExtentReports/ExtentReports.html");
        sparkReporter.config().setTheme(Theme.STANDARD);
        sparkReporter.config().setDocumentTitle("Automation Report");
        sparkReporter.config().setReportName("Test Automation Results");
        extentReports.attachReporter(sparkReporter);
    }

    public static void createTest(String testName) {
        ExtentTest test = extentReports.createTest(testName);
        testThread.set(test);
    }

    public static void logPass(String message) {
        if (Objects.nonNull(testThread.get())) {
            testThread.get().log(Status.PASS, message);
        }
    }

    public static void logFail(String message) {
        if (Objects.nonNull(testThread.get())) {
            testThread.get().log(Status.FAIL, message);
        }
    }

    public static void logSkip(String message) {
        if (Objects.nonNull(testThread.get())) {
            testThread.get().log(Status.SKIP, message);
        }
    }

    public static void writeToReport(String message) {
        if (Objects.nonNull(testThread.get())) {
            testThread.get().log(Status.INFO, message);
        }
    }

    // ✅ Added logInfo method
    public static void logInfo(String message) {
        if (Objects.nonNull(testThread.get())) {
            testThread.get().log(Status.INFO, message);
        } else {
            System.out.println("INFO: " + message); // fallback if test is null
        }
    }

    public static void logFailWithScreenShot(String message, String base64Screenshot) {
        if (Objects.nonNull(testThread.get())) {
            testThread.get().fail(message, MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
        }
    }

    public static void flushReport() {
        if (Objects.nonNull(extentReports)) {
            extentReports.flush();
        }
    }
}