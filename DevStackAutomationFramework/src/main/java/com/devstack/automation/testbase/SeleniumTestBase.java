package com.devstack.automation.testbase;

import com.devstack.automation.reporter.ExtentReportManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;
import java.util.List;

public class SeleniumTestBase {

    protected WebDriver driver;
    protected JavascriptExecutor executor;

    // =========================
    // 🚀 SETUP
    // =========================

    @BeforeMethod
    public void setUp() {

        WebDriverManager.chromedriver().setup();

        driver = new ChromeDriver();

        executor = (JavascriptExecutor) driver;

        driver.manage().window().maximize();

        driver.get("https://devstacklms.vercel.app/auth/login");
    }

    // =========================
    // 🧹 TEARDOWN
    // =========================

    @AfterMethod
    public void tearDown() {

        if (driver != null) {

            driver.quit();
        }
    }

    // =========================
    // ⏳ WAIT
    // =========================

    public WebElement waitForVisibilityOfElement(By locator) {

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        );
    }

    // =========================
    // 🖱 CLICK
    // =========================

    public void click(By locator) {

        try {

            WebElement element =
                    waitForVisibilityOfElement(locator);

            new Actions(driver)
                    .moveToElement(element)
                    .perform();

            element.click();

            ExtentReportManager.logPass(
                    "Clicked: " + locator
            );

        } catch (Exception e) {

            ExtentReportManager.logFail(
                    "Click Failed: " + locator
                            + "\n" + e.getMessage()
            );
        }
    }

    // =========================
    // ⌨ TYPE
    // =========================

    public void type(By locator, String inputText) {

        try {

            WebElement element =
                    waitForVisibilityOfElement(locator);

            element.clear();

            element.sendKeys(inputText);

            ExtentReportManager.logPass(
                    "Typed: " + inputText
            );

        } catch (Exception e) {

            ExtentReportManager.logFail(
                    "Typing Failed: "
                            + e.getMessage()
            );
        }
    }

    // =========================
    // 👁 ELEMENT CHECK
    // =========================

    public boolean isElementPresent(By locator) {

        try {

            List<WebElement> elements =
                    driver.findElements(locator);

            return !elements.isEmpty();

        } catch (Exception e) {

            return false;
        }
    }

    // =========================
    // 📄 GET TEXT
    // =========================

    public String getText(By locator) {

        return waitForVisibilityOfElement(locator)
                .getText();
    }
}