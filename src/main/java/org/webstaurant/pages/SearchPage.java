package org.webstaurant.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;

public class SearchPage extends BasePage{
    public SearchPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//ul[@id='awesomplete_list_1']/preceding-sibling::input")
    private WebElement searchBar;

    @FindBy(xpath = "//h1[@class='page-header search--title']")
    private WebElement searchTitle;

    @FindBy(xpath = "//h2[contains(text(), \"Sorry, we couldn't find\")]")
    private WebElement couldntFindMessage;

    @FindBy(xpath = "//div[@id='product_listing']")
    private WebElement productListing;

    @FindBy(xpath = "//div[@id='paging']//ul")
    private WebElement pageNavigationButtons;

    @FindBy(xpath = "//h2[text()='added to your cart']")
    private WebElement addedToCartMessage;

    @FindBy(xpath = "//button[@aria-label='close']")
    private WebElement closeCartMessageButton;

    @FindBy(xpath = "//span[@id='cartItemCountSpan']")
    private WebElement cartCount;

    /*
    Verifies whether Page is loaded by checking elements visibility
     */
    public void verifyPageLoaded(){
        Assert.assertTrue(isVisibleWithTimeout(searchBar, 30));
        System.out.println("Search Page successfully loaded");
    }

    /*
    Returns number of products found in current Search
     */
    public int getProductsAmount(){
        String resultsLabel = searchTitle.getText();
        String resultProductsAmount = resultsLabel.substring(0, resultsLabel.indexOf(" "));
        return Integer.parseInt(resultProductsAmount);
    }

    /*
    Returns number of pages if there are more than 60 products, if less - it returns 1
     */
    public int getPagesAmount() {
        if (getProductsAmount() > 60) {
            List<WebElement> pagingButtons = pageNavigationButtons.findElements(By.tagName("li"));
            String pages = pagingButtons.get(pagingButtons.size() - 2).getText();
            return Integer.parseInt(pages);
        } else {
            return 1;
        }
    }


    /*
    Verifies whether the titles in Product listing contains given String on all pages
     */
    public void verifyProductTitlesAllPages(String targetWord){
        for (int i=1; i < getPagesAmount(); i++){
            List<WebElement> allProducts = productListing.findElements(By.xpath("//div[@id='ProductBoxContainer']//span[@data-testid='itemDescription']"));
            for (WebElement product : allProducts){
                if (product.getText().contains(targetWord)){
                    continue;
                } else {
                    Assert.fail("Product with title [" + product.getText() + "] on Page [" + (i+1) + "] does NOT contain word [" + targetWord + "]");
                }
            }
            List<WebElement> pagingButtons = pageNavigationButtons.findElements(By.tagName("li"));
            pagingButtons.get(pagingButtons.size()-1).click();
        }
        System.out.println("All product titles in Search result contain [" + targetWord + "]");
    }

    /*
    Adds last product to Shopping Cart
     */
    public void addLastProductToCart() {
        String cartCountBefore = cartCount.getText();
        List<WebElement> allProducts = productListing.findElements(By.xpath("//div[@id='ProductBoxContainer']"));

        try{
            WebElement addToCartButton = allProducts.get(allProducts.size()-1).findElement(By.xpath("//input[@value='Add to Cart']"));
            scrollToElement(addToCartButton);
            addToCartButton.click();
        } catch (NoSuchElementException e) {
            Assert.fail("Could not add Product to Shopping Cart");
        }

        Assert.assertTrue(isVisibleWithTimeout(addedToCartMessage, 30));
        closeCartMessageButton.click();
        Assert.assertFalse(cartCount.getText().equals(cartCountBefore));
        System.out.println("Product was added to Shopping Cart");
    }
}