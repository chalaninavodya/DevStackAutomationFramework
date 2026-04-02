package com.devstack.automation.functions.commons;

import com.devstack.automation.functions.FunctionBase;
import com.devstack.automation.pages.commons.LoginPage;
import com.devstack.automation.reporter.ExtentReportManager;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class LIB_Common extends FunctionBase {
    private LoginPage loginPage;

    public LIB_Common(WebDriver driver) {
        super(driver);
        this.loginPage = new LoginPage(driver);
    }

    /**
     * Performs login with given credentials and validates the flow.
     * @param email User email
     * @param password User password
     */
    public void tc_login(String email, String password) {
        try {

            System.out.println("Before login click URL: " + driver.getCurrentUrl());

            loginPage.clickLogin();

            System.out.println("After login click URL: " + driver.getCurrentUrl());
            ExtentReportManager.writeToReport("Start of tc_login");

            // Fill login form
            loginPage.fillEmail(email);
            loginPage.fillPassword(password);
            loginPage.clickLogin();


            // Optional validations
            String errorMsg = loginPage.getErrorMessage();
            if (errorMsg != null && !errorMsg.isEmpty()) {
                ExtentReportManager.writeToReport("Login error: " + errorMsg);
            }

            String emailValidation = loginPage.getEmailValidationMessage();
            if (emailValidation != null && !emailValidation.isEmpty()) {
                ExtentReportManager.writeToReport("Email validation message: " + emailValidation);
            }

            boolean dashboardLoaded = loginPage.isDashboardLoaded();
            Assert.assertTrue(dashboardLoaded, "Dashboard did not load after login");

            ExtentReportManager.writeToReport("End of tc_login");
        } catch (Exception e) {
            ExtentReportManager.writeToReport("Exception in tc_login: " + e.getMessage());
            Assert.fail("Login test failed due to exception: " + e.getMessage());
        }
    }
}