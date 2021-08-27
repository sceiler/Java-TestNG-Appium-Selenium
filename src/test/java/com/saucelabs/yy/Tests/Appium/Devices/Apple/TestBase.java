package com.saucelabs.yy.Tests.Appium.Devices.Apple;

import com.saucelabs.yy.Tests.Appium.Devices.OCR;
import com.saucelabs.yy.Tests.SuperTestBase;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.remote.CapabilityType;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestBase extends SuperTestBase {
    public String buildTag = System.getenv("BUILD_TAG");
    private ThreadLocal<String> sessionId = new ThreadLocal<>();
    protected ThreadLocal<OCR> ocr = new ThreadLocal<>();
    private ThreadLocal<List<String>> deviceInfo = new ThreadLocal<>();

    @DataProvider(name = "RDCDataProvider", parallel = true)
    public static Object[][] emuSimDataProvider(Method testMethod) {
        return new Object[][]{
                new Object[]{"iOS", ".*", "10"},
                new Object[]{"iOS", ".*", "10"},
                new Object[]{"iOS", ".*", "10"},
                new Object[]{"iOS", ".*", "10"},
                new Object[]{"iOS", ".*", "10"},
                new Object[]{"iOS", ".*", "10"},
                new Object[]{"iOS", ".*", "10"},
                new Object[]{"iOS", ".*", "10"},
                new Object[]{"iOS", ".*", "10"},
                new Object[]{"iOS", ".*", "10"},
                new Object[]{"iOS", ".*", "11"},
                new Object[]{"iOS", ".*", "11"},
                new Object[]{"iOS", ".*", "11"},
                new Object[]{"iOS", ".*", "11"},
                new Object[]{"iOS", ".*", "11"},
                new Object[]{"iOS", ".*", "11"},
                new Object[]{"iOS", ".*", "11"},
                new Object[]{"iOS", ".*", "11"},
                new Object[]{"iOS", ".*", "11"},
                new Object[]{"iOS", ".*", "11"},
                new Object[]{"iOS", ".*", "11"},
                new Object[]{"iOS", ".*", "11"},
                new Object[]{"iOS", ".*", "11"},
                new Object[]{"iOS", ".*", "11"},
                new Object[]{"iOS", ".*", "11"},
                new Object[]{"iOS", ".*", "11"},
                new Object[]{"iOS", ".*", "11"},
                new Object[]{"iOS", ".*", "11"},
                new Object[]{"iOS", ".*", "11"},
                new Object[]{"iOS", ".*", "11"},
                new Object[]{"iOS", ".*", "12"},
                new Object[]{"iOS", ".*", "12"},
                new Object[]{"iOS", ".*", "12"},
                new Object[]{"iOS", ".*", "12"},
                new Object[]{"iOS", ".*", "12"},
                new Object[]{"iOS", ".*", "12"},
                new Object[]{"iOS", ".*", "12"},
                new Object[]{"iOS", ".*", "12"},
                new Object[]{"iOS", ".*", "12"},
                new Object[]{"iOS", ".*", "12"},
                new Object[]{"iOS", ".*", "12"},
                new Object[]{"iOS", ".*", "12"},
                new Object[]{"iOS", ".*", "12"},
                new Object[]{"iOS", ".*", "12"},
                new Object[]{"iOS", ".*", "12"},
                new Object[]{"iOS", ".*", "12"},
                new Object[]{"iOS", ".*", "12"},
                new Object[]{"iOS", ".*", "12"},
                new Object[]{"iOS", ".*", "12"},
                new Object[]{"iOS", ".*", "12"},
                new Object[]{"iOS", ".*", "13"},
                new Object[]{"iOS", ".*", "13"},
                new Object[]{"iOS", ".*", "13"},
                new Object[]{"iOS", ".*", "13"},
                new Object[]{"iOS", ".*", "13"},
                new Object[]{"iOS", ".*", "13"},
                new Object[]{"iOS", ".*", "13"},
                new Object[]{"iOS", ".*", "13"},
                new Object[]{"iOS", ".*", "13"},
                new Object[]{"iOS", ".*", "13"},
                new Object[]{"iOS", ".*", "13"},
                new Object[]{"iOS", ".*", "13"},
                new Object[]{"iOS", ".*", "13"},
                new Object[]{"iOS", ".*", "13"},
                new Object[]{"iOS", ".*", "13"},
                new Object[]{"iOS", ".*", "13"},
                new Object[]{"iOS", ".*", "13"},
                new Object[]{"iOS", ".*", "13"},
                new Object[]{"iOS", ".*", "13"},
                new Object[]{"iOS", ".*", "13"},
                new Object[]{"iOS", ".*", "13"},
                new Object[]{"iOS", ".*", "13"},
                new Object[]{"iOS", ".*", "13"},
                new Object[]{"iOS", ".*", "13"},
                new Object[]{"iOS", ".*", "13"},
                new Object[]{"iOS", ".*", "13"},
                new Object[]{"iOS", ".*", "13"},
                new Object[]{"iOS", ".*", "13"},
                new Object[]{"iOS", ".*", "13"},
                new Object[]{"iOS", ".*", "13"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "14"},
                new Object[]{"iOS", ".*", "15"},
                new Object[]{"iOS", ".*", "15"},
                new Object[]{"iOS", ".*", "15"},
                new Object[]{"iOS", ".*", "15"},
                new Object[]{"iOS", ".*", "15"},
                new Object[]{"iOS", ".*", "15"},
                new Object[]{"iOS", ".*", "15"},
                new Object[]{"iOS", ".*", "15"},
                new Object[]{"iOS", ".*", "15"},
                new Object[]{"iOS", ".*", "15"},
                new Object[]{"iOS", ".*", "15"},
                new Object[]{"iOS", ".*", "15"},
                new Object[]{"iOS", ".*", "15"},
                new Object[]{"iOS", ".*", "15"},
                new Object[]{"iOS", ".*", "15"},
                new Object[]{"iOS", ".*", "15"},
                new Object[]{"iOS", ".*", "15"},
                new Object[]{"iOS", ".*", "15"},
                new Object[]{"iOS", ".*", "15"},
                new Object[]{"iOS", ".*", "15"},
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
        capabilities.setCapability(CapabilityType.PLATFORM_NAME, platformName);

        if (!platformVersion.equals("")) {
            capabilities.setCapability("platformVersion", platformVersion);
        }

        capabilities.setCapability("deviceName", deviceName);
        capabilities.setCapability("name", methodName);

        if (platformName.equals("Android")) {
            capabilities.setCapability(CapabilityType.BROWSER_NAME, "Chrome");
        } else {
            capabilities.setCapability(CapabilityType.BROWSER_NAME, "Safari");
        }

        if (caps != null) {
            capabilities.merge(caps);
        }

        if (buildTag != null) {
            capabilities.setCapability("build", buildTag);
        } else {
            capabilities.setCapability("build", "YiMin-Local-Java-Appium-Device-Check-" + localBuildTag);
        }

        driver.set(new AppiumDriver<>(createDriverURL(), capabilities));
        driver.get().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        ocr.set(new OCR());
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (driver.get() != null) {
            ((JavascriptExecutor) driver.get()).executeScript("sauce:job-result=" + (result.isSuccess() ? "passed" : "failed"));

            if (!result.isSuccess()) {
                var caps = driver.get().getCapabilities();
                String udid = (String) caps.getCapability("udid");
                String deviceName = (String) caps.getCapability("testobject_device");
                String platformVersion = (String) caps.getCapability("platformVersion");
                String url = (String) caps.getCapability("testobject_test_report_url");
                deviceInfo.get().add(String.format("%s,%s,%s,%s", udid, deviceName, platformVersion, url));
            }

            driver.get().quit();
        }
    }

    @AfterSuite
    public void tearDown() {
        for (String text : deviceInfo.get()) {
            System.out.println(text);
        }
    }

}
