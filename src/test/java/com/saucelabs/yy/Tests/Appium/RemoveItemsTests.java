package com.saucelabs.yy.Tests.Appium;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.rmi.UnexpectedException;

public class RemoveItemsTests extends TestBase {

    @Test(dataProvider = "hardCodedBrowsers")
    public void addOneItemtoCart(String platform, String deviceName, String platformVersion, String appiumVersion,
                                 String orientation, String browserName, Method methodName) throws MalformedURLException, UnexpectedException {
        
        createDriver(platform, deviceName, browserName, platformVersion, appiumVersion, orientation, methodName.getName());
        getAndroidDriver().get("https://www.saucedemo.com/inventory.html");
        getAndroidDriver().findElement(By.className("btn_primary")).click();
        getAndroidDriver().findElement(By.className("btn_primary")).click();
        getAndroidDriver().findElement(By.className("btn_secondary")).click();

        Assert.assertEquals("1", getAndroidDriver().findElement(By.className("shopping_cart_badge")).getText());

        getAndroidDriver().get("http://www.saucedemo.com/cart.html");
        Assert.assertEquals(1, getAndroidDriver().findElements(By.className("inventory_item_name")).size());
    }
}
