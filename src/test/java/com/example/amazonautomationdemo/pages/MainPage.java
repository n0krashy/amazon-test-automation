package com.example.amazonautomationdemo.pages;

import org.fluentlenium.core.annotation.PageUrl;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
@PageUrl("https://www.amazon.eg/")
public class MainPage {
    private final static By language = By.id("icp-nav-flyout");
    private final static By logo = By.id("nav-bb-logo");
    private final static By searchButton = By.xpath("//input[@type='submit']");
    private final static By englishLanguage = By.xpath("//a[@href='#switch-lang=en_AE']");
    private final static By hamburgerMenu = By.cssSelector("div[id='nav-main'] div[class='nav-left']");
    private final static By seeAllButton = By.xpath("//div[normalize-space()='see all']");
    private final static By videoGamesButton = By.xpath("//div[normalize-space()='Video Games']");
    private final static By allVideoGamesButton = By.xpath("//a[normalize-space()='All Video Games']");

    private final WebDriver driver;
    private final WebDriverWait wait;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        // Defining Explicit Wait
        wait = new WebDriverWait(driver, Duration.ofSeconds(6));
        waitForPageToLoad();
        clickLogo();
        setLanguageToEnglish();
    }

    /**
     * This method sets language to english instead of Arabic
     */
    private void setLanguageToEnglish(){
        if(!driver.findElement(language).getText().equals("EN")) {
            WebElement element = driver.findElement(language);
            Actions actions = new Actions(driver);
            actions.moveToElement(element).perform();
            wait.until(ExpectedConditions.elementToBeClickable(englishLanguage));
            driver.findElement(englishLanguage).click();
        }
    }

    /**
     * This method clicks on amazon logo
     */
    private void clickLogo(){
        if(driver.findElement(searchButton).getAttribute("value").equals("Go"))
            driver.findElement(logo).click();
    }

    /**
     * This method clicks to open Hamburger menu
     */
    private void openHamburgerMenu(){
        wait.until(ExpectedConditions.elementToBeClickable(hamburgerMenu));
        driver.findElement(hamburgerMenu).click();
    }

    /**
     * This method clicks on see all
     */
    private void clickSeeAll(){
        wait.until(ExpectedConditions.elementToBeClickable(seeAllButton));
        driver.findElement(seeAllButton).click();
    }

    /**
     * This method clicks on Video games button
     */
    private void clickVideoGames(){
        wait.until(ExpectedConditions.elementToBeClickable(videoGamesButton));
        driver.findElement(videoGamesButton).click();
    }

    /**
     * This method clicks on All Video games button
     */
    private void clickAllVideoGames(){
        wait.until(ExpectedConditions.elementToBeClickable(allVideoGamesButton));
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", driver.findElement(allVideoGamesButton));
    }

    /**
     * This method opens Video game page
     */
    public VideoGamesPage openVideoGames() {
        int tries = 0;
        do {
            openHamburgerMenu();
            tries++;
        }
        while (driver.findElements(seeAllButton).isEmpty() && tries<4);

        tries = 0;
        do {
            clickSeeAll();
            tries++;
        }
        while (driver.findElements(videoGamesButton).isEmpty() && tries<4);

        clickVideoGames();
        clickAllVideoGames();

        return new VideoGamesPage(driver);
    }

    /**
     * This method waits for the page to load by waiting for hamburgerMenu button to be clickable
     */
    private void waitForPageToLoad() {
        wait.until(ExpectedConditions.elementToBeClickable(searchButton));
    }
}
