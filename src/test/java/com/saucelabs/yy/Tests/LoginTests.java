package com.saucelabs.yy.Tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.rmi.UnexpectedException;

public class LoginTests extends TestBase {

    @Test(dataProvider = "hardCodedBrowsers")
    public void invalidCredentials(String browser, String version, String os, Method method) throws MalformedURLException, UnexpectedException {
        createDriver(browser, version, os, method.getName());
        getWebDriver().get("https://www.saucedemo.com");
        getWebDriver().findElement(By.id("user-name")).sendKeys("bad");
        getWebDriver().findElement(By.id("password")).sendKeys("bad");
        getWebDriver().findElement(By.className("btn_action")).click();

        Assert.assertTrue(getWebDriver().findElements(By.className("error-button")).size() > 0);
    }

    @Test(dataProvider = "hardCodedBrowsers")
    public void blankCredentials(String browser, String version, String os, Method method) throws MalformedURLException, UnexpectedException {
        createDriver(browser, version, os, method.getName());
        getWebDriver().get("https://www.saucedemo.com");
        getWebDriver().findElement(By.id("user-name")).sendKeys("");
        getWebDriver().findElement(By.id("password")).sendKeys("");
        getWebDriver().findElement(By.className("btn_action")).click();

        Assert.assertTrue(getWebDriver().findElements(By.className("error-button")).size() > 0);
    }

    @Test(dataProvider = "hardCodedBrowsers")
    public void validCredentials(String browser, String version, String os, Method method) throws MalformedURLException, UnexpectedException {
        createDriver(browser, version, os, method.getName());
        getWebDriver().get("https://www.saucedemo.com");
        getWebDriver().findElement(By.id("user-name")).sendKeys("standard_user");
        getWebDriver().findElement(By.id("password")).sendKeys("secret_sauce");
        getWebDriver().findElement(By.className("btn_action")).click();

        Assert.assertTrue(getWebDriver().getCurrentUrl().contains("inventory"));
    }
}