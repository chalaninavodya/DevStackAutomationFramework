package com.devstack.automation.pages.commons;

import com.devstack.automation.testbase.SeleniumTestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage extends SeleniumTestBase {

    // Locators
    private By tf_email = By.id("email");
    private By tf_password = By.id("password");
    private By btn_login = By.xpath("//button[text()='Sign In with Email']");
    private By lbl_error = By.xpath("//div[text()='Invalid email or password' and contains(@class,'text-sm')]");
    private By dashboard_main = By.id("dashboardMain"); // Replace with actual dashboard locator

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // Fill login form
    public void fillEmail(String email) {
        driver.findElement(tf_email).clear();
        driver.findElement(tf_email).sendKeys(email);
    }

    public void fillPassword(String password) {
        driver.findElement(tf_password).clear();
        driver.findElement(tf_password).sendKeys(password);
    }

    // Click login button
    public void clickLoginButton() {
        driver.findElement(btn_login).click();
    }

    // Submit form (for browser validation)
    public void submitLoginForm() {
        driver.findElement(tf_email).submit();
    }

    // Get HTML5 browser validation message
    public String getEmailValidationMessage() {
        return driver.findElement(tf_email).getAttribute("validationMessage");
    }

    // Get system error message
    public String getErrorMessage() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(lbl_error));
            return errorMsg.getText();
        } catch (Exception e) {
            return null;
        }
    }

    // Check if dashboard is loaded
    public boolean isDashboardLoaded() {
        try {
            return driver.findElement(dashboard_main).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}