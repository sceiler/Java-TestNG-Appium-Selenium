package com.saucelabs.yy.Tests.Selenium;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;

public class RemoveItemsTests extends TestBase {

    @Test(dataProvider = "hardCodedBrowsers")
    public void removeOneItemFromCart(String browser, String version, String os, Method method) throws MalformedURLException {
        createDriver(browser, version, os, method.getName());
        getRemoteWebDriver().get("https://www.saucedemo.com/inventory.html");
        getRemoteWebDriver().findElement(By.cssSelector("#add-to-cart-sauce-labs-backpack")).click();

        Assert.assertEquals("1", getRemoteWebDriver().findElement(By.cssSelector("span[class*='shopping_cart_badge']")).getText());

        getRemoteWebDriver().get("http://www.saucedemo.com/cart.html");
        Assert.assertEquals("Sauce Labs Backpack", getRemoteWebDriver().findElement(By.cssSelector("div[class='inventory_item_name']")).getText());

        getRemoteWebDriver().findElement(By.cssSelector("button[id='remove-sauce-labs-backpack']")).click();
        Assert.assertEquals(0, getRemoteWebDriver().findElements(By.cssSelector("div[class='inventory_item_name']")).size());
    }
}
