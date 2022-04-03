package com.saucelabs.yy.Tests.Appium.SwagLabsApp.RealDevice;

import com.saucelabs.yy.Tests.SuperTestBase;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.time.Duration;

import static com.saucelabs.yy.Tests.Appium.TestBase.APK;
import static com.saucelabs.yy.Tests.Appium.TestBase.IPA;

public class TestBase extends SuperTestBase {

    public String buildTag = System.getenv("BUILD_TAG");

    private ThreadLocal<String> sessionId = new ThreadLocal<>();

    @DataProvider(name = "AndroidRealDevices", parallel = true)
    public static Object[][] androidRealDevicesDataProvider(Method testMethod) {
        return new Object[][]{
                new Object[]{"Android", ".*"}
        };
    }

    @DataProvider(name = "iOSRealDevices", parallel = true)
    public static Object[][] iOSRealDevicesDataProvider(Method testMethod) {
        return new Object[][]{
                new Object[]{"iOS", "iPhone.*"}
        };
    }

    public String getSessionId() {
        return sessionId.get();
    }

    protected void createDriver(String platformName, String deviceName, String testMethod) throws MalformedURLException {
        MutableCapabilities caps = new MutableCapabilities();
        MutableCapabilities sauceOptions = new MutableCapabilities();

        caps.setCapability("appium:deviceName", deviceName);
        caps.setCapability("platformName", platformName);
        sauceOptions.setCapability("name", testMethod);

        if (platformName.equals("Android")) {
            caps.setCapability("appium:app", "storage:filename=" + IPA);
        } else if (platformName.equals("iOS")) {
            caps.setCapability("appium:app", "storage:filename=" + APK);
        }

        if (buildTag != null) {
            sauceOptions.setCapability("build", buildTag);
        } else {
            sauceOptions.setCapability("build", "YiMin-Local-Java-Appium-Mobile-App-" + localBuildTag);
        }

        caps.setCapability("sauce:options", sauceOptions);

        driver.set(new AppiumDriver(createDriverURL(), caps));
        sessionId.set(driver.get().getSessionId().toString());
        driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    protected void createJWPDriver(String platformName, String deviceName, String testMethod) throws MalformedURLException {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("deviceName", deviceName);
        desiredCapabilities.setCapability("platformName", platformName);
        desiredCapabilities.setCapability("name", testMethod);
        desiredCapabilities.setCapability("appiumVersion", "1.21.0");

        if (platformName.equals("Android")) {
            desiredCapabilities.setCapability("app", "storage:filename=" + APK);
        } else if (platformName.equals("iOS")) {
            desiredCapabilities.setCapability("app", "storage:filename=" + IPA);
        }

        if (buildTag != null) {
            desiredCapabilities.setCapability("build", buildTag);
        } else {
            desiredCapabilities.setCapability("build", "YiMin-Local-Java-Appium-Mobile-App-" + localBuildTag);
        }

        driver.set(new AppiumDriver(createDriverURL(), desiredCapabilities));
        sessionId.set(driver.get().getSessionId().toString());
        driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        ((JavascriptExecutor) getDriver()).executeScript("sauce:job-result=" + (result.isSuccess() ? "passed" : "failed"));
        getDriver().quit();
    }
}