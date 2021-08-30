package com.saucelabs.yy.Tests.Appium.SwagLabsApp.RealDevice;

import com.saucelabs.yy.Tests.SuperTestBase;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

public class TestBase extends SuperTestBase {

    public String buildTag = System.getenv("BUILD_TAG");
    public final String ANDROID_FILE_NAME = "Android.SauceLabs.Mobile.Sample.app.2.7.1.apk";
    public final String IOS_FILE_NAME = "iOS.RealDevice.SauceLabs.Mobile.Sample.app.2.7.1.ipa";

    private ThreadLocal<String> sessionId = new ThreadLocal<>();

    @DataProvider(name = "AndroidRealDevices", parallel = true)
    public static Object[][] androidRealDevicesDataProvider(Method testMethod) {
        return new Object[][]{
                new Object[]{"Android", ".*"},
                new Object[]{"Android", ".*"},
                new Object[]{"Android", ".*"},
                new Object[]{"Android", ".*"}
        };
    }

    @DataProvider(name = "iOSRealDevices", parallel = true)
    public static Object[][] iOSRealDevicesDataProvider(Method testMethod) {
        return new Object[][]{
                new Object[]{"iOS", "iPhone.*"},
                new Object[]{"iOS", "iPhone.*"},
                new Object[]{"iOS", "iPhone.*"},
                new Object[]{"iOS", "iPhone.*"}
        };
    }

    public String getSessionId() {
        return sessionId.get();
    }

    protected void createDriver(String platformName, String deviceName, String testMethod) throws MalformedURLException {
        MutableCapabilities caps = new MutableCapabilities();
        caps.setCapability("username", username);
        caps.setCapability("accessKey", accesskey);
        caps.setCapability("deviceName", deviceName);
        caps.setCapability("browserName", "");
        caps.setCapability("platformName", platformName);
        caps.setCapability("name", testMethod);

        if (platformName.equals("Android")) {
            caps.setCapability("app", "storage:filename=" + ANDROID_FILE_NAME);
        } else if (platformName.equals("iOS")) {
            caps.setCapability("app", "storage:filename=" + IOS_FILE_NAME);
        }

        if (buildTag != null) {
            caps.setCapability("build", buildTag);
        } else {
            caps.setCapability("build", "YiMin-Local-Java-Appium-Mobile-App-" + localBuildTag);
        }

        driver.set(new AppiumDriver<>(createDriverURL(), caps));
        sessionId.set(driver.get().getSessionId().toString());
        driver.get().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        ((JavascriptExecutor) getDriver()).executeScript("sauce:job-result=" + (result.isSuccess() ? "passed" : "failed"));
        getDriver().quit();
    }
}