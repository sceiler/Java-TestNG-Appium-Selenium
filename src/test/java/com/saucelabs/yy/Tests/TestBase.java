package com.saucelabs.yy.Tests;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.UnexpectedException;

/**
 * Simple TestNG test which demonstrates being instantiated via a DataProvider in order to supply multiple browser combinations.
 *
 * @author Neil Manvar
 */
public class TestBase {

    public String buildTag = System.getenv("BUILD_TAG");
    public String username = System.getenv("SAUCE_USERNAME");
    public String accesskey = System.getenv("SAUCE_ACCESS_KEY");

    /**
     * ThreadLocal variable which contains the  {@link WebDriver} instance which is used to perform browser interactions with.
     */
    private ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();

    /**
     * ThreadLocal variable which contains the Sauce Job Id.
     */
    private ThreadLocal<String> sessionId = new ThreadLocal<String>();

    private ThreadLocal<WebDriverWait> webDriverWait = new ThreadLocal<>();

    /**
     * DataProvider that explicitly sets the browser combinations to be used.
     *
     * @param testMethod
     * @return Two dimensional array of objects with browser, version, and platform information
     */
    @DataProvider(name = "hardCodedBrowsers", parallel = true)
    public static Object[][] sauceBrowserDataProvider(Method testMethod) {
        return new Object[][]{
//                new Object[]{"MicrosoftEdge", "18.17763", "Windows 10"},
//                new Object[]{"firefox", "latest", "Windows 10"},
//                new Object[]{"internet explorer", "11.0", "Windows 8.1"},
//                new Object[]{"safari", "12", "macOS 10.13"},
//                new Object[]{"chrome", "latest", "macOS 10.13"},
//                new Object[]{"firefox", "latest-1", "Windows 10"},
                new Object[]{Constants.BROWSER.FIREFOX.getNotation(), Constants.VERSION.LATEST.getNotation(), Constants.PLATFORM.WINDOWS10.getNotation()},
                new Object[]{Constants.BROWSER.CHROME.getNotation(), Constants.VERSION.LATEST.getNotation(), Constants.PLATFORM.WINDOWS10.getNotation()},
                new Object[]{Constants.BROWSER.FIREFOX.getNotation(), Constants.VERSION.LATEST.getNotation(), Constants.PLATFORM.WINDOWS81.getNotation()},
                new Object[]{Constants.BROWSER.CHROME.getNotation(), Constants.VERSION.LATEST.getNotation(), Constants.PLATFORM.WINDOWS81.getNotation()},
                new Object[]{Constants.BROWSER.EDGE.getNotation(), Constants.VERSION.LATEST.getNotation(), Constants.PLATFORM.WINDOWS10.getNotation()},
                new Object[]{Constants.BROWSER.INTERNETEXPLORER.getNotation(), Constants.VERSION.LATEST.getNotation(), Constants.PLATFORM.WINDOWS81.getNotation()},
                new Object[]{Constants.BROWSER.SAFARI.getNotation(), Constants.VERSION.LATEST.getNotation(), Constants.PLATFORM.MACOSCATALINA.getNotation()},
                new Object[]{Constants.BROWSER.CHROME.getNotation(), Constants.VERSION.LATEST.getNotation(), Constants.PLATFORM.MACOSMOJAVE.getNotation()},
                new Object[]{Constants.BROWSER.SAFARI.getNotation(), Constants.VERSION.LATEST.getNotation(), Constants.PLATFORM.MACOSHIGHSIERRA.getNotation()}
        };
    }

    /**
     * @return the {@link WebDriver} for the current thread
     */
    public WebDriver getWebDriver() {
        return webDriver.get();
    }

    public WebDriverWait getWebDriverWait() {
        return webDriverWait.get();
    }

    /**
     *
     * @return the Sauce Job id for the current thread
     */
    public String getSessionId() {
        return sessionId.get();
    }

    /**
     * Constructs a new {@link RemoteWebDriver} instance which is configured to use the capabilities defined by the browser,
     * version and os parameters, and which is configured to run against ondemand.saucelabs.com, using
     * the username and access key populated by the {@link #authentication} instance.
     *
     * @param browser Represents the browser to be used as part of the test run.
     * @param version Represents the version of the browser to be used as part of the test run.
     * @param os Represents the operating system to be used as part of the test run.
     * @param methodName Represents the name of the test case that will be used to identify the test on Sauce.
     * @return
     * @throws MalformedURLException if an error occurs parsing the url
     */
    protected void createDriver(String browser, String version, String os, String methodName)
            throws MalformedURLException, UnexpectedException {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        // set desired capabilities to launch appropriate browser on Sauce
        capabilities.setCapability(CapabilityType.BROWSER_NAME, browser);
        capabilities.setCapability(CapabilityType.VERSION, version);
        capabilities.setCapability(CapabilityType.PLATFORM, os);
        capabilities.setCapability("name", methodName);

        if (buildTag != null) {
            capabilities.setCapability("build", buildTag);
        }

        // Launch remote browser and set it as the current thread
        String driverCreationURL = accesskey;

//        webDriver.set(new RemoteWebDriver(
//                new URL("https://" + username + ":" + accesskey + "@ondemand.saucelabs.com/wd/hub"),
//                capabilities));

        webDriver.set(new RemoteWebDriver(new URL(driverCreationURL), capabilities));

        webDriverWait.set(new WebDriverWait(webDriver.get(), 10));

        // set current sessionId
        String id = ((RemoteWebDriver) getWebDriver()).getSessionId().toString();
        sessionId.set(id);
    }

    /**
     * Method that gets invoked after test.
     * Dumps browser log and
     * Closes the browser
     */
    @AfterMethod
    public void tearDown(ITestResult result) throws Exception {
        ((JavascriptExecutor) webDriver.get()).executeScript("sauce:job-result=" + (result.isSuccess() ? "passed" : "failed"));
        webDriver.get().quit();
    }

    protected void annotate(String text) {
        ((JavascriptExecutor) webDriver.get()).executeScript("sauce:context=" + text);
    }
}
