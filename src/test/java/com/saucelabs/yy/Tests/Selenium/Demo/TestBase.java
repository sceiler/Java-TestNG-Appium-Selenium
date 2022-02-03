package com.saucelabs.yy.Tests.Selenium.Demo;

import com.saucelabs.yy.Tests.Selenium.Constants;
import com.saucelabs.yy.Tests.SuperTestBase;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.time.Duration;
import java.util.Arrays;

public class TestBase extends SuperTestBase {

    public String buildTag = System.getenv("BUILD_TAG");

    @DataProvider(name = "hardCodedBrowsers", parallel = true)
    public static Object[][] sauceBrowserDataProvider(Method testMethod) {
        return new Object[][]{
                new Object[]{Constants.BROWSER.CHROME.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.WINDOWS10.label()},
                new Object[]{Constants.BROWSER.CHROME.label(), Constants.VERSION.LATEST1.label(), Constants.PLATFORM.WINDOWS10.label()},

                new Object[]{Constants.BROWSER.FIREFOX.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.WINDOWS10.label()},
                new Object[]{Constants.BROWSER.FIREFOX.label(), Constants.VERSION.LATEST1.label(), Constants.PLATFORM.WINDOWS10.label()}
        };
    }

    @DataProvider(name = "singleBrowser", parallel = true)
    public static Object[][] sauceSingleBrowser(Method testMethod) {
        return new Object[][]{
                new Object[]{Constants.BROWSER.CHROME.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.WINDOWS10.label()},
        };
    }

    @DataProvider(name = "twoBrowsers", parallel = true)
    public static Object[][] sauceTwoBrowsers(Method testMethod) {
        return new Object[][]{
                new Object[]{Constants.BROWSER.CHROME.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.WINDOWS10.label()},
                new Object[]{Constants.BROWSER.FIREFOX.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.WINDOWS10.label()},
        };
    }

    @DataProvider(name = "headlessBrowsers", parallel = true)
    public static Object[][] headlessBrowsers(Method testMethod) {
        return new Object[][]{
                new Object[]{Constants.BROWSER.CHROME.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.LINUX.label()},
                new Object[]{Constants.BROWSER.FIREFOX.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.LINUX.label()},
        };
    }

    protected void createDriver(String browser, String version, String os, String methodName) throws MalformedURLException {
        MutableCapabilities capabilities = new MutableCapabilities();
        MutableCapabilities sauceOptions = new MutableCapabilities();

        capabilities.setCapability(CapabilityType.BROWSER_NAME, browser);
        capabilities.setCapability(CapabilityType.BROWSER_VERSION, version);
        capabilities.setCapability(CapabilityType.PLATFORM_NAME, os);

        sauceOptions.setCapability("name", methodName);
        sauceOptions.setCapability("tags", Arrays.asList("SVFLG"));

        if (buildTag != null) {
            sauceOptions.setCapability("build", buildTag);
        } else {
            sauceOptions.setCapability("build", "YiMin-Local-Java-Selenium-Web-SVLFG-" + localBuildTag);
        }

        capabilities.setCapability("sauce:options", sauceOptions);

        remoteWebDriver.set(new RemoteWebDriver(createDriverURL(), capabilities));
        remoteWebDriver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
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
