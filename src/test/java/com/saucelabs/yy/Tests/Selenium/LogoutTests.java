package com.saucelabs.yy.Tests.Selenium;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;

public class LogoutTests extends TestBase {

    @Test(dataProvider = "Browsers")
    public void passingLogout(String browser, String version, String os, Method method) throws MalformedURLException {
        createDriver(browser, version, os, method.getName());
        annotate("Open menu");
        getRemoteWebDriver().findElement(By.cssSelector("#react-burger-menu-btn")).click();
        annotate("Click logout button");
        getRemoteWebDriver().findElement(By.cssSelector("#logout_sidebar_link")).click();
        Assert.assertTrue(getRemoteWebDriver().findElement(By.cssSelector("#login-button")).isDisplayed());
    }
}
