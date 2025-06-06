package com.saucelabs.yy.Tests.Selenium.Performance;

import com.saucelabs.yy.Tests.Selenium.Constants;
import com.saucelabs.yy.Tests.SuperTestBase;
import com.saucelabs.yy.Utility.RemoteWebDriverExtended;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.time.Duration;
import java.util.Arrays;

public class TestBase extends SuperTestBase {

    public String buildTag = System.getenv("BUILD_TAG");
    private ThreadLocal<String> sessionId = new ThreadLocal<>();
    private ThreadLocal<WebDriverWait> webDriverWait = new ThreadLocal<>();

    @DataProvider(name = "hardCodedBrowsers", parallel = true)
    public static Object[][] sauceBrowserDataProvider(Method testMethod) {
        return new Object[][]{
                new Object[]{Constants.BROWSER.CHROME.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.WINDOWS10.label()},
                new Object[]{Constants.BROWSER.CHROME.label(), Constants.VERSION.LATEST1.label(), Constants.PLATFORM.WINDOWS10.label()},
        };
    }

    public RemoteWebDriverExtended getRemoteWebDriver() {
        return remoteWebDriver.get();
    }

    public String getSessionId() {
        return sessionId.get();
    }

    protected void createDriver(String browser, String version, String os, String methodName) throws MalformedURLException {
        MutableCapabilities capabilities = new MutableCapabilities();
        MutableCapabilities sauceOptions = new MutableCapabilities();

        capabilities.setCapability(CapabilityType.BROWSER_NAME, browser);
        capabilities.setCapability(CapabilityType.BROWSER_VERSION, version);
        capabilities.setCapability(CapabilityType.PLATFORM_NAME, os);

        sauceOptions.setCapability("name", methodName);
        sauceOptions.setCapability("tags", Arrays.asList("Performance"));
        sauceOptions.setCapability("extendedDebugging", true);
        sauceOptions.setCapability("capturePerformance", true);

        if (buildTag != null) {
            sauceOptions.setCapability("build", buildTag);
        } else {
            sauceOptions.setCapability("build", "YiMin-Local-Java-Selenium-Web-Performance-" + localBuildTag);
        }

        capabilities.setCapability("sauce:options", sauceOptions);

        remoteWebDriver.set(new RemoteWebDriverExtended(createDriverURL(), capabilities));
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
