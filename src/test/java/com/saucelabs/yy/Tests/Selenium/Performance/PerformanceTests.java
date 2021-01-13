package com.saucelabs.yy.Tests.Selenium.Performance;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

public class PerformanceTests extends TestBase {

    @Test(dataProvider = "hardCodedBrowsers")
    public void SauceDemo(String browser, String version, String os, Method method) throws MalformedURLException {
        HashMap<String, Object> metrics = new HashMap<>();
        metrics.put("type", "sauce:performance");

        createDriver(browser, version, os, method.getName());
        annotate("Open " + method.getName());
        getRemoteWebDriver().get("https://www.saucedemo.com");

        Map<String, Object> perfMetrics = (Map<String, Object>) ((JavascriptExecutor)getRemoteWebDriver()).executeScript("sauce:log", metrics);
        int loadTime = Integer.parseInt(perfMetrics.get("load").toString());
        Assert.assertTrue(loadTime < 1000);
    }

    @Test(dataProvider = "hardCodedBrowsers")
    public void SauceLabs(String browser, String version, String os, Method method) throws MalformedURLException {
        HashMap<String, Object> metrics = new HashMap<>();
        metrics.put("type", "sauce:performance");

        createDriver(browser, version, os, method.getName());
        annotate("Open " + method.getName());
        getRemoteWebDriver().get("https://www.saucelabs.com");
    }

    @Test(dataProvider = "hardCodedBrowsers")
    public void Amazon(String browser, String version, String os, Method method) throws MalformedURLException {
        HashMap<String, Object> metrics = new HashMap<>();
        metrics.put("type", "sauce:performance");

        createDriver(browser, version, os, method.getName());
        annotate("Open " + method.getName());
        getRemoteWebDriver().get("https://www.amazon.com");
    }

    @Test(dataProvider = "hardCodedBrowsers")
    public void Facebook(String browser, String version, String os, Method method) throws MalformedURLException {
        HashMap<String, Object> metrics = new HashMap<>();
        metrics.put("type", "sauce:performance");

        createDriver(browser, version, os, method.getName());
        annotate("Open " + method.getName());
        getRemoteWebDriver().get("https://www.facebook.com");
    }

    @Test(dataProvider = "hardCodedBrowsers")
    public void Google(String browser, String version, String os, Method method) throws MalformedURLException {
        HashMap<String, Object> metrics = new HashMap<>();
        metrics.put("type", "sauce:performance");

        createDriver(browser, version, os, method.getName());
        annotate("Open " + method.getName());
        getRemoteWebDriver().get("https://www.google.com");
    }

    @Test(dataProvider = "hardCodedBrowsers")
    public void Tesla(String browser, String version, String os, Method method) throws MalformedURLException {
        HashMap<String, Object> metrics = new HashMap<>();
        metrics.put("type", "sauce:performance");

        createDriver(browser, version, os, method.getName());
        annotate("Open " + method.getName());
        getRemoteWebDriver().get("https://www.tesla.com");
    }

    @Test(dataProvider = "hardCodedBrowsers")
    public void Apple(String browser, String version, String os, Method method) throws MalformedURLException {
        HashMap<String, Object> metrics = new HashMap<>();
        metrics.put("type", "sauce:performance");

        createDriver(browser, version, os, method.getName());
        annotate("Open " + method.getName());
        getRemoteWebDriver().get("https://www.apple.com");
    }
}