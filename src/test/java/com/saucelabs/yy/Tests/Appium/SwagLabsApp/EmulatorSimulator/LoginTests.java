package com.saucelabs.yy.Tests.Appium.SwagLabsApp.EmulatorSimulator;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.MobileBy;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;

public class LoginTests extends TestBase {

    @Test(dataProvider = "EmulatorSimulatorDataProvider")
    public void invalidCredentials(String platform, String deviceName, String platformVersion, Method methodName) throws MalformedURLException {
        createDriver(platform, deviceName, platformVersion, methodName.getName());
        getDriver().findElement(new AppiumBy.ByAccessibilityId("test-Username")).sendKeys("bad");
        getDriver().findElement(new AppiumBy.ByAccessibilityId("test-Password")).sendKeys("bad");
        getDriver().findElement(new AppiumBy.ByAccessibilityId("test-LOGIN")).click();

        Assert.assertTrue(getDriver().findElement(new MobileBy.ByAccessibilityId("test-Error message")).isDisplayed());
    }

    @Test(dataProvider = "EmulatorSimulatorDataProvider")
    public void blankCredentials(String platform, String deviceName, String platformVersion, Method methodName) throws MalformedURLException {
        createDriver(platform, deviceName, platformVersion, methodName.getName());
        getDriver().findElement(new AppiumBy.ByAccessibilityId("test-Username")).sendKeys("");
        getDriver().findElement(new AppiumBy.ByAccessibilityId("test-Password")).sendKeys("");
        getDriver().findElement(new AppiumBy.ByAccessibilityId("test-LOGIN")).click();

        Assert.assertTrue(getDriver().findElement(new MobileBy.ByAccessibilityId("test-Error message")).isDisplayed());
    }

    @Test(dataProvider = "EmulatorSimulatorDataProvider")
    public void validCredentials(String platform, String deviceName, String platformVersion, Method methodName) throws MalformedURLException {
        createDriver(platform, deviceName, platformVersion, methodName.getName());
        getDriver().findElement(new AppiumBy.ByAccessibilityId("test-Username")).sendKeys("standard_user");
        getDriver().findElement(new AppiumBy.ByAccessibilityId("test-Password")).sendKeys("secret_sauce");
        getDriver().findElement(new AppiumBy.ByAccessibilityId("test-LOGIN")).click();

        if (platform.equalsIgnoreCase("Android")) {
            Assert.assertTrue(getDriver().findElement(By.xpath("(//android.view.ViewGroup[@content-desc=\"test-ADD TO CART\"])[1]")).isDisplayed());
        } else {
            Assert.assertTrue(getDriver().findElement(By.xpath("(//XCUIElementTypeOther[@name=\"test-ADD TO CART\"])[1]")).isDisplayed());
        }
    }
}
