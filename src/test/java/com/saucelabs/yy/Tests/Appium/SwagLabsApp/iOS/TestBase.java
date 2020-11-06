package com.saucelabs.yy.Tests.Appium.SwagLabsApp.iOS;

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
import java.util.concurrent.TimeUnit;

public class TestBase extends SuperTestBase {

    public String buildTag = System.getenv("BUILD_TAG");
    public final String APK_FILE_NAME = "e867fa19-3f85-4bde-af8f-5f2655230a9a";

    private ThreadLocal<String> sessionId = new ThreadLocal<>();

    @DataProvider(name = "iOSRealDevices", parallel = true)
    public static Object[][] iosRealDevices(Method testMethod) {
        return new Object[][]{
                new Object[]{"iOS", ".*"},
                new Object[]{"iOS", ".*"}
        };
    }

    public String getSessionId() {
        return sessionId.get();
    }

    protected void createDriver(String platformName, String deviceName, String testMethod, String apkFileID) throws MalformedURLException {
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
        } else {
            caps.setCapability("build", "YiMin-Local-Java-Appium-SwagLabs-App-iOS-" + super.dateTime);
        }

        iosDriver.set(new IOSDriver(createDriverURL(), caps));
        sessionId.set(iosDriver.get().getSessionId().toString());
        System.out.println("Session id:" + sessionId.get());
        getIOSDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        ((JavascriptExecutor) getIOSDriver()).executeScript("sauce:job-result=" + (result.isSuccess() ? "passed" : "failed"));
        getIOSDriver().quit();
    }

    protected void annotate(String text) {
        ((JavascriptExecutor) getIOSDriver()).executeScript("sauce:context=" + text);
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        ((JavascriptExecutor) getIOSDriver()).executeScript("sauce:job-result=" + (iTestResult.isSuccess() ? "passed" : "failed"));
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        ((JavascriptExecutor) getIOSDriver()).executeScript("sauce:job-result=" + (iTestResult.isSuccess() ? "passed" : "failed"));
    }
}