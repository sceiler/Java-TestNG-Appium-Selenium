package com.saucelabs.yy.Tests.Appium.MobileWeb.EmulatorSimulator;

import com.saucelabs.yy.Tests.SuperTestBase;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.remote.CapabilityType;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

public class TestBase extends SuperTestBase {

    public String buildTag = System.getenv("BUILD_TAG");
    private ThreadLocal<String> sessionId = new ThreadLocal<>();

    @DataProvider(name = "EmulatorSimulatorDataProvider", parallel = true)
    public static Object[][] emuSimDataProvider(Method testMethod) {
        return new Object[][]{
                new Object[]{"Android", "Android GoogleAPI Emulator", "11.0"},
                new Object[]{"iOS", "iPhone 12 Simulator", "14.3"}
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
        MutableCapabilities sauceOptions = new MutableCapabilities();
        capabilities.setCapability(CapabilityType.PLATFORM_NAME, platformName);

        if (!platformVersion.equals("")) {
            capabilities.setCapability("appium:platformVersion", platformVersion);
        }

        capabilities.setCapability("appium:deviceName", deviceName);
        sauceOptions.setCapability("name", methodName);

        if (platformName.equals("Android")) {
            capabilities.setCapability(CapabilityType.BROWSER_NAME, "Chrome");
        } else {
            capabilities.setCapability(CapabilityType.BROWSER_NAME, "Safari");
        }

        if (caps != null) {
            capabilities.merge(caps);
        }

        if (buildTag != null) {
            sauceOptions.setCapability("build", buildTag);
        } else {
            sauceOptions.setCapability("build", "YiMin-Local-Java-Appium-Mobile-Web-EmuSim-" + super.dateTime);
        }

        capabilities.setCapability("sauce:options", sauceOptions);

        driver.set(new AppiumDriver<>(createDriverURL(), capabilities));
        driver.get().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
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