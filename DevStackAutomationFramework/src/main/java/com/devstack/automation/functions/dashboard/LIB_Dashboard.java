
    package com.devstack.automation.functions.dashboard;

import com.devstack.automation.functions.FunctionBase;
import com.devstack.automation.pages.commons.DashboardPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

    public class LIB_Dashboard extends FunctionBase {

        DashboardPage dashboardPage;

        public LIB_Dashboard(WebDriver driver) {
            super(driver);
            dashboardPage = new DashboardPage(driver);
        }

        public void verifyDashboardUI() {

            Assert.assertTrue(dashboardPage.isDashboardLoaded(), "Dashboard not loaded");

            Assert.assertTrue(dashboardPage.isCardsVisible(), "Dashboard cards missing");

            Assert.assertTrue(dashboardPage.isMemberActive(), "Member not active");

            Assert.assertTrue(dashboardPage.isProfileSectionVisible(), "Profile section missing");

            Assert.assertTrue(dashboardPage.isBookSectionVisible(), "Book section missing");

            Assert.assertTrue(dashboardPage.isBookListLoaded(), "Books not loaded");
        }
    }

