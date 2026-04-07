package com.devstack.automation.pages.commons;

import com.devstack.automation.testbase.SeleniumTestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends SeleniumTestBase {

    private By tf_email = By.id("email");
    private By tf_password = By.id("password");
    private By btn_login = By.xpath("//button[text()='Sign In with Email']");
    private By lbl_error = By.xpath("//div[contains(text(),'Invalid email or password')]");
    private By dashboard_main = By.id("dashboardMain");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void fillEmail(String email) {
        driver.findElement(tf_email).clear();
        if (email != null && !email.isEmpty()) {
            driver.findElement(tf_email).sendKeys(email);
        }
    }

    public void fillPassword(String password) {
        driver.findElement(tf_password).clear();
        if (password != null && !password.isEmpty()) {
            driver.findElement(tf_password).sendKeys(password);
        }
    }

    public void clickLoginButton() {
        driver.findElement(btn_login).click();
    }

    // ✅ HTML5 validation trigger
    public void submitLoginForm() {
        driver.findElement(btn_login).submit();
    }

    public String getEmailValidationMessage() {
        try {
            return driver.findElement(tf_email).getAttribute("validationMessage");
        } catch (Exception e) {
            return "";
        }
    }

    public String getErrorMessage() {
        try {
            return driver.findElement(lbl_error).getText();
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isDashboardLoaded() {
        try {
            return driver.findElement(dashboard_main).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}