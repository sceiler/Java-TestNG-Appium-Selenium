package com.saucelabs.yy.Tests.Selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.time.Duration;

public class SimpleTests extends TestBase {

    @Test(dataProvider = "Browsers")
    public void openSauceDemo(String browser, String version, String os, Method method) throws MalformedURLException {
        createDriver(browser, version, os, method.getName());
        Assert.assertTrue(getRemoteWebDriver().getTitle().contains("Swag Labs"));
    }

    @Test(dataProvider = "Browsers")
    public void logout(String browser, String version, String os, Method method) throws MalformedURLException {
        createDriver(browser, version, os, method.getName());

        new WebDriverWait(getRemoteWebDriver(), Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(By.id("react-burger-menu-btn"))).click();
        new WebDriverWait(getRemoteWebDriver(), Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(By.id("logout_sidebar_link"))).click();
        Assert.assertTrue(getRemoteWebDriver().findElement(By.className("bot_column")).isDisplayed());
    }

    @Test(dataProvider = "Browsers")
    public void lockedOutUser(String browser, String version, String os, Method method) throws MalformedURLException {
        createDriver(browser, version, os, method.getName());
        new WebDriverWait(getRemoteWebDriver(), Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(By.id("react-burger-menu-btn"))).click();
        new WebDriverWait(getRemoteWebDriver(), Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(By.id("logout_sidebar_link"))).click();
        getRemoteWebDriver().findElement(By.cssSelector("#user-name")).sendKeys("locked_out_user");
        getRemoteWebDriver().findElement(By.cssSelector("#password")).sendKeys("secret_sauce");
        getRemoteWebDriver().findElement(By.cssSelector("#login-button")).click();
        Assert.assertTrue(getRemoteWebDriver().findElement(By.className("error-button")).isDisplayed());
    }

    @Test(dataProvider = "Browsers")
    public void problemUser(String browser, String version, String os, Method method) throws MalformedURLException {
        createDriver(browser, version, os, method.getName());
        new WebDriverWait(getRemoteWebDriver(), Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(By.id("react-burger-menu-btn"))).click();
        new WebDriverWait(getRemoteWebDriver(), Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(By.id("logout_sidebar_link"))).click();
        getRemoteWebDriver().findElement(By.cssSelector("#user-name")).sendKeys("problem_user");
        getRemoteWebDriver().findElement(By.cssSelector("#password")).sendKeys("secret_sauce");
        getRemoteWebDriver().findElement(By.cssSelector("#login-button")).click();
        Assert.assertTrue(getRemoteWebDriver().getPageSource().contains("/static/media/sl-404.168b1cce.jpg"));
    }

    @Test(dataProvider = "Browsers")
    public void checkFooter(String browser, String version, String os, Method method) throws MalformedURLException {
        createDriver(browser, version, os, method.getName());
        Assert.assertTrue(getRemoteWebDriver().findElement(By.className("footer_copy")).getText().contains("Sauce"));
    }

    @Test(dataProvider = "Browsers")
    public void checkFooterRobot(String browser, String version, String os, Method method) throws MalformedURLException {
        createDriver(browser, version, os, method.getName());
        Assert.assertTrue(getRemoteWebDriver().findElement(By.className("footer_robot")).isDisplayed());
    }

    @Test(dataProvider = "Browsers")
    public void checkLogo(String browser, String version, String os, Method method) throws MalformedURLException {
        createDriver(browser, version, os, method.getName());
        Assert.assertTrue(getRemoteWebDriver().findElement(By.className("app_logo")).isDisplayed());
    }

    @Test(dataProvider = "Browsers")
    public void checkTwitter(String browser, String version, String os, Method method) throws MalformedURLException {
        createDriver(browser, version, os, method.getName());
        Assert.assertTrue(getRemoteWebDriver().findElement(By.className("social_twitter")).isDisplayed());
    }

    @Test(dataProvider = "Browsers")
    public void checkFacebook(String browser, String version, String os, Method method) throws MalformedURLException {
        createDriver(browser, version, os, method.getName());
        Assert.assertTrue(getRemoteWebDriver().findElement(By.className("social_facebook")).isDisplayed());
    }

    @Test(dataProvider = "Browsers")
    public void checkLinkedin(String browser, String version, String os, Method method) throws MalformedURLException {
        createDriver(browser, version, os, method.getName());
        Assert.assertTrue(getRemoteWebDriver().findElement(By.className("social_linkedin")).isDisplayed());
    }

    @Test(dataProvider = "Browsers")
    public void checkFleeceName(String browser, String version, String os, Method method) throws MalformedURLException {
        createDriver(browser, version, os, method.getName());
        getRemoteWebDriver().findElement(By.id("item_5_img_link")).click();
        Assert.assertEquals(getRemoteWebDriver().findElement(By.cssSelector(".inventory_details_name.large_size")).getText(), "Sauce Labs Fleece Jacket");
    }

