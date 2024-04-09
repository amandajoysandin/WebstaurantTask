package org.webstaurant.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.webstaurant.utilities.DataUtils;

import java.util.concurrent.TimeUnit;

public class Driver {

    private static WebDriver driver;

    /*
    Sets up WebDriver, navigates to base URL and maximizes window
    Implicitly waits for page to load, with a timeout at 30 seconds if the page does not load
     */
    public static WebDriver SetUp() {
        if (driver==null){
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            driver.get(DataUtils.getProperty("BASE_URL"));
            driver.manage().window().maximize();
            driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        }
        return driver;
    }

    /*
    Tears down WebDriver
     */
    public static void TearDown(){
        if (driver!=null){
            driver.quit();
            driver = null;
        }
    }
}