package com.saucelabs.yy.Tests.Selenium;

import com.saucelabs.yy.Tests.SuperTestBase;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class TestBase extends SuperTestBase {

    public String buildTag = System.getenv("BUILD_TAG");

    @DataProvider(name = "hardCodedBrowsers", parallel = true)
    public static Object[][] sauceBrowserDataProvider(Method testMethod) {
        return new Object[][]{
                new Object[]{Constants.BROWSER.FIREFOX.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.WINDOWS10.label()},
                new Object[]{Constants.BROWSER.FIREFOX.label(), Constants.VERSION.LATEST1.label(), Constants.PLATFORM.WINDOWS10.label()},
                new Object[]{Constants.BROWSER.FIREFOX.label(), Constants.VERSION.LATEST2.label(), Constants.PLATFORM.WINDOWS10.label()},
                new Object[]{Constants.BROWSER.FIREFOX.label(), Constants.VERSION.LATEST2.label(), Constants.PLATFORM.WINDOWS8.label()},
                new Object[]{Constants.BROWSER.CHROME.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.WINDOWS10.label()},
                new Object[]{Constants.BROWSER.FIREFOX.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.WINDOWS81.label()},
                new Object[]{Constants.BROWSER.FIREFOX.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.MACOSCATALINA.label()},
                new Object[]{Constants.BROWSER.FIREFOX.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.MACOSMOJAVE.label()},
                new Object[]{Constants.BROWSER.CHROME.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.WINDOWS81.label()},
                new Object[]{Constants.BROWSER.EDGE.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.WINDOWS10.label()},
                new Object[]{Constants.BROWSER.INTERNETEXPLORER.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.WINDOWS81.label()},
                new Object[]{Constants.BROWSER.SAFARI.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.MACOSCATALINA.label()},
                new Object[]{Constants.BROWSER.SAFARI.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.MACOSHIGHSIERRA.label()},
                new Object[]{Constants.BROWSER.SAFARI.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.MACOSMOJAVE.label()},
                new Object[]{Constants.BROWSER.CHROME.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.MACOSHIGHSIERRA.label()},
                new Object[]{Constants.BROWSER.CHROME.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.MACOSMOJAVE.label()},
                new Object[]{Constants.BROWSER.CHROME.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.MACOSCATALINA.label()},
                new Object[]{Constants.BROWSER.CHROME.label(), Constants.VERSION.LATEST1.label(), Constants.PLATFORM.MACOSHIGHSIERRA.label()},
                new Object[]{Constants.BROWSER.CHROME.label(), Constants.VERSION.LATEST1.label(), Constants.PLATFORM.MACOSMOJAVE.label()},
                new Object[]{Constants.BROWSER.CHROME.label(), Constants.VERSION.LATEST1.label(), Constants.PLATFORM.MACOSCATALINA.label()},
        };
    }

    protected void createDriver(String browser, String version, String os, String methodName) throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability(CapabilityType.BROWSER_NAME, browser);
        capabilities.setCapability(CapabilityType.VERSION, version);
        capabilities.setCapability(CapabilityType.PLATFORM, os);
        capabilities.setCapability("name", methodName);
        capabilities.setCapability("tags", Arrays.asList(""));

        if (buildTag != null) {
            capabilities.setCapability("build", buildTag);
        } else {
            capabilities.setCapability("build", "YiMin-Local-Java-Selenium-Web-" + super.dateTime);
        }

        remoteWebDriver.set(new RemoteWebDriver(createDriverURL(), capabilities));
        //remoteWebDriver.get().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        remoteWebDriver.get().get("https://www.saucedemo.com");
        remoteWebDriver.get().manage().addCookie(new Cookie("session-username", "standard_user"));
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        ((JavascriptExecutor) getRemoteWebDriver()).executeScript("sauce:job-result=" + (result.isSuccess() ? "passed" : "failed"));
        remoteWebDriver.get().quit();
    }

    protected void annotate(String text) {
        ((JavascriptExecutor) getRemoteWebDriver()).executeScript("sauce:context=" + text);
    }
}
