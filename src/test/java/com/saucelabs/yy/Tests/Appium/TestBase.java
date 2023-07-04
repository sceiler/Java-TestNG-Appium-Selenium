package com.saucelabs.yy.Tests.Appium;

import com.saucelabs.yy.Tests.SuperTestBase;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.remote.CapabilityType;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;
import java.time.Duration;

public abstract class TestBase extends SuperTestBase {

    public static final String IPA = "MyRNDemoApp-1.5.0.ipa";
    public static final String ZIP = "MyRNDemoApp-1.5.0.zip";
    public static final String APK = "MyRNDemoApp-1.5.0.apk";

    public static final String IPAOld = "MyRNDemoApp-1.3.0.ipa";
    public static final String ZIPOld = "MyRNDemoApp-1.3.0.zip";
    public static final String APKOld = "MyRNDemoApp-1.3.0.apk";

    public String buildTag = System.getenv("BUILD_TAG");
    protected ThreadLocal<String> sessionId = new ThreadLocal<>();
    protected static ThreadLocal<String> config = new ThreadLocal<>();
    protected String platform = "";

    @DataProvider(name = "Devices", parallel = true)
    public static Object[][] Devices() {
        Object[][] configs;

        switch (config.get()) {
            case "OnlyAndroid":
                configs = new Object[][]{
                        new Object[]{"Android", ".*", "13"},
                        new Object[]{"Android", ".*", "12"}
                };
                return configs;
            case "OnlyiOS":
                configs = new Object[][]{
                        new Object[]{"iOS", ".*", "16"},
                        new Object[]{"iOS", ".*", "15"}
                };
                return configs;
            case "OnlySimulator":
                configs = new Object[][]{
                        new Object[]{"iOS", "iPhone 14 Simulator", "16.2"},
                        new Object[]{"iOS", "iPhone 13 Simulator", "15.5"},
                };
                return configs;
            case "OnlyEmulator":
                configs = new Object[][]{
                        new Object[]{"Android", "Google Pixel 4a (5G) GoogleAPI Emulator", "13.0"},
                        new Object[]{"Android", "Google Pixel 4a (5G) GoogleAPI Emulator", "12.0"},
                };
                return configs;
            default:
                configs = new Object[][]{
                        new Object[]{"Android", ".*", "13"},
                        new Object[]{"Android", ".*", "12"},
                        new Object[]{"iOS", ".*", "16"},
                        new Object[]{"iOS", ".*", "15"}
                };
                return configs;
        }
    }

    protected void createDriver(String platformName, String deviceName, String platformVersion, boolean isSimulator, boolean isAppTest, String testMethod) throws MalformedURLException {
        MutableCapabilities caps = new MutableCapabilities();
        MutableCapabilities sauceOptions = new MutableCapabilities();

        caps.setCapability("appium:deviceName", deviceName);
        caps.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
        caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
        sauceOptions.setCapability("name", testMethod);

        if (isAppTest) {
            if (platformName.equalsIgnoreCase(MobilePlatform.ANDROID)) {
                caps.setCapability("appium:app", "storage:filename=" + APK);
                caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
            } else if (platformName.equalsIgnoreCase(MobilePlatform.IOS)) {
                caps.setCapability("appium:app", "storage:filename=" + (isSimulator ? ZIP : IPA));
                caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
            }
        } else {
            if (platformName.equalsIgnoreCase(MobilePlatform.ANDROID)) {
                caps.setCapability(CapabilityType.BROWSER_NAME, "Chrome");
                caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
            } else if (platformName.equalsIgnoreCase(MobilePlatform.IOS)) {
                caps.setCapability(CapabilityType.BROWSER_NAME, "Safari");
                caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
            }
        }

        if (buildTag != null) {
            sauceOptions.setCapability("build", buildTag);
        } else {
            sauceOptions.setCapability("build", "YiMin-Local-Java-Appium-" + localBuildTag);
        }

        caps.setCapability("sauce:options", sauceOptions);

        if (platformName.equalsIgnoreCase(MobilePlatform.ANDROID)) {
            androidDriver.set(new AndroidDriver(createDriverURL(), caps));
            sessionId.set(getAndroidDriver().getSessionId().toString());
            getAndroidDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
            platform = MobilePlatform.ANDROID;
        } else if (platformName.equalsIgnoreCase(MobilePlatform.IOS)) {
            iosDriver.set(new IOSDriver(createDriverURL(), caps));
            sessionId.set(getIOSDriver().getSessionId().toString());
            getIOSDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
            platform = MobilePlatform.IOS;
        }
    }

    protected void createDriver(String platformName, String deviceName, String platformVersion, String testMethod) throws MalformedURLException {
        createDriver(platformName, deviceName, platformVersion, false, true, testMethod);
    }

    @Parameters({"config"})
    @BeforeClass
    public void setup(String config) {
        TestBase.config.set(config);
        System.out.println(TestBase.config.get());
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (platform.equalsIgnoreCase(MobilePlatform.ANDROID)) {
            if (getAndroidDriver() != null) {
                ((JavascriptExecutor) getAndroidDriver()).executeScript("sauce:job-result=" + (result.isSuccess() ? "passed" : "failed"));
                getAndroidDriver().quit();
            } else if (getDriver() != null) {
                ((JavascriptExecutor) getDriver()).executeScript("sauce:job-result=" + (result.isSuccess() ? "passed" : "failed"));
                getDriver().quit();
            }
        } else if (platform.equalsIgnoreCase(MobilePlatform.IOS)) {
            if (getIOSDriver() != null) {
                ((JavascriptExecutor) getIOSDriver()).executeScript("sauce:job-result=" + (result.isSuccess() ? "passed" : "failed"));
                getIOSDriver().quit();
            } else if (getDriver() != null) {
                ((JavascriptExecutor) getDriver()).executeScript("sauce:job-result=" + (result.isSuccess() ? "passed" : "failed"));
                getDriver().quit();
            }
        }
    }

    public void annotate(String text) {
        if (platform.equalsIgnoreCase(MobilePlatform.ANDROID)) {
            if (getAndroidDriver() != null) {
                ((JavascriptExecutor) getAndroidDriver()).executeScript("sauce:context=" + text);
            }
        } else if (platform.equalsIgnoreCase(MobilePlatform.IOS)) {
            if (getIOSDriver() != null) {
                ((JavascriptExecutor) getIOSDriver()).executeScript("sauce:context=" + text);
            }
        }
    }
}