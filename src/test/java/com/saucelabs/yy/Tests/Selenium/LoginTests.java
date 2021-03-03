package com.saucelabs.yy.Tests.Selenium;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;

public class LoginTests extends TestBase {

    @Test(dataProvider = "hardCodedBrowsers")
    public void invalidCredentials(String browser, String version, String os, Method method) throws MalformedURLException {
        createDriver(browser, version, os, method.getName());
        annotate("Open saucedemo.com");
        getRemoteWebDriver().get("https://www.saucedemo.com");
        annotate("Sending invalid username");
        getRemoteWebDriver().findElement(By.id("user-name")).sendKeys("bad");
        annotate("Sending invalid password");
        getRemoteWebDriver().findElement(By.id("password")).sendKeys("bad");
        annotate("Clicking login button");
        getRemoteWebDriver().findElement(By.className("btn_action")).click();

        Assert.assertTrue(getRemoteWebDriver().findElements(By.className("error-button")).size() > 0);
    }

    @Test(dataProvider = "hardCodedBrowsers")
    public void blankCredentials(String browser, String version, String os, Method method) throws MalformedURLException {
        createDriver(browser, version, os, method.getName());
        annotate("Open saucedemo.com");
        getRemoteWebDriver().get("https://www.saucedemo.com");
        annotate("Sending blank username");
        getRemoteWebDriver().findElement(By.id("user-name")).sendKeys("");
        annotate("Sending blank password");
        getRemoteWebDriver().findElement(By.id("password")).sendKeys("");
        annotate("Clicking login button");
        getRemoteWebDriver().findElement(By.className("btn_action")).click();

        Assert.assertTrue(getRemoteWebDriver().findElements(By.className("error-button")).size() > 0);
    }

    @Test(dataProvider = "hardCodedBrowsers")
    public void validCredentials(String browser, String version, String os, Method method) throws MalformedURLException {
        createDriver(browser, version, os, method.getName());
        annotate("Open saucedemo.com");
        getRemoteWebDriver().get("https://www.saucedemo.com");
        annotate("Sending valid username");
        getRemoteWebDriver().findElement(By.id("user-name")).sendKeys("standard_user");
        annotate("Sending valid password");
        getRemoteWebDriver().findElement(By.id("password")).sendKeys("secret_sauce");
        annotate("Clicking login button");
        getRemoteWebDriver().findElement(By.className("btn_action")).click();

        Assert.assertTrue(getRemoteWebDriver().getCurrentUrl().contains("inventory"));
    }

    @Test(dataProvider = "hardCodedBrowsers")
    public void loginUIPresent(String browser, String version, String os, Method method) throws MalformedURLException {
        createDriver(browser, version, os, method.getName());
        annotate("Open saucedemo.com");
        getRemoteWebDriver().get("https://www.saucedemo.com");
        annotate("Check if username input present");
        Assert.assertTrue(getRemoteWebDriver().findElement(By.id("user-name")).isDisplayed());
        annotate("Check if password input present");
        Assert.assertTrue(getRemoteWebDriver().findElement(By.id("password")).isDisplayed());
    }
}