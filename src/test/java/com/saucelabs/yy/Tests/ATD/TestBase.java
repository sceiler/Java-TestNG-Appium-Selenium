package com.saucelabs.yy.Tests.ATD;

import com.saucelabs.yy.Tests.SuperTestBase;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TestBase extends SuperTestBase {
    public String buildTag = System.getenv("BUILD_TAG");
    public final String ANDROID_APK_FILE_NAME = "Android.SauceLabs.Mobile.Sample.app.2.7.0.apk";
    public final String IOS_IPA_FILE_NAME = "iOS.Simulator.SauceLabs.Mobile.Sample.app.2.7.0.zip";

    private ThreadLocal<String> sessionId = new ThreadLocal<>();

    @DataProvider(name = "iOSSimulator", parallel = true)
    public static Object[][] iOSSimulator(Method testMethod) {
        return new Object[][]{
                new Object[]{"iOS", "iPhone XS Max Simulator", "12.4", "1.13.0"},
                new Object[]{"iOS", "iPhone XR Simulator", "12.4", "1.13.0"},
                new Object[]{"iOS", "iPhone XS Max Simulator", "13.0", "1.15.0"},
                new Object[]{"iOS", "iPhone XR Simulator", "13.0", "1.15.0"},
                //iOS 12
                //appium 1.14 1.15
        };
    }

    @DataProvider(name = "AndroidEmulator", parallel = true)
    public static Object[][] androidEmulator(Method testMethod) {
        return new Object[][]{
                new Object[]{"Android", "Android GoogleAPI Emulator", "11.0"},
                new Object[]{"Android", "Android GoogleAPI Emulator", "10.0"},
                new Object[]{"Android", "Android GoogleAPI Emulator", "9.0"}
        };
    }

    public String getSessionId() {
        return sessionId.get();
    }

    protected void createDriver(String platformName, String deviceName, String platformVersion, String appiumVersion, String testMethod) throws MalformedURLException {
        MutableCapabilities caps = new MutableCapabilities();
        caps.setCapability("username", username);
        caps.setCapability("accessKey", accesskey);
        caps.setCapability("deviceName", deviceName);
        caps.setCapability("browserName", "");
        caps.setCapability("platformName", platformName);
        caps.setCapability("platformVersion", platformVersion);
        caps.setCapability("name", testMethod);
        caps.setCapability("appiumVersion", appiumVersion);

        if (platformName.equalsIgnoreCase("Android")) {
            caps.setCapability("app", "storage:filename=" + ANDROID_APK_FILE_NAME);
        } else {
            caps.setCapability("app", "storage:filename=" + IOS_IPA_FILE_NAME);
        }

        if (buildTag != null) {
            caps.setCapability("build", buildTag);
        } else {
            caps.setCapability("build", "YiMin-Local-ATD-" + platformName + "-" + super.dateTime);
        }

        driver.set(new AppiumDriver(createDriverURL(), caps));
        sessionId.set(driver.get().getSessionId().toString());
        System.out.println("Session id:" + sessionId.get());
        getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        ((JavascriptExecutor) getDriver()).executeScript("sauce:job-result=" + (result.isSuccess() ? "passed" : "failed"));
        getDriver().quit();
    }

    public void swipeUp() {
        Map<String, Object> params = new HashMap<>();
        params.put("direction", "up");
        ((JavascriptExecutor) getDriver()).executeScript("mobile: swipe", params);
    }
}
