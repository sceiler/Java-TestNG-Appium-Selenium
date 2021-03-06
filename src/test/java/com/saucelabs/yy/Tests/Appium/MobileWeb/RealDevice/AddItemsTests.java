package com.saucelabs.yy.Tests.Appium.MobileWeb.RealDevice;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;

public class AddItemsTests extends TestBase {

    @Test(dataProvider = "RDCDataProvider")
    public void addOneItemtoCart(String platform, String deviceName, String platformVersion, Method methodName) throws MalformedURLException {
        createDriver(platform, deviceName, platformVersion, methodName.getName());
        getDriver().get("https://www.saucedemo.com/inventory.html");
        getDriver().findElement(By.cssSelector("button[class='btn_primary btn_inventory']")).click();
        getDriver().findElement(By.cssSelector("button[class='btn_primary btn_inventory']")).click();
        getDriver().findElement(By.cssSelector("button[class='btn_secondary btn_inventory']")).click();

        Assert.assertEquals("1", getDriver().findElement(By.cssSelector("span[class*='shopping_cart_badge']")).getText());

        getDriver().get("http://www.saucedemo.com/cart.html");
        Assert.assertEquals(1, getDriver().findElements(By.cssSelector("div[class='inventory_item_name']")).size());
    }
}
