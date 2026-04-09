
package com.devstack.automation.pages.commons;

import com.devstack.automation.testbase.SeleniumTestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

    public class DashboardPage extends SeleniumTestBase {

        public DashboardPage(WebDriver driver) {
            super(driver);
        }

        // Header
        private By dashboardTitle = By.xpath("//h1[contains(text(),'Library Portal')]");

        // Cards
        private By activeReservations = By.xpath("//p[contains(text(),'Active Reservations')]/preceding-sibling::p");
        private By currentBorrowings = By.xpath("//p[contains(text(),'Current Borrowings')]/preceding-sibling::p");
        private By overdueBooks = By.xpath("//p[contains(text(),'Overdue Books')]/preceding-sibling::p");
        private By memberStatus = By.xpath("//p[contains(text(),'Member Status')]/following::p[contains(text(),'Active')]");

        // Profile Section
        private By profileSection = By.xpath("//h2[contains(text(),'My Profile')]");
        private By emailText = By.xpath("//p[contains(text(),'@library.com')]");

        // Book Section
        private By availableBooksTitle = By.xpath("//h2[contains(text(),'Available Books')]");
        private By searchBox = By.xpath("//input[@placeholder='Search books by title,']");
        private By filterGenre = By.xpath("//button[contains(text(),'Filter by Genre')]");
        private By filterAuthor = By.xpath("//button[contains(text(),'Filter by Author')]");

        // Book Card
        private By bookCard = By.xpath("//p[contains(text(),'Test Book Title')]");

        // Actions
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
                    && driver.findElement(searchBox).isDisplayed()
                    && driver.findElement(filterGenre).isDisplayed()
                    && driver.findElement(filterAuthor).isDisplayed();
        }

        public boolean isBookListLoaded() {
            return driver.findElements(bookCard).size() > 0;
        }
    }


