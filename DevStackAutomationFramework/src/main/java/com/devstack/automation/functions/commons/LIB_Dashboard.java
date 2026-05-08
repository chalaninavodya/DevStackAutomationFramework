package com.devstack.automation.functions.commons;

import com.devstack.automation.pages.dashboard.DashboardPage;
import com.devstack.automation.reporter.ExtentReportManager;
import org.openqa.selenium.WebDriver;

public class LIB_Dashboard {

    private WebDriver driver;
    private DashboardPage dashboardPage;

    public LIB_Dashboard(WebDriver driver) {
        this.driver = driver;
        this.dashboardPage = new DashboardPage(driver);
    }

    // ===========================================
    // ✅ BASIC VALIDATIONS
    // ===========================================

    public void verifyDashboardLoaded() {
        try {
            if (dashboardPage.isDashboardLoaded()) {
                ExtentReportManager.logPass("Dashboard loaded successfully");
            } else {
                ExtentReportManager.logFail("Dashboard not loaded");
            }
        } catch (Exception e) {
            ExtentReportManager.logFail("Dashboard verification failed: " + e.getMessage());
        }
    }

    public void verifyCards() {
        try {
            if (dashboardPage.isCardsVisible()) {
                ExtentReportManager.logPass("Dashboard cards are visible");
            } else {
                ExtentReportManager.logFail("Dashboard cards not visible");
            }
        } catch (Exception e) {
            ExtentReportManager.logFail("Card validation failed: " + e.getMessage());
        }
    }

    public void verifyProfileSection() {
        try {
            if (dashboardPage.isProfileSectionVisible()) {
                ExtentReportManager.logPass("Profile section visible");
            } else {
                ExtentReportManager.logFail("Profile section not visible");
            }
        } catch (Exception e) {
            ExtentReportManager.logFail("Profile validation failed: " + e.getMessage());
        }
    }

    public void verifyBooksSection() {
        try {
            if (dashboardPage.isBookSectionVisible()) {
                ExtentReportManager.logPass("Books section visible");
            } else {
                ExtentReportManager.logFail("Books section not visible");
            }
        } catch (Exception e) {
            ExtentReportManager.logFail("Books section validation failed: " + e.getMessage());
        }
    }

    public void verifyBookList() {
        try {
            if (dashboardPage.isBookListLoaded()) {
                ExtentReportManager.logPass("Book list loaded");
            } else {
                ExtentReportManager.logFail("Book list not loaded");
            }
        } catch (Exception e) {
            ExtentReportManager.logFail("Book list validation failed: " + e.getMessage());
        }
    }

    // ===========================================
    // ✅ ACTION METHODS (DATA-DRIVEN)
    // ===========================================

    public void searchBook(String book) {
        try {
            dashboardPage.enterSearch(book);
            dashboardPage.clickSearch();
            ExtentReportManager.logInfo("Searched book: " + book);
        } catch (Exception e) {
            ExtentReportManager.logFail("Search failed: " + e.getMessage());
        }
    }

    public void filterByGenre(String genre) {
        try {
            dashboardPage.clickFilterGenre();
            ExtentReportManager.logInfo("Filter by genre: " + genre);
        } catch (Exception e) {
            ExtentReportManager.logFail("Genre filter failed: " + e.getMessage());
        }
    }

    public void filterByAuthor(String author) {
        try {
            dashboardPage.clickFilterAuthor();
            ExtentReportManager.logInfo("Filter by author: " + author);
        } catch (Exception e) {
            ExtentReportManager.logFail("Author filter failed: " + e.getMessage());
        }
    }

    public void viewBookDetails() {
        try {
            dashboardPage.clickBookCard();
            ExtentReportManager.logInfo("Opened book details");
        } catch (Exception e) {
            ExtentReportManager.logFail("View book failed: " + e.getMessage());
        }
    }

    public void borrowBook() {
        try {
            dashboardPage.clickBorrow();
            ExtentReportManager.logPass("Borrow action successful");
        } catch (Exception e) {
            ExtentReportManager.logFail("Borrow failed: " + e.getMessage());
        }
    }

    public void goToNextPage() {
        try {
            dashboardPage.goToNextPage();
            ExtentReportManager.logInfo("Navigated to next page");
        } catch (Exception e) {
            ExtentReportManager.logFail("Pagination failed: " + e.getMessage());
        }
    }

    // ===========================================
    // ✅ URL VALIDATION (optional)
    // ===========================================

    public void verifyDashboardURL() {
        try {
            if (driver.getCurrentUrl().contains("dashboard")) {
                ExtentReportManager.logPass("Dashboard URL correct");
            } else {
                ExtentReportManager.logFail("Dashboard URL incorrect");
            }
        } catch (Exception e) {
            ExtentReportManager.logFail("URL validation failed: " + e.getMessage());
        }
    }
}
