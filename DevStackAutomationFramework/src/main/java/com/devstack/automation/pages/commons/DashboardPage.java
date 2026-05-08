package com.devstack.automation.pages.dashboard;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DashboardPage {

    private WebDriver driver;

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
    }

    // 🔥 Header
    private By dashboardTitle = By.xpath("//h1[contains(text(),'Library Portal')]");

    // 🔥 Cards
    private By activeReservations = By.xpath("//p[contains(text(),'Active Reservations')]/preceding-sibling::p");
    private By currentBorrowings = By.xpath("//p[contains(text(),'Current Borrowings')]/preceding-sibling::p");
    private By overdueBooks = By.xpath("//p[contains(text(),'Overdue Books')]/preceding-sibling::p");

    // 🔥 Member Status
    private By memberStatus = By.xpath("//p[contains(text(),'Member Status')]/following::p");

    // 🔥 Profile Section
    private By profileSection = By.xpath("//h2[contains(text(),'My Profile')]");
    private By emailText = By.xpath("//p[contains(text(),'@')]");

    // 🔥 Book Section
    private By availableBooksTitle = By.xpath("//h2[contains(text(),'Available Books')]");
    private By searchBox = By.xpath("//input[contains(@placeholder,'Search')]");
    private By filterGenreBtn = By.xpath("//button[contains(text(),'Genre')]");
    private By filterAuthorBtn = By.xpath("//button[contains(text(),'Author')]");

    // 🔥 Book Card
    private By bookCard = By.xpath("//div[contains(@class,'book')]");

    // 🔥 Pagination
    private By nextPageBtn = By.xpath("//button[contains(text(),'Next')]");

    // ===========================================
    // ✅ VALIDATIONS
    // ===========================================

    public boolean isDashboardLoaded() {
        return driver.findElement(dashboardTitle).isDisplayed();
    }

    public boolean isCardsVisible() {
        return driver.findElement(activeReservations).isDisplayed()
                && driver.findElement(currentBorrowings).isDisplayed()
                && driver.findElement(overdueBooks).isDisplayed();
    }

    public boolean isMemberActive() {
        return driver.findElement(memberStatus).isDisplayed();
    }

    public boolean isProfileSectionVisible() {
        return driver.findElement(profileSection).isDisplayed()
                && driver.findElement(emailText).isDisplayed();
    }

    public boolean isBookSectionVisible() {
        return driver.findElement(availableBooksTitle).isDisplayed()
                && driver.findElement(searchBox).isDisplayed();
    }

    public boolean isBookListLoaded() {
        return driver.findElements(bookCard).size() > 0;
    }

    // ===========================================
    // ✅ ACTION METHODS (DATA-DRIVEN USE)
    // ===========================================

    public void enterSearch(String bookName) {
        driver.findElement(searchBox).clear();
        driver.findElement(searchBox).sendKeys(bookName);
    }

    public void clickSearch() {
        driver.findElement(searchBox).submit();
    }

    public void clickFilterGenre() {
        driver.findElement(filterGenreBtn).click();
    }

    public void clickFilterAuthor() {
        driver.findElement(filterAuthorBtn).click();
    }

    public void clickBookCard() {
        driver.findElement(bookCard).click();
    }

    public void clickBorrow() {
        driver.findElement(By.xpath("//button[contains(text(),'Borrow')]")).click();
    }

    public void goToNextPage() {
        driver.findElement(nextPageBtn).click();
    }
}


