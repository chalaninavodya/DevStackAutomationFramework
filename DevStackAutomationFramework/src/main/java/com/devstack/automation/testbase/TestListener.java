package com.devstack.automation.testbase;

import com.devstack.automation.reporter.ExtentReportManager;
import com.devstack.automation.utils.ThreadLocalWebDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITest;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    @Override
    public void onStart(ITestContext context) {
        ExtentReportManager.initReport();
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentReportManager.flushReport();
    }

    @Override
    public void onTestStart(ITestResult result) {

        String testName;

        // ✅ Get dynamic test name from ITest (Excel data)
        if (result.getInstance() instanceof ITest) {
            testName = ((ITest) result.getInstance()).getTestName();
        } else {
            testName = result.getMethod().getMethodName();
        }

        // ✅ Safety fallback
        if (testName == null || testName.trim().isEmpty()) {
            testName = result.getMethod().getMethodName();
        }

        ExtentReportManager.createTest(testName);
        ExtentReportManager.logInfo("Test Started: " + testName);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentReportManager.logPass("Test Passed: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {

        WebDriver driver = ThreadLocalWebDriverManager.getDriver();

        String base64Screenshot = "";

        try {
            if (driver != null) {
                base64Screenshot = ((TakesScreenshot) driver)
                        .getScreenshotAs(OutputType.BASE64);
            }
        } catch (Exception e) {
            ExtentReportManager.logFail("Screenshot capture failed");
        }

        String testName = result.getMethod().getMethodName();

        ExtentReportManager.logFailWithScreenShot(
                "Test Failed: " + testName +
                        "\nReason: " + result.getThrowable(),
                base64Screenshot
        );
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentReportManager.logSkip(
                "Test Skipped: " + result.getMethod().getMethodName()
        );
    }
}