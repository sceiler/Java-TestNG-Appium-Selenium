package com.saucelabs.yy.Visual;

import com.saucelabs.yy.Tests.SuperTestBase;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.net.MalformedURLException;

public class TestBase extends SuperTestBase {

    public String buildTag = System.getenv("BUILD_TAG");
    private ThreadLocal<String> sessionId = new ThreadLocal<>();

    @DataProvider(name = "RDC", parallel = true)
    public static Object[][] RDCBrowserDataProvider(Method testMethod) {
        return new Object[][]{
                new Object[]{"iOS", "iPhone_SE_2020_14_POC18", "14"},
                new Object[]{"Android", "Samsung_Galaxy_S20_POC38", "10"}
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

        capabilities.setCapability("platformName", platformName);
        capabilities.setCapability("appium:platformVersion", platformVersion);
        capabilities.setCapability("appium:deviceName", deviceName);

        if (platformName.equals("Android")) {
            capabilities.setCapability("appium:app", "storage:filename=Android-MyDemoAppRN.1.1.0.build-226.apk");
        } else {
            capabilities.setCapability("appium:app", "storage:filename=iOS-Real-Device-MyRNDemoApp.1.1.0-146.ipa.ipa");
        }

        sauceOptions.setCapability("name", methodName);
        sauceOptions.setCapability("appiumVersion", "1.22.2");
        sauceOptions.setCapability("username", System.getenv("SAUCE_USERNAME"));
        sauceOptions.setCapability("accessKey", System.getenv("SAUCE_ACCESS_KEY"));

        if (buildTag != null) {
            sauceOptions.setCapability("build", buildTag);
        } else {
            sauceOptions.setCapability("build", "YiMin-Local-Java-Appium-Mobile-Visual-" + localBuildTag);
        }

        if (caps != null) {
            capabilities.merge(caps);
        }

        if (platformName.equals("Android")) {
            driver.set(new AndroidDriver(createDriverURL(), capabilities));
        } else {
            driver.set(new IOSDriver(createDriverURL(), capabilities));
        }
        remoteWebDriver.set(new RemoteWebDriver(createDriverURL(), capabilities));
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        ((JavascriptExecutor) driver.get()).executeScript("sauce:job-result=" + (result.isSuccess() ? "passed" : "failed"));
        driver.get().quit();

        if (androidDriver.get() != null) {
            ((JavascriptExecutor) androidDriver.get()).executeScript("sauce:job-result=" + (result.isSuccess() ? "passed" : "failed"));
            androidDriver.get().quit();
        } else if (iosDriver.get() != null) {
            ((JavascriptExecutor) iosDriver.get()).executeScript("sauce:job-result=" + (result.isSuccess() ? "passed" : "failed"));
            iosDriver.get().quit();
        }
    }

    protected void annotate(String text) {
        ((JavascriptExecutor) driver.get()).executeScript("sauce:context=" + text);
    }
}