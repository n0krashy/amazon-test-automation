package com.example.amazonautomationdemo;

import com.example.amazonautomationdemo.pages.MainPage;
import com.example.amazonautomationdemo.pages.VideoGamesPage;
import org.testng.annotations.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class MainPageTest {
    private WebDriver driver;
    private MainPage mainPage;
    private VideoGamesPage videoGamesPage;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.amazon.eg/");

        mainPage = new MainPage(driver);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @Test(priority=1)
    public void openVideoGames() throws InterruptedException {
        videoGamesPage = mainPage.openVideoGames();
        videoGamesPage.applyFilters();
        videoGamesPage.addToCartUnder15k();
    }

//    @Test
//    public void toolsMenu() {
//        mainPage.toolsMenu.click();
//
//        WebElement menuPopup = driver.findElement(By.cssSelector("div[data-test='main-submenu']"));
//        assertTrue(menuPopup.isDisplayed());
//    }
//
//    @Test
//    public void navigationToAllTools() {
//        mainPage.seeDeveloperToolsButton.click();
//        mainPage.findYourToolsButton.click();
//
//        WebElement productsList = driver.findElement(By.id("products-page"));
//        assertTrue(productsList.isDisplayed());
//        assertEquals(driver.getTitle(), "All Developer Tools and Products by JetBrains");
//    }
}
