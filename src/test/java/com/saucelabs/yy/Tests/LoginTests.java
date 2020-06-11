package com.saucelabs.yy.Tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.rmi.UnexpectedException;

public class LoginTests extends TestBase {

    @Test(dataProvider = "hardCodedBrowsers", enabled = false)
    public void invalidCredentials(String browser, String version, String os, Method method) throws MalformedURLException, UnexpectedException {
        createDriver(browser, version, os, method.getName());
        annotate("Open saucedemo.com");
        getWebDriver().get("https://www.saucedemo.com");
        annotate("Sending invalid username");
        getWebDriver().findElement(By.id("user-name")).sendKeys("bad");
        annotate("Sending invalid password");
        getWebDriver().findElement(By.id("password")).sendKeys("bad");
        annotate("Clicking login button");
        getWebDriver().findElement(By.className("btn_action")).click();

        Assert.assertTrue(getWebDriver().findElements(By.className("error-button")).size() > 0);
    }

    @Test(dataProvider = "hardCodedBrowsers", enabled = false)
    public void blankCredentials(String browser, String version, String os, Method method) throws MalformedURLException, UnexpectedException {
        createDriver(browser, version, os, method.getName());
        annotate("Open saucedemo.com");
        getWebDriver().get("https://www.saucedemo.com");
        annotate("Sending blank username");
        getWebDriver().findElement(By.id("user-name")).sendKeys("");
        annotate("Sending blank password");
        getWebDriver().findElement(By.id("password")).sendKeys("");
        annotate("Clicking login button");
        getWebDriver().findElement(By.className("btn_action")).click();

        Assert.assertTrue(getWebDriver().findElements(By.className("error-button")).size() > 0);
    }

    @Test(dataProvider = "hardCodedBrowsers", enabled = false)
    public void validCredentials(String browser, String version, String os, Method method) throws MalformedURLException, UnexpectedException {
        createDriver(browser, version, os, method.getName());
        annotate("Open saucedemo.com");
        getWebDriver().get("https://www.saucedemo.com");
        annotate("Sending valid username");
        getWebDriver().findElement(By.id("user-name")).sendKeys("standard_user");
        annotate("Sending valid password");
        getWebDriver().findElement(By.id("password")).sendKeys("secret_sauce");
        annotate("Clicking login button");
        getWebDriver().findElement(By.className("btn_action")).click();

        Assert.assertTrue(getWebDriver().getCurrentUrl().contains("inventory"));
    }

    @Test
    public void loginUIPresent() throws MalformedURLException, UnexpectedException {
        createDriver("chrome", "latest", "Windows 10", "loginUIPresent");
        annotate("Open saucedemo.com");
        getWebDriver().get("https://www.saucedemo.com");
        annotate("Check if username input present");
        Assert.assertTrue(getWebDriver().findElement(By.id("user-name")).isDisplayed());
        annotate("Check if password input present");
        Assert.assertTrue(getWebDriver().findElement(By.id("password")).isDisplayed());
    }
}