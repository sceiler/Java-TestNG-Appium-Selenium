package com.saucelabs.yy.Tests.Appium.SwagLabsApp.EmulatorSimulator;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;

public class AddItemsTests extends TestBase {

    @Test(dataProvider = "EmulatorSimulatorDataProvider")
    public void addOneItemtoCart(String platform, String deviceName, String platformVersion, Method methodName) throws MalformedURLException {
        createDriver(platform, deviceName, platformVersion, methodName.getName());
        getDriver().findElement(new AppiumBy.ByAccessibilityId("test-Username")).sendKeys("standard_user");
        getDriver().findElement(new AppiumBy.ByAccessibilityId("test-Password")).sendKeys("secret_sauce");
        getDriver().findElement(new AppiumBy.ByAccessibilityId("test-LOGIN")).click();

        if (platform.equalsIgnoreCase("Android")) {
            getDriver().findElement(By.xpath("(//android.view.ViewGroup[@content-desc=\"test-ADD TO CART\"])[1]")).click();
            getDriver().findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"test-Cart\"]/android.view.ViewGroup/android.widget.TextView")).click();
            Assert.assertEquals(getDriver().findElement(By.xpath("(//android.view.ViewGroup[@content-desc=\"test-Description\"])[1]/android.widget.TextView[1]")).getText(), "Sauce Labs Backpack");
        } else {
            getDriver().findElement(By.xpath("(//XCUIElementTypeOther[@name=\"test-ADD TO CART\"])[1]")).click();
            getDriver().findElement(By.xpath("(//XCUIElementTypeOther[@name=\"1\"])[4]")).click();
            Assert.assertTrue(getDriver().findElement(By.xpath("(//XCUIElementTypeOther[@name=\"1\"])[4]")).isDisplayed());
        }
    }
}
