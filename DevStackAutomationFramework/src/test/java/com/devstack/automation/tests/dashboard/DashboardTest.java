package com.devstack.automation.tests.dashboard;

import com.devstack.automation.functions.commons.LIB_Common;
import com.devstack.automation.functions.commons.LIB_Dashboard;
import com.devstack.automation.reporter.ExtentReportManager;
import com.devstack.automation.testbase.SeleniumTestBase;
import org.testng.annotations.*;

import java.lang.reflect.Method;

public class DashboardTest extends SeleniumTestBase {

    // ===========================================
    // ✅ REPORT SETUP
    // ===========================================

    @BeforeSuite
    public void startReport() {
        ExtentReportManager.initReport();
    }

    @BeforeMethod
    public void setUpTest(Method method) {

        super.setUp(); // 🔥 initialize driver

        ExtentReportManager.createTest(method.getName());
    }

    @AfterMethod
    public void tearDownTest() {
        super.tearDown(); // 🔥 close browser
    }

    @AfterSuite
    public void endReport() {
        ExtentReportManager.flushReport();
    }

    // ===========================================
    // ✅ BASIC TEST (SMOKE)
    // ===========================================

    @Test
    public void verifyDashboard() {

        LIB_Common common = new LIB_Common(driver);
        common.tc_login("super@admin.com", "password");

        LIB_Dashboard dashboard = new LIB_Dashboard(driver);
        dashboard.verifyDashboardLoaded();
    }

    // ===========================================
    // 🔥 DATA-DRIVEN TEST
    // ===========================================

    @Test(dataProvider = "dashboardData")
    public void dashboardDataDrivenTest(String testCaseName,
                                        String action,
                                        String input,
                                        String expected,
                                        String validationType) {

        LIB_Common common = new LIB_Common(driver);
        common.tc_login("super@admin.com", "password");

        LIB_Dashboard dashboard = new LIB_Dashboard(driver);

        switch (action) {

            case "loadDashboard":
                dashboard.verifyDashboardLoaded();
                break;

            case "getCards":
                dashboard.verifyCards();
                break;

            case "getProfile":
                dashboard.verifyProfileSection();
                break;

            case "loadBooks":
                dashboard.verifyBooksSection();
                break;

            case "searchBook":
                dashboard.searchBook(input);
                break;

            case "filterGenre":
                dashboard.filterByGenre(input);
                break;

            case "filterAuthor":
                dashboard.filterByAuthor(input);
                break;

            case "viewBook":
                dashboard.viewBookDetails();
                break;

            case "borrowBook":
                dashboard.borrowBook();
                break;

            case "pagination":
                dashboard.goToNextPage();
                break;

            default:
                ExtentReportManager.logInfo("No matching action for: " + action);
        }
    }

    // ===========================================
    // 🔥 DATA PROVIDER (TEMP - later Excel)
    // ===========================================

    @DataProvider(name = "dashboardData")
    public Object[][] getData() {
        return new Object[][]{

                {"TC_DASH_001","loadDashboard","-","success","ui"},
                {"TC_DASH_002","getCards","-","displayed","ui"},
                {"TC_DASH_003","getProfile","-","displayed","ui"},
                {"TC_DASH_004","loadBooks","-","success","ui"},
                {"TC_DASH_005","searchBook","Harry Potter","results","ui"},
                {"TC_DASH_006","filterGenre","Fiction","filtered","ui"},
                {"TC_DASH_007","filterAuthor","Rowling","filtered","ui"},
                {"TC_DASH_008","viewBook","-","opened","ui"},
                {"TC_DASH_009","borrowBook","-","success","system"},
                {"TC_DASH_010","pagination","page2","success","ui"}
        };
    }
}
