package com.saucelabs.yy.Tests.Appium.MobileWeb.EmulatorSimulator;

import com.saucelabs.yy.Tests.SuperTestBase;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.net.MalformedURLException;

public class TestBase extends SuperTestBase {

    public String buildTag = System.getenv("BUILD_TAG");
    private ThreadLocal<String> sessionId = new ThreadLocal<>();

    @DataProvider(name = "EmulatorSimulatorDataProvider", parallel = true)
    public static Object[][] emuSimDataProvider(Method testMethod) {
        return new Object[][]{
                new Object[]{"Android", "Android GoogleAPI Emulator", "10.0"},
                new Object[]{"iOS", "iPhone XS Simulator", "13.4"}
        };
    }

    public String getSessionId() {
        return sessionId.get();
    }

    protected void createDriver(String platformName, String deviceName, String platformVersion, String methodName) throws MalformedURLException {
        createDriver(platformName, deviceName, platformVersion, methodName, null);
    }

    protected void createDriver(String platformName, String deviceName, String methodName, MutableCapabilities caps) throws MalformedURLException {
        createDriver(platformName, deviceName, "", methodName, caps);
    }

    protected void createDriver(String platformName, String deviceName, String platformVersion, String methodName, MutableCapabilities caps) throws MalformedURLException {
        MutableCapabilities capabilities = new MutableCapabilities();
        capabilities.setCapability("platformName", platformName);

        if (!platformVersion.equals("")) {
            capabilities.setCapability("platformVersion", platformVersion);
        }

        capabilities.setCapability("deviceName", deviceName);
        capabilities.setCapability("name", methodName);

        if (platformName.equals("Android")) {
            capabilities.setCapability("browserName", "chrome");
        } else {
            capabilities.setCapability("browserName", "Safari");
        }

        if (caps != null) {
            capabilities.merge(caps);
        }

        if (buildTag != null) {
            capabilities.setCapability("build", buildTag);
        } else {
            capabilities.setCapability("build", "YiMin-Local-Java-Appium-Mobile-Web-EmuSim-" + super.dateTime);
        }

        driver.set(new AppiumDriver<>(createDriverURL(), capabilities));
        driver.get().get("https://www.saucedemo.com");
        driver.get().manage().addCookie(new Cookie("session-username", "standard_user"));
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (driver.get() != null) {
            ((JavascriptExecutor) driver.get()).executeScript("sauce:job-result=" + (result.isSuccess() ? "passed" : "failed"));
            driver.get().quit();
        }
    }

    protected void annotate(String text) {
        ((JavascriptExecutor) driver.get()).executeScript("sauce:context=" + text);
    }
}