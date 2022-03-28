package com.saucelabs.yy.Tests.Selenium.SimpleTests;

import com.saucelabs.yy.Tests.Selenium.TestBase;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;

public class OpenURLTests extends TestBase {

    @Test(dataProvider = "Browsers")
    public void openSauceDemo(String browser, String version, String os, Method method) throws MalformedURLException {
        createDriver(browser, version, os, method.getName());
        annotate("Open saucedemo.com");
        getRemoteWebDriver().get("https://www.saucedemo.com");

        Assert.assertTrue(getRemoteWebDriver().getTitle().equalsIgnoreCase("Swag Labs"));
    }

    @Test(dataProvider = "Browsers")
    public void openGoogle(String browser, String version, String os, Method method) throws MalformedURLException {
        createDriver(browser, version, os, method.getName());
        annotate("Open google.com");
        getRemoteWebDriver().get("https://www.google.com");

        Assert.assertTrue(getRemoteWebDriver().getTitle().equalsIgnoreCase("Google"));
    }

    @Test(dataProvider = "Browsers")
    public void openApple(String browser, String version, String os, Method method) throws MalformedURLException {
        createDriver(browser, version, os, method.getName());
        annotate("Open apple.com");
        getRemoteWebDriver().get("https://www.apple.com");

        Assert.assertTrue(getRemoteWebDriver().getTitle().equalsIgnoreCase("Apple"));
    }

    @Test(dataProvider = "Browsers")
    public void openInstagram(String browser, String version, String os, Method method) throws MalformedURLException {
        createDriver(browser, version, os, method.getName());
        annotate("Open instagram.com");
        getRemoteWebDriver().get("https://www.instagram.com");

        Assert.assertTrue(getRemoteWebDriver().getTitle().equalsIgnoreCase("Instagram"));
    }
}