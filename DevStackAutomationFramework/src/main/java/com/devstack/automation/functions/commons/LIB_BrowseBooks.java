package com.devstack.automation.functions.commons;

import com.devstack.automation.functions.FunctionBase;
import com.devstack.automation.pages.commons.BrowseBooksPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class LIB_BrowseBooks extends FunctionBase {

    BrowseBooksPage browseBooksPage;

    public LIB_BrowseBooks(WebDriver driver) {

        this.driver = driver;

        browseBooksPage = new BrowseBooksPage(driver);
    }

    // =========================
    // ✅ VERIFY PAGE LOAD
    // =========================

    public void verifyBrowseBooksLoaded() {

        Assert.assertTrue(
                browseBooksPage.areBookCardsDisplayed(),
                "Browse Books page not loaded"
        );
    }

    // =========================
    // ✅ SEARCH BOOK
    // =========================

    public void searchBook() {

        browseBooksPage.searchBook("API");

        Assert.assertTrue(
                browseBooksPage.areBookCardsDisplayed(),
                "Search failed"
        );
    }

    // =========================
    // ✅ FILTER GENRE
    // =========================

    public void verifyGenreFilter() {

        browseBooksPage.filterGenre();

        Assert.assertTrue(
                browseBooksPage.areBookCardsDisplayed(),
                "Genre filter failed"
        );
    }

    // =========================
    // ✅ FILTER AUTHOR
    // =========================

    public void verifyAuthorFilter() {

        browseBooksPage.filterAuthor();

        Assert.assertTrue(
                browseBooksPage.areBookCardsDisplayed(),
                "Author filter failed"
        );
    }

    // =========================
    // ✅ BOOK CARDS
    // =========================

    public void verifyBookCardsDisplayed() {

        Assert.assertTrue(
                browseBooksPage.areBookCardsDisplayed(),
                "Book cards not displayed"
        );
    }

    // =========================
    // ✅ PAGINATION
    // =========================

    public void verifyPaginationNext() {

        browseBooksPage.clickNextButton();
    }

    public void verifyPaginationPrevious() {

        browseBooksPage.clickPreviousButton();
    }
}