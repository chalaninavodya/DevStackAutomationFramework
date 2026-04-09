package com.devstack.automation.tests.dashboard;

import com.devstack.automation.functions.commons.LIB_Common;
import com.devstack.automation.functions.dashboard.LIB_Dashboard;
import com.devstack.automation.testbase.SeleniumTestBase;
import org.testng.annotations.Test;

    public class DashboardTest extends SeleniumTestBase {



        @Test
        public void verifyDashboard() {

            LIB_Common common = new LIB_Common(driver);
            LIB_Dashboard dashboard = new LIB_Dashboard(driver);

            // Login
            common.login("super@admin.com", "password");

            // Verify Dashboard
            dashboard.verifyDashboardUI();
        }
    }

