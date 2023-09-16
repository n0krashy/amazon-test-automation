package com.example.amazonautomationdemo.pages;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.PageUrl;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.numberOfWindowsToBe;

@PageUrl("https://www.amazon.eg/b?node=18022560031")
public class VideoGamesPage extends FluentPage {
    private final static By freeShippingCheckbox = By.xpath("//label[@for='apb-browse-refinements-checkbox_2']");
    private final static By newButton = By.xpath("//span[@class='a-size-base a-color-base'][normalize-space()='New']");
    private final static By sortBy = By.cssSelector(".a-dropdown-prompt");
    private final static By highToLow = By.id("s-result-sort-select_2");
    private final static By price = By.cssSelector("span[data-component-type='s-search-results'] span[class='a-price-whole']");
    private final static By searchResults = By.cssSelector("span[data-component-type='s-search-results']");
    private final static By searchResult = By.cssSelector("div[data-component-type='s-search-result']");
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
    public void applyFilters() throws InterruptedException {
        clickAndWaitForStaleness(freeShippingCheckbox, newButton);
        clickAndWaitForStaleness(newButton);
        openSortingList();
        clickOnHighToLow();
        scrollDown();
    }

    private void scrollDown() {
        new Actions(driver)
                .scrollByAmount(0, 100)
                .perform();
    }

    public void addToCartUnder15k(){
        wait.until(ExpectedConditions.presenceOfElementLocated(price));
        List<WebElement> pricesElements = driver.findElements(price);
        int elementPrice;
        for (WebElement webElement : pricesElements){
            elementPrice = Integer.parseInt(webElement.getText().replaceAll(",", ""));
            System.out.println("7amada: " + elementPrice);
            if (elementPrice < 15000){
                addItemToCart(webElement);
            }
        }
    }

    private void addItemToCart(WebElement e){
        Actions actions = new Actions(driver);
        actions.keyDown(Keys.COMMAND).moveToElement(e).click().keyUp(Keys.COMMAND).build().perform();
        //Wait for the new window or tab
        wait.until(numberOfWindowsToBe(2));
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        new ProductPage(driver);
        driver.close();
        driver.switchTo().window(tabs.get(0));
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
     * This method waits for the page to load by waiting for free shipping checkbox to be clickable
     */
    private void waitForPageToLoad() {
        wait.until(ExpectedConditions.elementToBeClickable(freeShippingCheckbox));
    }
}