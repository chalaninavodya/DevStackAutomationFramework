package com.devstack.automation.tests.books;

import com.devstack.automation.functions.commons.LIB_BrowseBooks;
import com.devstack.automation.functions.commons.LIB_Common;
import com.devstack.automation.functions.commons.LIB_Dashboard;
import com.devstack.automation.reporter.ExtentReportManager;
import com.devstack.automation.testbase.SeleniumTestBase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class BrowseBooksTest extends SeleniumTestBase {

    LIB_Common common;
    LIB_Dashboard dashboard;
    LIB_BrowseBooks browseBooks;

    @BeforeMethod
    public void setupTest() throws Exception {

        common = new LIB_Common(driver);

        dashboard = new LIB_Dashboard(driver);

        browseBooks = new LIB_BrowseBooks(driver);

        // ✅ LOGIN
        common.tc_login(
                "student1@library.com",
                "password"
        );

        Thread.sleep(3000);

        // ✅ GO TO BROWSE BOOKS
        dashboard.goToBrowseBooks();

        Thread.sleep(3000);
    }

    @DataProvider(name = "browseBooksData")
    public Object[][] browseBooksData() {

        return new Object[][]{

                {"loadBrowseBooks"},
                {"searchBook"},
                {"filterGenre"},
                {"filterAuthor"},
                {"bookCards"},
                {"nextPagination"},
                {"previousPagination"}
        };
    }

    @Test(dataProvider = "browseBooksData")
    public void verifyBrowseBooks(String action) {

        ExtentReportManager.createTest(
                "Browse Books Test - " + action
        );

        switch (action) {

            case "loadBrowseBooks":

                browseBooks.verifyBrowseBooksLoaded();
                break;

            case "searchBook":

                browseBooks.searchBook();
                break;

            case "filterGenre":

                browseBooks.verifyGenreFilter();
                break;

            case "filterAuthor":

                browseBooks.verifyAuthorFilter();
                break;

            case "bookCards":

                browseBooks.verifyBookCardsDisplayed();
                break;

            case "nextPagination":

                browseBooks.verifyPaginationNext();
                break;

            case "previousPagination":

                browseBooks.verifyPaginationPrevious();
                break;

            default:

                System.out.println("Invalid Action");
        }
    }
}