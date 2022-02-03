package com.saucelabs.yy.Tests.VisualTesting;

import com.saucelabs.yy.Tests.Selenium.Constants;
import com.saucelabs.yy.Tests.SuperTestBase;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Arrays;

public class TestBase extends SuperTestBase {

    public String buildTag = System.getenv("BUILD_TAG");

    @DataProvider(name = "hardCodedBrowsers", parallel = true)
    public static Object[][] sauceBrowserDataProvider(Method testMethod) {
        return new Object[][]{
                new Object[]{Constants.BROWSER.CHROME.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.WINDOWS10.label()},
                new Object[]{Constants.BROWSER.CHROME.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.MACOSBIGSUR.label()},

                new Object[]{Constants.BROWSER.FIREFOX.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.WINDOWS10.label()},
                new Object[]{Constants.BROWSER.FIREFOX.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.MACOSBIGSUR.label()},

                new Object[]{Constants.BROWSER.EDGE.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.WINDOWS10.label()},
                new Object[]{Constants.BROWSER.EDGE.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.MACOSBIGSUR.label()},

                new Object[]{Constants.BROWSER.SAFARI.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.MACOSBIGSUR.label()},

                new Object[]{Constants.BROWSER.INTERNETEXPLORER.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.WINDOWS10.label()},
        };
    }

    @DataProvider(name = "singleBrowser", parallel = true)
    public static Object[][] sauceSingleBrowser(Method testMethod) {
        return new Object[][]{
                new Object[]{Constants.BROWSER.CHROME.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.WINDOWS10.label()},
        };
    }

    protected void createDriver(String browser, String version, String os, String methodName) throws MalformedURLException {
        MutableCapabilities capabilities = new MutableCapabilities();
        MutableCapabilities sauceOptions = new MutableCapabilities();
        MutableCapabilities visualOptions = new MutableCapabilities();

        capabilities.setCapability(CapabilityType.BROWSER_NAME, browser);
        capabilities.setCapability(CapabilityType.BROWSER_VERSION, version);
        capabilities.setCapability(CapabilityType.PLATFORM_NAME, os);

        sauceOptions.setCapability("username", username);
        sauceOptions.setCapability("accessKey", accesskey);
        sauceOptions.setCapability("name", methodName);
        sauceOptions.setCapability("tags", Arrays.asList(""));

        if (buildTag != null) {
            sauceOptions.setCapability("build", buildTag);
        } else {
            sauceOptions.setCapability("build", "YiMin-Local-Java-Selenium-Web-Visual-" + localBuildTag);
        }

        visualOptions.setCapability("projectName", "Google Maps");
        visualOptions.setCapability("apiKey", visualAccessKey);
        visualOptions.setCapability("iframes", true);

        capabilities.setCapability("sauce:options", sauceOptions);
        capabilities.setCapability("sauce:visual", visualOptions);

        remoteWebDriver.set(new RemoteWebDriver(new URL("http://hub.screener.io:80/wd/hub"), capabilities));
        remoteWebDriver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
        remoteWebDriver.get().get("https://www.saucedemo.com");
        remoteWebDriver.get().manage().addCookie(new Cookie("session-username", "standard_user"));
        javascriptExecutor.set(remoteWebDriver.get());
        javascriptExecutor.get().executeScript("/*@visual.init*/", "Visual Tests");
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        javascriptExecutor.get().executeScript("sauce:job-result=" + (result.isSuccess() ? "passed" : "failed"));
        javascriptExecutor.get().executeScript("/*@visual.end*/");
        getRemoteWebDriver().quit();
    }

    protected void annotate(String text) {
        ((JavascriptExecutor) getRemoteWebDriver()).executeScript("sauce:context=" + text);
    }
}