    @Test(dataProvider = "Browsers")
    public void clickBikeLight(String browser, String version, String os, Method method) throws MalformedURLException {
        createDriver(browser, version, os, method.getName());
        getRemoteWebDriver().findElement(By.id("item_0_img_link")).click();
        Assert.assertEquals(getRemoteWebDriver().getCurrentUrl(), "https://www.saucedemo.com/inventory-item.html?id=0");
    }

    @Test(dataProvider = "Browsers")
    public void addBackpackToCart(String browser, String version, String os, Method method) throws MalformedURLException {
        createDriver(browser, version, os, method.getName());

        getRemoteWebDriver().findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        getRemoteWebDriver().get("https://www.saucedemo.com/cart.html");
        Assert.assertEquals(getRemoteWebDriver().findElement(By.className("inventory_item_name")).getText(), "Sauce Labs Backpack");
    }

    @Test(dataProvider = "Browsers")
    public void checkCartCounter(String browser, String version, String os, Method method) throws MalformedURLException {
        createDriver(browser, version, os, method.getName());

        getRemoteWebDriver().findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        Assert.assertEquals(getRemoteWebDriver().findElement(By.className("shopping_cart_badge")).getText(), "1");
        getRemoteWebDriver().findElement(By.id("remove-sauce-labs-backpack")).click();
        Assert.assertEquals(getRemoteWebDriver().findElement(By.className("shopping_cart_link")).getText(), "");
    }

    @Test(dataProvider = "Browsers")
    public void addBackpackAndOnesieToCart(String browser, String version, String os, Method method) throws MalformedURLException {
        createDriver(browser, version, os, method.getName());

        annotate("Add Backpack");
        getRemoteWebDriver().findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        annotate("Add Onesie");
        getRemoteWebDriver().findElement(By.id("add-to-cart-sauce-labs-onesie")).click();
        annotate("Go to cart");
        getRemoteWebDriver().get("https://www.saucedemo.com/cart.html");

        annotate("Check if cart has 2 items");
        Assert.assertEquals(getRemoteWebDriver().findElements(By.className("inventory_item_name")).size(), 2);
        annotate("Check if Backpack is displayed");
        Assert.assertTrue(getRemoteWebDriver().findElement(By.id("item_2_title_link")).isDisplayed());
        annotate("Check if Onesie is displayed");
        Assert.assertTrue(getRemoteWebDriver().findElement(By.id("item_4_title_link")).isDisplayed());
    }

    @Test(dataProvider = "Browsers")
    public void checkout(String browser, String version, String os, Method method) throws MalformedURLException {
        createDriver(browser, version, os, method.getName());
        
        annotate("Click Backpack");
        getRemoteWebDriver().findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        annotate("Click Onesie");
        getRemoteWebDriver().findElement(By.id("add-to-cart-sauce-labs-onesie")).click();
        annotate("Go to Shopping Cart");
        getRemoteWebDriver().get("https://www.saucedemo.com/cart.html");
        annotate("Click on Checkout");
        getRemoteWebDriver().findElement(By.id("checkout")).click();
        annotate("Enter \"Sauce\" as first name");
        getRemoteWebDriver().findElement(By.id("first-name")).sendKeys("Sauce");
        annotate("Enter \"Bot\" as last name");
        getRemoteWebDriver().findElement(By.id("last-name")).sendKeys("Bot");
        annotate("Enter \"123456\" as postal code");
        getRemoteWebDriver().findElement(By.id("postal-code")).sendKeys("123456");
        annotate("Click on Continue button");
        getRemoteWebDriver().findElement(By.id("continue")).click();
        annotate("Check that SauceCard #31337 is displayed");
        Assert.assertEquals(getRemoteWebDriver().findElement(By.className("summary_value_label")).getText(), "SauceCard #31337");
        // Click is bugged on older MacOS/Safari versions. Looks to be fixed in Big Sur
        if (os.equalsIgnoreCase(Constants.PLATFORM.MACOSCATALINA.label)) {
            annotate("Clicking on Finish button using JavaScript executor");
            getRemoteWebDriver().executeScript("arguments[0].click();", getRemoteWebDriver().findElement(By.id("finish")));
        } else {
            annotate("Click on Finish button");
            getRemoteWebDriver().findElement(By.id("finish")).click();
        }
        annotate("Assert that Pony Express image is displayed");
        Assert.assertTrue(getRemoteWebDriver().findElement(By.className("pony_express")).isDisplayed());
        annotate("Click on Products");
        getRemoteWebDriver().findElement(By.id("back-to-products")).click();
        annotate("Check that Shopping Cart is empty");
        Assert.assertEquals(getRemoteWebDriver().findElement(By.className("shopping_cart_link")).getText(), "");
    }
}