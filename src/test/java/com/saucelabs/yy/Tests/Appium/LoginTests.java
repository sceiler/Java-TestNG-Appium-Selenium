package com.saucelabs.yy.Tests.Appium;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.rmi.UnexpectedException;

public class LoginTests extends TestBase {

    @Test(dataProvider = "hardCodedBrowsers")
    public void invalidCredentials(String platform, String deviceName, String platformVersion, String appiumVersion,
                                   String orientation, String browserName, Method methodName) throws MalformedURLException, UnexpectedException {

        createDriver(platform, deviceName, browserName, platformVersion, appiumVersion, orientation, methodName.getName());
        annotate("Open saucedemo.com");
        getAndroidDriver().get("https://www.saucedemo.com");
        annotate("Sending invalid username");
        getAndroidDriver().findElement(By.id("user-name")).sendKeys("bad");
        annotate("Sending invalid password");
        getAndroidDriver().findElement(By.id("password")).sendKeys("bad");
        annotate("Clicking login button");
        getAndroidDriver().findElement(By.className("btn_action")).click();

        Assert.assertTrue(getAndroidDriver().findElements(By.className("error-button")).size() > 0);
    }

    @Test(dataProvider = "hardCodedBrowsers")
    public void blankCredentials(String platform, String deviceName, String platformVersion, String appiumVersion,
                                 String orientation, String browserName, Method methodName) throws MalformedURLException, UnexpectedException {
        createDriver(platform, deviceName, browserName, platformVersion, appiumVersion, orientation, methodName.getName());
        annotate("Open saucedemo.com");
        getAndroidDriver().get("https://www.saucedemo.com");
        annotate("Sending blank username");
        getAndroidDriver().findElement(By.id("user-name")).sendKeys("");
        annotate("Sending blank password");
        getAndroidDriver().findElement(By.id("password")).sendKeys("");
        annotate("Clicking login button");
        getAndroidDriver().findElement(By.className("btn_action")).click();

        Assert.assertTrue(getAndroidDriver().findElements(By.className("error-button")).size() > 0);
    }

    @Test(dataProvider = "hardCodedBrowsers")
    public void validCredentials(String platform, String deviceName, String platformVersion, String appiumVersion,
                                 String orientation, String browserName, Method methodName) throws MalformedURLException, UnexpectedException {
        createDriver(platform, deviceName, browserName, platformVersion, appiumVersion, orientation, methodName.getName());
        annotate("Open saucedemo.com");
        getAndroidDriver().get("https://www.saucedemo.com");
        annotate("Sending valid username");
        getAndroidDriver().findElement(By.id("user-name")).sendKeys("standard_user");
        annotate("Sending valid password");
        getAndroidDriver().findElement(By.id("password")).sendKeys("secret_sauce");
        annotate("Clicking login button");
        getAndroidDriver().findElement(By.className("btn_action")).click();

        Assert.assertTrue(getAndroidDriver().getCurrentUrl().contains("inventory"));
    }

    @Test(dataProvider = "hardCodedBrowsers")
    public void loginUIPresent(String platform, String deviceName, String platformVersion, String appiumVersion,
                               String orientation, String browserName, Method methodName) throws MalformedURLException, UnexpectedException {
        createDriver(platform, deviceName, browserName, platformVersion, appiumVersion, orientation, methodName.getName());
        annotate("Open saucedemo.com");
        getAndroidDriver().get("https://www.saucedemo.com");
        annotate("Check if username input present");
        Assert.assertTrue(getAndroidDriver().findElement(By.id("user-name")).isDisplayed());
        annotate("Check if password input present");
        Assert.assertTrue(getAndroidDriver().findElement(By.id("password")).isDisplayed());
    }
}
