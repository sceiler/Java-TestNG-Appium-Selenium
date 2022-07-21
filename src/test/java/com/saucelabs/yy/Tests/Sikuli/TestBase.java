package com.saucelabs.yy.Tests.Sikuli;

import com.saucelabs.yy.Tests.Selenium.Constants;
import com.saucelabs.yy.Tests.SuperTestBase;
import com.saucelabs.yy.Utility.RemoteWebDriverExtended;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.remote.CapabilityType;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.time.Duration;
import java.util.Collections;

public class TestBase extends SuperTestBase {

    public String buildTag = System.getenv("BUILD_TAG");

    @DataProvider(name = "browsers", parallel = true)
    public static Object[][] browser(Method testMethod) {
        return new Object[][]{
                new Object[]{Constants.BROWSER.CHROME.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.WINDOWS10.label()},
                new Object[]{Constants.BROWSER.CHROME.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.MACOSBIGSUR.label()},
                new Object[]{Constants.BROWSER.FIREFOX.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.WINDOWS10.label()},
                new Object[]{Constants.BROWSER.FIREFOX.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.MACOSBIGSUR.label()},
                new Object[]{Constants.BROWSER.SAFARI.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.MACOSBIGSUR.label()},
        };
    }

    protected void createDriver(String browser, String version, String os, String methodName) throws MalformedURLException {
        MutableCapabilities capabilities = new MutableCapabilities();
        MutableCapabilities sauceOptions = new MutableCapabilities();

        capabilities.setCapability(CapabilityType.BROWSER_NAME, browser);
        capabilities.setCapability(CapabilityType.BROWSER_VERSION, version);
        capabilities.setCapability(CapabilityType.PLATFORM_NAME, os);

        sauceOptions.setCapability("name", methodName);
        sauceOptions.setCapability("tags", Collections.singletonList("Sikuli"));

        if (os.equalsIgnoreCase("macOS 11.00")) {
            sauceOptions.setCapability("screenResolution", "2048x1536");
        } else if (os.equalsIgnoreCase("Windows 10")) {
            sauceOptions.setCapability("screenResolution", "2560x1600");
        }

        if (buildTag != null) {
            sauceOptions.setCapability("build", buildTag);
        } else {
            sauceOptions.setCapability("build", "YiMin-Local-Java-Selenium-Web-Sikuli-" + localBuildTag);
        }

        capabilities.setCapability("sauce:options", sauceOptions);

        remoteWebDriver.set(new RemoteWebDriverExtended(createDriverURL(), capabilities));
        remoteWebDriver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
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
