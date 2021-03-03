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
        getRemoteWebDriver().findElement(By.className("btn_primary")).click();
        getRemoteWebDriver().findElement(By.className("btn_primary")).click();
        getRemoteWebDriver().findElement(By.className("btn_secondary")).click();

        Assert.assertEquals("1", getRemoteWebDriver().findElement(By.className("shopping_cart_badge")).getText());

        getRemoteWebDriver().get("http://www.saucedemo.com/cart.html");
        Assert.assertEquals(1, getRemoteWebDriver().findElements(By.className("inventory_item_name")).size());

        getRemoteWebDriver().findElement(By.cssSelector("button[class='btn_secondary cart_button']")).click();
        Assert.assertEquals(0, getRemoteWebDriver().findElements(By.className("inventory_item_name")).size());
    }
}
