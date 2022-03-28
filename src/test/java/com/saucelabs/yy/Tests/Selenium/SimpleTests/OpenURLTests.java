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
        Assert.assertTrue(getRemoteWebDriver().getTitle().contains("Swag Labs"));
    }
}