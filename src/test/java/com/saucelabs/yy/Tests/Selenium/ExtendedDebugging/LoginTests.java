package com.saucelabs.yy.Tests.Selenium.ExtendedDebugging;

import com.saucelabs.saucerest.DataCenter;
import com.saucelabs.saucerest.SauceREST;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.rmi.UnexpectedException;

public class LoginTests extends TestBase {

    @Test(dataProvider = "hardCodedBrowsers")
    public void blankCredentials(String browser, String version, String os, Method method) throws MalformedURLException, UnexpectedException, InterruptedException {
        SauceREST sauceREST = new SauceREST(username, accesskey, DataCenter.EU);
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
        Thread.sleep(60000);
        sauceREST.downloadHAR(getSessionId(), "/Users/yimin.yang/temp/");
    }

    @Test(dataProvider = "hardCodedBrowsers")
    public void validCredentials(String browser, String version, String os, Method method) throws MalformedURLException, UnexpectedException, InterruptedException {
        SauceREST sauceREST = new SauceREST(username, accesskey, DataCenter.EU);
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
        Thread.sleep(60000);
        sauceREST.downloadHAR(getSessionId(), "/Users/yimin.yang/temp/");
    }
}