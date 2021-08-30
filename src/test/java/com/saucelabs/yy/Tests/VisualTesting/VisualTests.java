package com.saucelabs.yy.Tests.VisualTesting;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;

public class VisualTests extends TestBase {

    @Test(dataProvider = "singleBrowser")
    public void SauceDemo(String browser, String version, String os, Method method) throws MalformedURLException {
        createDriver(browser, version, os, method.getName());
        annotate("Open saucedemo.com");
        getRemoteWebDriver().get("https://www.saucedemo.com");
        annotate("Sending valid username");
        getRemoteWebDriver().findElement(By.cssSelector("#user-name")).sendKeys("standard_user");
        annotate("Sending valid password");
        getRemoteWebDriver().findElement(By.cssSelector("#password")).sendKeys("secret_sauce");
        javascriptExecutor.get().executeScript("/*@visual.snapshot*/", "Swag Labs - Login Page");
        annotate("Clicking login button");
        getRemoteWebDriver().findElement(By.cssSelector("#login-button")).click();

        javascriptExecutor.get().executeScript("/*@visual.snapshot*/", "Swag Labs - Inventory");
    }

    @Test(dataProvider = "singleBrowser")
    public void GoogleMaps(String browser, String version, String os, Method method) throws MalformedURLException, InterruptedException {
        createDriver(browser, version, os, method.getName());
        getRemoteWebDriver().get("https://www.google.com/maps");
        javascriptExecutor.get().executeScript("/*@visual.snapshot*/", "Google Maps - Init");
        getRemoteWebDriver().findElement(By.cssSelector("#searchboxinput")).sendKeys("Frankfurt");
        getRemoteWebDriver().findElement(By.cssSelector("#searchboxinput")).sendKeys(Keys.ENTER);
        Thread.sleep(5000);
        javascriptExecutor.get().executeScript("/*@visual.snapshot*/", "Google Maps - Result");
    }
}
