package com.devstack.automation.tests.login;

import com.devstack.automation.functions.commons.LIB_Common;
import com.devstack.automation.model.LoginTestData;
import com.devstack.automation.pages.commons.LoginPage;
import com.devstack.automation.tests.BaseTest;
import com.devstack.automation.utils.ExcelHandler;
import com.devstack.automation.utils.PropertyHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITest;
import org.testng.annotations.Test;

import java.time.Duration;

public class LoginTest extends BaseTest implements ITest {

    private String testName = "Login Test";

    @Test(dataProvider = "commonDataProvider", dataProviderClass = ExcelHandler.class)
    public void loginTest(LoginTestData data) {

        testName = (data.getTestCaseName() != null && !data.getTestCaseName().isEmpty())
                ? data.getTestCaseName()
                : "Login Test";

        driver.get(PropertyHandler.getProperty("url"));

        LIB_Common common = new LIB_Common(driver);
        LoginPage loginPage = new LoginPage(driver);

        common.tc_login(data.getEmail(), data.getPassword());

        String expected = data.getExpectedResult() != null ? data.getExpectedResult() : "";
        String validationType = data.getValidationType() != null ? data.getValidationType() : "";

        if (expected.equalsIgnoreCase("success")) {

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

            wait.until(ExpectedConditions.or(
                    ExpectedConditions.urlContains("/student/dashboard"),
                    ExpectedConditions.urlToBe("https://devstacklms.vercel.app/")
            ));

            String currentUrl = driver.getCurrentUrl();

            boolean isLoggedIn = currentUrl.contains("/student/dashboard")
                    || currentUrl.equals("https://devstacklms.vercel.app/");

            Assert.assertTrue(isLoggedIn, "Login should be successful");

        } else {

            // ✅ Browser validation
            if (validationType.equalsIgnoreCase("browser")) {

                loginPage.submitLoginForm();

                String msg = loginPage.getEmailValidationMessage();

                Assert.assertTrue(msg != null && msg.isEmpty(),
                        "Browser validation message should appear");

            } else {

                // ✅ System error
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