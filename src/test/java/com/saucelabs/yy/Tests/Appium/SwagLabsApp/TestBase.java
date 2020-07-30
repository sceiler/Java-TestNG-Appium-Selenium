package com.saucelabs.yy.Tests.Appium.SwagLabsApp;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.saucelabs.saucerest.DataCenter;
import com.saucelabs.yy.Tests.SuperTestBase;
import com.saucelabs.yy.Utility.SauceRESTExtension;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;


import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

public class TestBase extends SuperTestBase {

    public String buildTag = System.getenv("BUILD_TAG");
    private final String APK_FILE_NAME = "Android.SauceLabs.Mobile.Sample.app.2.3.0.apk";
    private String APK_FILE_ID = "";

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

    protected void createDriver(String platformName, String deviceName, String testMethod) throws MalformedURLException {
        MutableCapabilities caps = new MutableCapabilities();
        caps.setCapability("username", username);
        caps.setCapability("accessKey", accesskey);
        caps.setCapability("deviceName", deviceName);
        caps.setCapability("browserName", "");
        //caps.setCapability("platformVersion","[10-11]");
        caps.setCapability("platformName", platformName);
        caps.setCapability("app", "storage:" + APK_FILE_ID);
        caps.setCapability("name", testMethod);

        if (buildTag != null) {
            caps.setCapability("build", buildTag);
        }

        androidDriver.set(new AndroidDriver(createDriverURL(), caps));
        getAndroidDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @BeforeTest
    public void setup() {
        SauceRESTExtension sauceRESTExtension = new SauceRESTExtension(username, accesskey, DataCenter.EU);
        String storedFiles = sauceRESTExtension.getStoredFiles();
        JsonArray storedFilesArray = new JsonParser().parse(storedFiles).getAsJsonObject().get("items").getAsJsonArray();

        for (int i = storedFilesArray.size() - 1; i >= 0; i--) {
            String id = storedFilesArray.get(i).getAsJsonObject().get("id").getAsString();
            String name = storedFilesArray.get(i).getAsJsonObject().get("name").getAsString();
            String kind = storedFilesArray.get(i).getAsJsonObject().get("kind").getAsString();

            if (kind.equals("android") && name.equals(APK_FILE_NAME)) {
                APK_FILE_ID = id;
                break;
            }
        }
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