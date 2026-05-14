package com.devstack.automation.pages.commons;

import com.devstack.automation.functions.FunctionBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DashboardPage extends FunctionBase {

    public DashboardPage(WebDriver driver) {

        super(driver);
    }

    // 🔥 Header
    private By dashboardTitle =
            By.xpath("//h1[contains(text(),'Library Portal')]");

    // 📚 Browse Books Sidebar
    private By btn_browse_books =
            By.xpath("//a[@href='/student/books' and .//span[normalize-space()='Browse Books']]");

    // 🔥 Cards
    private By activeReservations =
            By.xpath("//p[contains(text(),'Active Reservations')]/preceding-sibling::p");

    private By currentBorrowings =
            By.xpath("//p[contains(text(),'Current Borrowings')]/preceding-sibling::p");

    private By overdueBooks =
            By.xpath("//p[contains(text(),'Overdue Books')]/preceding-sibling::p");

    // 🔥 Member Status
    private By memberStatus =
            By.xpath("//p[contains(text(),'Member Status')]/following::p");

    // 🔥 Profile Section
    private By profileSection =
            By.xpath("//h2[contains(text(),'My Profile')]");

    private By emailText =
            By.xpath("//p[contains(text(),'@')]");

    // 🔥 Book Section
    private By availableBooksTitle =
            By.xpath("//h2[contains(text(),'Available Books')]");

    private By searchBox =
            By.xpath("//input[contains(@placeholder,'Search')]");

    private By filterGenreBtn =
            By.xpath("//button[contains(text(),'Genre')]");

    private By filterAuthorBtn =
            By.xpath("//button[contains(text(),'Author')]");

    // 🔥 Book Card
    private By bookCard =
            By.xpath("//div[contains(@class,'book')]");

    // 🔥 Pagination
    private By nextPageBtn =
            By.xpath("//button[contains(text(),'Next')]");

    // ===========================================
    // ✅ VALIDATIONS
    // ===========================================

    public boolean isDashboardLoaded() {

        return isElementPresent(dashboardTitle);
    }

    public boolean isCardsVisible() {

        return isElementPresent(activeReservations)
                && isElementPresent(currentBorrowings)
                && isElementPresent(overdueBooks);
    }

    public boolean isMemberActive() {

        return isElementPresent(memberStatus);
    }

    public boolean isProfileSectionVisible() {

        return isElementPresent(profileSection)
                && isElementPresent(emailText);
    }

    public boolean isBookSectionVisible() {

        return isElementPresent(availableBooksTitle)
                && isElementPresent(searchBox);
    }

    public boolean isBookListLoaded() {

        return isElementPresent(bookCard);
    }

    // ===========================================
    // ✅ ACTION METHODS
    // ===========================================

    // ✅ CLICK BROWSE BOOKS
    public void clickBrowseBooks() {

        click(btn_browse_books);
    }

    public void enterSearch(String bookName) {

        type(searchBox, bookName);
    }

    public void clickSearch() {

        waitForVisibilityOfElement(searchBox)
                .submit();
    }

    public void clickFilterGenre() {

        click(filterGenreBtn);
    }

    public void clickFilterAuthor() {

        click(filterAuthorBtn);
    }

    public void clickBookCard() {

        click(bookCard);
    }

    public void clickBorrow() {

        click(By.xpath("//button[contains(text(),'Borrow')]"));
    }

    public void goToNextPage() {

        click(nextPageBtn);
    }
}

