package com.devstack.automation.tests.login;

import com.devstack.automation.functions.commons.LIB_Common;
import com.devstack.automation.model.LoginTestData;
import com.devstack.automation.pages.commons.LoginPage;
import com.devstack.automation.tests.BaseTest;
import com.devstack.automation.utils.ExcelHandler;
import com.devstack.automation.utils.PropertyHandler;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITest;
import org.testng.annotations.Test;

import java.time.Duration;

public class LoginTest extends BaseTest implements ITest {

    private String testName = "Login Test";

    @Test(dataProvider = "commonDataProvider", dataProviderClass = ExcelHandler.class)
    public void loginTest(LoginTestData data) {

        // Dynamic test name
        testName = (data.getTestCaseName() != null && !data.getTestCaseName().isEmpty())
                ? data.getTestCaseName()
                : "Login Test";

        // Open login page
        driver.get(PropertyHandler.getProperty("url"));

        LIB_Common common = new LIB_Common(driver);
        LoginPage loginPage = new LoginPage(driver);

        // Perform login
        common.tc_login(data.getEmail(), data.getPassword());

        String expected = data.getExpectedResult() != null ? data.getExpectedResult() : "";
        String validationType = data.getValidationType() != null ? data.getValidationType() : "";

        if (expected.equalsIgnoreCase("success")) {

            // Wait until either student or admin dashboard is loaded
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            boolean dashboardLoaded = wait.until(d -> {
                String url = d.getCurrentUrl();
                return url.contains("/student/dashboard") || url.contains("/admin/dashboard");
            });

            Assert.assertTrue(dashboardLoaded, "Login should be successful");

        } else {

            // Browser validation check (HTML5)
            if (validationType.equalsIgnoreCase("browser")) {
                loginPage.submitLoginForm();
                String msg = loginPage.getEmailValidationMessage();
                Assert.assertTrue(msg != null && !msg.isEmpty(),
                        "Browser validation message should appear");

            } else {
                // System error message check
                String errorMsg = loginPage.getErrorMessage();
                Assert.assertTrue(errorMsg != null && errorMsg.toLowerCase().contains("invalid"),
                        "System error message should appear");
            }
        }
    }

    @Override
    public String getTestName() {
        return testName;
    }
}