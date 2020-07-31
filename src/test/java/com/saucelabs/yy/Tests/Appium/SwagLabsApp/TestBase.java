package com.saucelabs.yy.Tests.Appium.SwagLabsApp;

import com.saucelabs.yy.Tests.SuperTestBase;

import io.appium.java_client.android.AndroidDriver;
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
    public final String APK_FILE_NAME = "Android.SauceLabs.Mobile.Sample.app.2.3.0.apk";

    private ThreadLocal<String> sessionId = new ThreadLocal<>();

    @DataProvider(name = "hardCodedBrowsers", parallel = true)
    public static Object[][] sauceBrowserDataProvider(Method testMethod) {
        return new Object[][]{
                new Object[]{"Android", "Google.*"},
                new Object[]{"Android", "Google.*"}
        };
    }

    public String getSessionId() {
        return sessionId.get();
    }

    protected void createDriver(String platformName, String deviceName, String testMethod, String apkFileID) throws MalformedURLException {
        System.out.println("createDriver()");
        MutableCapabilities caps = new MutableCapabilities();
        caps.setCapability("username", username);
        caps.setCapability("accessKey", accesskey);
        caps.setCapability("deviceName", deviceName);
        caps.setCapability("browserName", "");
        caps.setCapability("platformName", platformName);
        caps.setCapability("app", "storage:" + apkFileID);
        caps.setCapability("name", testMethod);

        if (buildTag != null) {
            caps.setCapability("build", buildTag);
        }

        androidDriver.set(new AndroidDriver(createDriverURL(), caps));
        getAndroidDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        ((JavascriptExecutor) getAndroidDriver()).executeScript("sauce:job-result=" + (result.isSuccess() ? "passed" : "failed"));
        getAndroidDriver().quit();
    }

    protected void annotate(String text) {
        ((JavascriptExecutor) getAndroidDriver()).executeScript("sauce:context=" + text);
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        ((JavascriptExecutor) getAndroidDriver()).executeScript("sauce:job-result=" + (iTestResult.isSuccess() ? "passed" : "failed"));
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        ((JavascriptExecutor) getAndroidDriver()).executeScript("sauce:job-result=" + (iTestResult.isSuccess() ? "passed" : "failed"));
    }
}