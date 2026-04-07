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

        // ✅ ONLY click if both values available
        if (email != null && !email.isEmpty() &&
                password != null && !password.isEmpty()) {

            loginPage.clickLoginButton();
        }

        // Small wait
        try { Thread.sleep(1500); } catch (InterruptedException ignored) {}

        System.out.println("After login URL: " + driver.getCurrentUrl());

        // Logging
        String errorMsg = loginPage.getErrorMessage();
        if (!errorMsg.isEmpty()) {
            ExtentReportManager.writeToReport("Login error: " + errorMsg);
        }

        String validation = loginPage.getEmailValidationMessage();
        if (!validation.isEmpty()) {
            ExtentReportManager.writeToReport("Validation: " + validation);
        }

        if (loginPage.isDashboardLoaded()) {
            ExtentReportManager.writeToReport("Login SUCCESS");
        }

        ExtentReportManager.writeToReport("End of tc_login");
    }
}