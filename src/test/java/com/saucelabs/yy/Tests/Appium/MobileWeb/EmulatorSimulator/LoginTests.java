package com.saucelabs.yy.Tests.Appium.MobileWeb.EmulatorSimulator;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;

public class LoginTests extends TestBase {

    @Test(dataProvider = "EmulatorSimulatorDataProvider")
    public void invalidCredentials(String platform, String deviceName, String platformVersion, Method methodName) throws MalformedURLException {
        createDriver(platform, deviceName, platformVersion, methodName.getName());
        annotate("Open saucedemo.com");
        getDriver().get("https://www.saucedemo.com");
        annotate("Sending invalid username");
        getDriver().findElement(By.id("user-name")).sendKeys("bad");
        annotate("Sending invalid password");
        getDriver().findElement(By.id("password")).sendKeys("bad");
        annotate("Clicking login button");
        getDriver().findElement(By.className("btn_action")).click();

        Assert.assertTrue(getDriver().findElements(By.className("error-button")).size() > 0);
    }

    @Test(dataProvider = "EmulatorSimulatorDataProvider")
    public void blankCredentials(String platform, String deviceName, String platformVersion, Method methodName) throws MalformedURLException {
        createDriver(platform, deviceName, platformVersion, methodName.getName());
        annotate("Open saucedemo.com");
        getDriver().get("https://www.saucedemo.com");
        annotate("Sending blank username");
        getDriver().findElement(By.id("user-name")).sendKeys("");
        annotate("Sending blank password");
        getDriver().findElement(By.id("password")).sendKeys("");
        annotate("Clicking login button");
        getDriver().findElement(By.className("btn_action")).click();

        Assert.assertTrue(getDriver().findElements(By.className("error-button")).size() > 0);
        Assert.assertTrue(getDriver().findElements(By.className("error-button")).size() > 0);
    }

    @Test(dataProvider = "EmulatorSimulatorDataProvider")
    public void validCredentials(String platform, String deviceName, String platformVersion, Method methodName) throws MalformedURLException {
        createDriver(platform, deviceName, platformVersion, methodName.getName());
        annotate("Open saucedemo.com");
        getDriver().get("https://www.saucedemo.com");
        annotate("Sending valid username");
        getDriver().findElement(By.id("user-name")).sendKeys("standard_user");
        annotate("Sending valid password");
        getDriver().findElement(By.id("password")).sendKeys("secret_sauce");
        annotate("Clicking login button");
        getDriver().findElement(By.className("btn_action")).click();

        Assert.assertTrue(getDriver().getCurrentUrl().contains("inventory"));
    }

    @Test(dataProvider = "EmulatorSimulatorDataProvider")
    public void loginUIPresent(String platform, String deviceName, String platformVersion, Method methodName) throws MalformedURLException {
        createDriver(platform, deviceName, platformVersion, methodName.getName());
        annotate("Open saucedemo.com");
        getDriver().get("https://www.saucedemo.com");
        annotate("Check if username input present");
        Assert.assertTrue(getDriver().findElement(By.id("user-name")).isDisplayed());
        annotate("Check if password input present");
        Assert.assertTrue(getDriver().findElement(By.id("password")).isDisplayed());
    }
}
