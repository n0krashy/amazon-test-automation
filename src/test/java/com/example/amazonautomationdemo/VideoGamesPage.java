package com.example.amazonautomationdemo;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.PageUrl;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@PageUrl("https://www.amazon.eg/b?node=18022560031")
public class VideoGamesPage extends FluentPage {
    private final static By freeShippingCheckbox = By.xpath("//label[@for='apb-browse-refinements-checkbox_2']");
    private final static By newButton = By.xpath("//span[@class='a-size-base a-color-base'][normalize-space()='New']");
    private final static By sortBy = By.cssSelector(".a-dropdown-prompt");
    private final static By highToLow = By.id("s-result-sort-select_2");
    private final WebDriver driver;
    private final WebDriverWait wait;

    public VideoGamesPage(WebDriver driver) {
        this.driver = driver;
        // Defining Explicit Wait
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        waitForPageToLoad();
    }

    /**
     * This method clicks on free shipping checkbox
     */
    public void clickFreeShipping() {
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", driver.findElement(freeShippingCheckbox));
    }

    /**
     * This method clicks on New button
     */
    public void clickNewButton() {
        driver.findElement(newButton).click();
    }

    /**
     * This method clicks on High to low button
     */
    public void clickOnHighToLow() {
        driver.findElement(highToLow).click();
    }

    /**
     * This method clicks on sort by button
     */
    public void openSortingList() {
        driver.findElement(sortBy).click();
    }

    /**
     * This method opens applies filters
     */
    public void applyFilters() {
        clickAndWaitForStaleness(freeShippingCheckbox, newButton);
        clickAndWaitForStaleness(newButton);
        openSortingList();
        clickOnHighToLow();
    }

    private void clickAndWaitForStaleness(By by){
        clickAndWaitForStaleness(by, by);
    }

    private void clickAndWaitForStaleness(By by, By satlenessBy){
        // save element to check staleness later
        WebElement elementOldPage = driver.findElement(satlenessBy);
        driver.findElement(by).click();
        // wait for page to reload
        wait.until(ExpectedConditions.stalenessOf(elementOldPage));
    }

    /**
     * This method waits for the page to load by waiting for hamburgerMenu button to be clickable
     */
    private void waitForPageToLoad() {
        wait.until(ExpectedConditions.elementToBeClickable(freeShippingCheckbox));
    }
}