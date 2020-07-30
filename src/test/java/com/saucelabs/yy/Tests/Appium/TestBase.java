package com.saucelabs.yy.Tests.Appium;

import com.saucelabs.yy.Tests.SuperTestBase;
import io.appium.java_client.android.AndroidDriver;
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

    @DataProvider(name = "emulatorBrowserDataProvider", parallel = true)
    public static Object[][] emulatorBrowserDataProvider(Method testMethod) {
        return new Object[][]{
                new Object[]{"Android", "Samsung Galaxy S9 WQHD GoogleAPI Emulator", "9.0"},
                new Object[]{"Android", "Android GoogleAPI Emulator", "10.0"}
        };
    }

    public AndroidDriver getAndroidDriver() {
        return androidDriver.get();
    }

    public String getSessionId() {
        return sessionId.get();
    }

    protected void createDriver(String platformName, String deviceName, String platformVersion, String methodName) throws MalformedURLException {
        createDriver(platformName, deviceName, platformVersion, methodName, null);
    }

    protected void createDriver(String platformName, String deviceName, String platformVersion, String methodName, MutableCapabilities caps) throws MalformedURLException {
        MutableCapabilities capabilities = new MutableCapabilities();
        capabilities.setCapability("platformName", platformName);
        capabilities.setCapability("platformVersion", platformVersion);
        capabilities.setCapability("deviceName", deviceName);
        capabilities.setCapability("browserName", "chrome");
        capabilities.setCapability("name", methodName);

        if (caps != null) {
            capabilities.merge(caps);
        }

        if (buildTag != null) {
            capabilities.setCapability("build", buildTag);
        }

        androidDriver.set(new AndroidDriver(createDriverURL(), capabilities));
        sessionId.set(getAndroidDriver().getSessionId().toString());
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        ((JavascriptExecutor) androidDriver.get()).executeScript("sauce:job-result=" + (result.isSuccess() ? "passed" : "failed"));
        androidDriver.get().quit();
    }

    protected void annotate(String text) {
        ((JavascriptExecutor) androidDriver.get()).executeScript("sauce:context=" + text);
    }
}