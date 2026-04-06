package com.devstack.automation.functions.commons;

import com.devstack.automation.functions.FunctionBase;
import com.devstack.automation.pages.commons.LoginPage;
import com.devstack.automation.reporter.ExtentReportManager;
import org.openqa.selenium.WebDriver;

public class LIB_Common extends FunctionBase {

    private LoginPage loginPage;

    public LIB_Common(WebDriver driver) {
        super(driver);
        this.loginPage = new LoginPage(driver);
    }

    public void tc_login(String email, String password) {

        System.out.println("Before login URL: " + driver.getCurrentUrl());
        ExtentReportManager.writeToReport("Start of tc_login");

        // Fill form
        loginPage.fillEmail(email);
        loginPage.fillPassword(password);

        // Click login
        loginPage.clickLoginButton();

        // Wait small delay (optional but useful)
        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}

        System.out.println("After login URL: " + driver.getCurrentUrl());

        // Check error message
        String errorMsg = loginPage.getErrorMessage();
        if (!errorMsg.isEmpty()) {
            ExtentReportManager.writeToReport("Login failed: " + errorMsg);
        } else {
            ExtentReportManager.writeToReport("No error message displayed");
        }

        // Email validation
        String emailValidation = loginPage.getEmailValidationMessage();
        if (!emailValidation.isEmpty()) {
            ExtentReportManager.writeToReport("Email validation: " + emailValidation);
        }

        // Dashboard check
        if (loginPage.isDashboardLoaded()) {
            ExtentReportManager.writeToReport("Login SUCCESS - Dashboard loaded");
        }

        ExtentReportManager.writeToReport("End of tc_login");
    }
}