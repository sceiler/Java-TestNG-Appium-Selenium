package com.saucelabs.yy.Tests.Selenium.ExtendedDebugging;

import com.saucelabs.yy.Tests.Selenium.Constants;
import com.saucelabs.yy.Tests.SuperTestBase;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.time.Duration;

public class TestBase extends SuperTestBase {

    public String buildTag = System.getenv("BUILD_TAG");
    private ThreadLocal<String> sessionId = new ThreadLocal<>();
    private ThreadLocal<WebDriverWait> webDriverWait = new ThreadLocal<>();

    @DataProvider(name = "hardCodedBrowsers", parallel = true)
    public static Object[][] sauceBrowserDataProvider(Method testMethod) {
        return new Object[][]{
                new Object[]{Constants.BROWSER.FIREFOX.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.WINDOWS10.label()},
                new Object[]{Constants.BROWSER.CHROME.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.WINDOWS10.label()}
        };
    }

    public RemoteWebDriver getRemoteWebDriver() {
        return remoteWebDriver.get();
    }

    public String getSessionId() {
        return sessionId.get();
    }

    protected void createDriver(String browser, String version, String os, String methodName) throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability(CapabilityType.BROWSER_NAME, browser);
        capabilities.setCapability(CapabilityType.BROWSER_VERSION, version);
        //capabilities.setCapability(CapabilityType.PLATFORM, os);
        capabilities.setCapability("extendedDebugging", true);
        capabilities.setCapability("name", methodName);

        if (buildTag != null) {
            capabilities.setCapability("build", buildTag);
        } else {
            capabilities.setCapability("build", "YiMin-Local-Java-Selenium-Web-ExtendedDebugging-" + localBuildTag);
        }

        remoteWebDriver.set(new RemoteWebDriver(createDriverURL(), capabilities));
        webDriverWait.set(new WebDriverWait(remoteWebDriver.get(), Duration.ofSeconds(10)));
        sessionId.set(getRemoteWebDriver().getSessionId().toString());
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
