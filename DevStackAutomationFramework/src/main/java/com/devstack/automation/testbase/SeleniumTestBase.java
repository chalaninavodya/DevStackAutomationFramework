package com.devstack.automation.testbase;

import com.devstack.automation.reporter.ExtentReportManager;
import com.devstack.automation.utils.ThreadLocalWebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;

public class SeleniumTestBase {

    protected WebDriver driver;
    protected JavascriptExecutor executor;



    public SeleniumTestBase(){
        this.driver = ThreadLocalWebDriverManager.getDriver();
        this.executor = (JavascriptExecutor) this.driver;
    }

    public WebElement waitForElement(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // 🔥 CLICK
    public void click(By locator) {
        try {
            WebElement element = waitForVisibilityOfElement(locator);
            moveToElement(element);
            element.click();
            ExtentReportManager.logPass("Clicked: " + locator);
        } catch (Exception e) {
            ExtentReportManager.logFail("Click failed: " + locator + "\n" + e.getMessage());
        }
    }

    // 🔥 TYPE
    public void type(By locator, String inputText) {
        try {
            WebElement element = waitForVisibilityOfElement(locator);
            moveToElement(element);
            element.clear();
            element.sendKeys(inputText);
            ExtentReportManager.logPass("Typed [" + inputText + "] into: " + locator);
        } catch (Exception e) {
            ExtentReportManager.logFail("Typing failed: " + locator + "\n" + e.getMessage());
        }
    }

    // 🔥 GET TEXT (IMPORTANT 🔥)
    public String getText(By locator) {
        try {
            WebElement element = waitForVisibilityOfElement(locator);
            String text = element.getText();
            ExtentReportManager.logPass("Text captured from: " + locator + " -> " + text);
            return text;
        } catch (Exception e) {
            ExtentReportManager.logFail("GetText failed: " + locator + "\n" + e.getMessage());
            return "";
        }
    }

    // 🔥 WAIT
    public WebElement waitForVisibilityOfElement(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // 🔥 DROPDOWN
    public void selectDropdownByVisibleText(By locator, String text) {
        WebElement element = waitForVisibilityOfElement(locator);
        new Select(element).selectByVisibleText(text);
    }

    // 🔥 MOVE
    public void moveToElement(WebElement element) {
        new Actions(driver).moveToElement(element).perform();
    }

    public void moveToJsElement(WebElement element) {
        executor.executeScript("arguments[0].scrollIntoView(true);", element);
    }


    // 🔥 SAFE ELEMENT CHECK (IMPORTANT)
    public boolean isElementPresent(By locator) {
        try {
            List<WebElement> elements = driver.findElements(locator);
            return elements.size() > 0;
        } catch (Exception e) {
            return false;
        }
    }
}

