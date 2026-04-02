package com.devstack.automation.tests.login;

import com.devstack.automation.functions.commons.LIB_Common;
import com.devstack.automation.model.LoginTestData;
import com.devstack.automation.pages.commons.LoginPage;
import com.devstack.automation.tests.BaseTest;
import com.devstack.automation.utils.ExcelHandler;
import com.devstack.automation.utils.PropertyHandler;
import org.testng.Assert;
import org.testng.ITest;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest implements ITest {

    private String testName = "Login Test";

    @Test(dataProvider = "commonDataProvider", dataProviderClass = ExcelHandler.class)
    public void loginTest(LoginTestData data) {

        // ✅ Safe dynamic test name
        testName = data.getTestCaseName() != null && !data.getTestCaseName().isEmpty()
                ? data.getTestCaseName()
                : "Login Test";

        driver.get(PropertyHandler.getProperty("url"));

        LIB_Common common = new LIB_Common(driver);
        LoginPage loginPage = new LoginPage(driver);

        // Perform login
        common.tc_login(data.getEmail(), data.getPassword());

        String expected = data.getExpectedResult() != null ? data.getExpectedResult() : "";
        String validationType = data.getValidationType() != null ? data.getValidationType() : "";

        if (expected.equalsIgnoreCase("success")) {

            Assert.assertTrue(loginPage.isDashboardLoaded(),
                    "Login should be successful");

        } else {

            if (validationType.equalsIgnoreCase("browser")) {

                String msg = loginPage.getEmailValidationMessage();

                Assert.assertTrue(msg != null && msg.length() > 0,
                        "Browser validation message should appear");

            } else {

                String errorMsg = loginPage.getErrorMessage();

                // ✅ flexible validation
                Assert.assertTrue(errorMsg != null && errorMsg.contains("Invalid"),
                        "System error message should appear");
            }
        }
    }

    @Override
    public String getTestName() {
        return testName;
    }
}