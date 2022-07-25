package com.saucelabs.yy.Tests.Selenium;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;

public class CheckoutTests extends TestBase {

    @Test(dataProvider = "Browsers")
    public void checkout(String browser, String version, String os, Method method) throws MalformedURLException {
        createDriver(browser, version, os, method.getName());
        annotate("Open saucedemo.com");
        getRemoteWebDriver().get("https://www.saucedemo.com");
        annotate("Sending valid username");
        getRemoteWebDriver().findElement(By.cssSelector("#user-name")).sendKeys("standard_user");
        annotate("Sending valid password");
        getRemoteWebDriver().findElement(By.cssSelector("#password")).sendKeys("secret_sauce");
        annotate("Clicking login button");
        getRemoteWebDriver().findElement(By.cssSelector("#login-button")).click();
        annotate("Add backpack to cart");
        getRemoteWebDriver().findElement(By.cssSelector("#add-to-cart-sauce-labs-backpack")).click();
        annotate("Click shopping cart");
        getRemoteWebDriver().findElement(By.cssSelector(".shopping_cart_badge")).click();
        annotate("Click checkout");
        getRemoteWebDriver().findElement(By.cssSelector("#checkout")).click();
        annotate("Enter first name");
        getRemoteWebDriver().findElement(By.cssSelector("#first-name")).sendKeys("Sauce");
        annotate("Enter last name");
        getRemoteWebDriver().findElement(By.cssSelector("#last-name")).sendKeys("Bot");
        annotate("Enter zip code");
        getRemoteWebDriver().findElement(By.cssSelector("#postal-code")).sendKeys("12345");
        annotate("Click continue button");
        getRemoteWebDriver().findElement(By.cssSelector("#continue")).click();
        annotate("Click finish button");
        // Click is bugged on older MacOS/Safari versions. Looks to be fixed in Big Sur
        if (getRemoteWebDriver().getCapabilities().getPlatformName().name().equalsIgnoreCase(Constants.PLATFORM.MACOSCATALINA.label)) {
            annotate("Clicking on Finish button using JavaScript executor");
            getRemoteWebDriver().executeScript("arguments[0].click();", getRemoteWebDriver().findElement(By.id("finish")));
        } else {
            annotate("Click on Finish button");
            getRemoteWebDriver().findElement(By.id("finish")).click();
        }
        Assert.assertTrue(getRemoteWebDriver().findElement(By.className("pony_express")).isDisplayed());
    }
}