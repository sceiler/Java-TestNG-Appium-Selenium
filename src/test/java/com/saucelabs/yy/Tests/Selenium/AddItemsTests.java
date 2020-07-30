package com.saucelabs.yy.Tests.Selenium;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.rmi.UnexpectedException;

public class AddItemsTests extends TestBase {

    @Test(dataProvider = "hardCodedBrowsers")
    public void addOneItemtoCart(String browser, String version, String os, Method method) throws MalformedURLException, UnexpectedException {
        createDriver(browser, version, os, method.getName());
        getRemoteWebDriver().get("https://www.saucedemo.com/inventory.html");
        getRemoteWebDriver().findElement(By.className("btn_primary")).click();

        Assert.assertEquals("1", getRemoteWebDriver().findElement(By.className("shopping_cart_badge")).getText());

        getRemoteWebDriver().get("http://www.saucedemo.com/cart.html");
        Assert.assertEquals(1, getRemoteWebDriver().findElements(By.className("inventory_item_name")).size());
    }

    @Test(dataProvider = "hardCodedBrowsers")
    public void addTwoItemsToCart(String browser, String version, String os, Method method) throws MalformedURLException, UnexpectedException {
        createDriver(browser, version, os, method.getName());
        getRemoteWebDriver().get("https://www.saucedemo.com/inventory.html");
        getRemoteWebDriver().findElement(By.className("btn_primary")).click();
        getRemoteWebDriver().findElement(By.className("btn_primary")).click();

        Assert.assertEquals("2", getRemoteWebDriver().findElement(By.className("shopping_cart_badge")).getText());

        getRemoteWebDriver().get("http://www.saucedemo.com/cart.html");
        Assert.assertEquals(2, getRemoteWebDriver().findElements(By.className("inventory_item_name")).size());
    }
}
