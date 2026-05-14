package com.devstack.automation.pages.commons;

import com.devstack.automation.functions.FunctionBase;
import com.devstack.automation.testbase.SeleniumTestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BrowseBooksPage extends FunctionBase {

    public BrowseBooksPage(WebDriver driver) {

        this.driver = driver;
    }

    // 📚 Browse Books Sidebar Button
    private By btn_browse_books =
            By.xpath("//a[@href='/student/books']");

    // 🔍 Search Box
    private By txt_search =
            By.xpath("//input[contains(@placeholder,'Search')]");

    // 🔍 Search Result
    private By search_result =
            By.xpath("//*[contains(text(),'API')]");

    // 📚 Genre Dropdown
    private By ddl_genre =
            By.xpath("(//button[@role='combobox'])[1]");

    // 📖 Science Option
    private By option_science =
            By.xpath("//*[text()='Science']");

    // 👤 Author Dropdown
    private By ddl_author =
            By.xpath("(//button[@role='combobox'])[2]");

    // ✍️ Test Author
    private By option_test_author =
            By.xpath("//*[text()='Test Author']");

    // 📚 Book Cards
    private By book_cards =
            By.xpath("//div[contains(@class,'rounded-lg border bg-card')]");

    // 📖 Book Details
    private By details_text =
            By.xpath("//*[contains(text(),'Click to view details and borrow')]");

    // ⏭ Next Button
    private By next_btn =
            By.xpath("//button[normalize-space()='Next']");

    // ⏮ Previous Button
    private By previous_btn =
            By.xpath("//button[normalize-space()='Previous']");

    // =========================
    // ✅ ACTION METHODS
    // =========================

    public void clickBrowseBooks() {

        click(btn_browse_books);
    }

    public void searchBook(String bookName) {

        type(txt_search, bookName);
    }

    public boolean isSearchResultDisplayed() {

        return isElementPresent(search_result);
    }

    public void filterGenre() {

        click(ddl_genre);
        click(option_science);
    }

    public void filterAuthor() {

        click(ddl_author);
        click(option_test_author);
    }

    public void clickBookCard() {

        click(book_cards);
    }

    public boolean isBookDetailsVisible() {

        return isElementPresent(details_text);
    }

    public boolean areBookCardsDisplayed() {

        return isElementPresent(book_cards);
    }

    public void clickNextButton() {

        click(next_btn);
    }

    public void clickPreviousButton() {

        click(previous_btn);
    }
}