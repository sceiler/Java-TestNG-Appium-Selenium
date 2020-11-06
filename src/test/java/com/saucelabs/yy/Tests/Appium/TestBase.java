package com.saucelabs.yy.Tests.Appium;

import com.saucelabs.yy.Tests.SuperTestBase;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
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

    @DataProvider(name = "simulatorBrowserDataProvider", parallel = true)
    public static Object[][] simulatorBrowserDataProvider(Method testMethod) {
        return new Object[][]{
                new Object[]{"iOS", "iPhone XS Simulator", "13.4"},
                new Object[]{"iOS", "iPhone 11 Pro Simulator", "13.2"}
        };
    }

    @DataProvider(name = "RDCBrowserDataProvider", parallel = true)
    public static Object[][] RDCBrowserDataProvider(Method testMethod) {
        return new Object[][]{
                new Object[]{"Android", "Samsung.*"},
                new Object[]{"Android", "Google.*"}
        };
    }

    @DataProvider(name = "EmuSimDataProvider", parallel = true)
    public static Object[][] emuSimDataProvider(Method testMethod) {
        return new Object[][]{
                new Object[]{"Android", "Android GoogleAPI Emulator", "10.0"},
                new Object[]{"iOS", "iPhone XS Simulator", "13.4"}
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
        //capabilities.setCapability("browserName", "chrome");
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
            capabilities.setCapability("build", "YiMin-Local-Java-Appium-Mobile-Web-" + super.dateTime);
        }

        if (platformName.equals("Android")) {
            driver.set(new AndroidDriver(createDriverURL(), capabilities));
            //androidDriver.set(new AndroidDriver(createDriverURL(), capabilities));
        } else {
            driver.set(new IOSDriver(createDriverURL(), capabilities));
            //iosDriver.set(new IOSDriver(createDriverURL(), capabilities));
        }
        //sessionId.set(getAndroidDriver().getSessionId().toString());
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        ((JavascriptExecutor) driver.get()).executeScript("sauce:job-result=" + (result.isSuccess() ? "passed" : "failed"));
        driver.get().quit();

//        if (androidDriver.get() != null) {
//            ((JavascriptExecutor) androidDriver.get()).executeScript("sauce:job-result=" + (result.isSuccess() ? "passed" : "failed"));
//            androidDriver.get().quit();
//        } else if (iosDriver.get() != null) {
//            ((JavascriptExecutor) iosDriver.get()).executeScript("sauce:job-result=" + (result.isSuccess() ? "passed" : "failed"));
//            iosDriver.get().quit();
//        }
    }

    protected void annotate(String text) {
        ((JavascriptExecutor) driver.get()).executeScript("sauce:context=" + text);
    }
}