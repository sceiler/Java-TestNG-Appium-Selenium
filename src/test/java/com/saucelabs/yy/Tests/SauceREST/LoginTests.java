package com.saucelabs.yy.Tests.SauceREST;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.rmi.UnexpectedException;

public class LoginTests extends TestBase {

    @Test(dataProvider = "singleBrowser")
    public void blankCredentials(String browser, String version, String os, Method method) throws MalformedURLException, UnexpectedException {
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
}