package org.webstaurant.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class HomePage extends BasePage{
    public HomePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//ul[@id='awesomplete_list_1']/preceding-sibling::input")
    private WebElement searchBar;

    @FindBy(xpath = "//h1[@class='page-header search--title']")
    private WebElement searchTitle;

    @FindBy(xpath = "//h2[contains(text(), \"Sorry, we couldn't find\")]")
    private WebElement couldntFindMessage;

    /*
    Verifies whether Page is loaded by checking elements visibility
     */
    public void verifyPageLoaded() {
        Assert.assertTrue(isVisibleWithTimeout(searchBar, 15));
        System.out.println("Home Page successfully loaded");
    }

    /*
    Conducting Search with given Product name, checks whether there're any results
    Or Search failed
     */
    public void searchProduct(String product){
        System.out.println("Searching for: [" + product + "]");
        searchBar.sendKeys(product, Keys.ENTER);
        if (isVisibleWithTimeout(searchTitle, 15)){
            String searchTitleText = searchTitle.getText();
            String searchTitleTextTrimmed = searchTitleText.substring(searchTitleText.indexOf("for: ") + "for: ".length());
            Assert.assertEquals(product.toLowerCase(), searchTitleTextTrimmed.toLowerCase());
            System.out.println("Search performed successfully");
        } else {
            isVisibleWithTimeout(couldntFindMessage, 15);
            Assert.fail("Given product was not found: [" + product + "]");
        }

    }

}