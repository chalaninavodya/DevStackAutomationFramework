package com.devstack.automation.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;

public class ThreadLocalWebDriverManager {
    private static ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();

    private static PropertyHandler propertyHandler = new PropertyHandler();

    public static WebDriver createDriver() {
        String browser = propertyHandler.getProperty("browser");
        WebDriver driver = null;

        if (browser.equalsIgnoreCase("chrome")) {
            driver = WebDriverManager.chromedriver().create();
        }else if (browser.equalsIgnoreCase("firefox")) {
            driver = WebDriverManager.firefoxdriver().create();
        }else if (browser.equalsIgnoreCase("edge")) {
            driver = WebDriverManager.edgedriver().create();
        }else if (browser.equalsIgnoreCase("opera")) {
            driver = WebDriverManager.operadriver().create();
        }else if (browser.equalsIgnoreCase("safari")) {
            driver = WebDriverManager.safaridriver().create();
        }else {
            throw new IllegalArgumentException("Unsupported browser: " + browser);
        }

        threadLocalDriver.set(driver);
        return driver;
    }

    public static WebDriver getDriver() {
        return threadLocalDriver.get();
    }

    public static void removeDriver() {
        WebDriver driver = threadLocalDriver.get();
        if(driver != null) {
            driver.quit();
        }
        threadLocalDriver.remove();
    }
}
