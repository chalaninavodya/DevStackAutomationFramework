package com.devstack.automation.pages.commons;

import com.devstack.automation.testbase.SeleniumTestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage extends SeleniumTestBase {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // Locators
    By tf_email = By.xpath("//input[@id='email']");
    By tf_password = By.xpath("//input[@id='password']");
    By btn_login = By.xpath("//button[@type='submit']");

    // ✅ FIXED (flexible XPath)
    By lbl_error = By.xpath("//div[@role='alert']");

    // Actions
    public void fillEmail(String email) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement emailField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("email"))
        );
        emailField.clear();
        emailField.sendKeys(email);
    }

    public void fillPassword(String password) {
        type(tf_password, password);
    }

    public void clickLogin() {
        click(btn_login);
    }

    public void login(String email, String password) {
        fillEmail(email);
        fillPassword(password);
        clickLogin();
    }

    // 🔥 System error (API / backend)
    public String getErrorMessage(){

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement error = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(lbl_error)
            );

            return error.getText();

        } catch (Exception e) {
            return null; // safe fallback
        }
    }

    // 🔥 Browser validation
    public String getEmailValidationMessage() {
        return driver.findElement(tf_email)
                .getAttribute("validationMessage");
    }

    // Success check
    public boolean isDashboardLoaded(){

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            return wait.until(
                    ExpectedConditions.urlContains("dashboard")
            );
        } catch (Exception e) {
            return false;
        }
    }
}