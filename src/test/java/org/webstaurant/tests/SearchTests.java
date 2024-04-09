package org.webstaurant.tests;

import org.testng.annotations.Test;
import org.webstaurant.utilities.DataUtils;

public class SearchTests extends BaseTest {

    public static String product = DataUtils.getProperty("SEARCH_PRODUCT");
    public static String targetWord = DataUtils.getProperty("TARGET_WORD");


    /*
    Test Case: Navigate to homepage, search for product ensuring a key word is included in each result.
    Add last found item to Cart. Empty Cart.
    */
    @Test
    public void searchAllResultPages() {
        homePage.verifyPageLoaded();
        homePage.searchProduct(product);
        searchPage.verifyPageLoaded();
        searchPage.verifyProductTitlesAllPages(targetWord);
        searchPage.addLastProductToCart();
        cartPage.navigateToCart();
        cartPage.verifyPageLoaded();
        cartPage.emptyCart();
    }




}