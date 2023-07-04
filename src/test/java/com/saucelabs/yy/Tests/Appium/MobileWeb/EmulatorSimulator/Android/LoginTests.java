package com.saucelabs.yy.Tests.Appium.MobileWeb.EmulatorSimulator.Android;

import com.saucelabs.yy.Tests.Appium.TestBase;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;

public class LoginTests extends TestBase {

    @Test(dataProvider = "Devices")
    public void loginUIPresent(String platform, String deviceName, String platformVersion, Method methodName) throws MalformedURLException {
        createDriver(platform, deviceName, platformVersion, true, false, methodName.getName(), "");
        annotate("Open saucedemo.com");
        getAndroidDriver().get("https://www.saucedemo.com");
        annotate("Check if username input present");
        Assert.assertTrue(getAndroidDriver().findElement(By.cssSelector("#user-name")).isDisplayed());
        annotate("Check if password input present");
        Assert.assertTrue(getAndroidDriver().findElement(By.cssSelector("#password")).isDisplayed());
    }
}