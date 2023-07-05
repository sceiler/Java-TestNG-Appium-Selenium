package com.saucelabs.yy.Tests.Appium;

import com.saucelabs.yy.Tests.SuperTestBase;
import io.appium.java_client.AppiumDriver;
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
    public static Object[][] devicesDataProvider() {
        String configValue = config.get();

        return switch (configValue) {
            case "OnlyAndroid" -> new Object[][]{
                    {"Android", ".*", "13"},
                    {"Android", ".*", "12"}
            };
            case "OnlyiOS" -> new Object[][]{
                    {"iOS", ".*", "16"},
                    {"iOS", ".*", "15"}
            };
            case "OnlySimulator" -> new Object[][]{
                    {"iOS", "iPhone 14 Simulator", "16.2"},
                    {"iOS", "iPhone 13 Simulator", "15.5"}
            };
            case "OnlyEmulator" -> new Object[][]{
                    {"Android", "Google Pixel 4a (5G) GoogleAPI Emulator", "13.0"},
                    {"Android", "Google Pixel 4a (5G) GoogleAPI Emulator", "12.0"}
            };
            default -> new Object[][]{
                    {"Android", ".*", "13"},
                    {"Android", ".*", "12"},
                    {"iOS", ".*", "16"},
                    {"iOS", ".*", "15"}
            };
        };
    }

    protected void createDriver(String platformName, String deviceName, String platformVersion, boolean isSimulator, boolean isAppTest, String testMethod, String appFileName) throws MalformedURLException {
        MutableCapabilities caps = new MutableCapabilities();
        MutableCapabilities sauceOptions = new MutableCapabilities();

        caps.setCapability("appium:deviceName", deviceName);
        caps.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
        caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
        sauceOptions.setCapability("name", testMethod);

        if (isAppTest) {
            if (platformName.equalsIgnoreCase(MobilePlatform.ANDROID)) {
                String appPath = appFileName.isEmpty() ? "storage:filename=" + APK : "storage:filename=" + appFileName;
                caps.setCapability("appium:app", appPath);
                caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
            } else if (platformName.equalsIgnoreCase(MobilePlatform.IOS)) {
                String appPath = appFileName.isEmpty() ? "storage:filename=" + (isSimulator ? ZIP : IPA) : "storage:filename=" + appFileName;
                caps.setCapability("appium:app", appPath);
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

        sauceOptions.setCapability("build", buildTag != null ? buildTag : "YiMin-Local-Java-Appium-" + localBuildTag);
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
        createDriver(platformName, deviceName, platformVersion, false, true, testMethod, "");
    }

    @Parameters({"config"})
    @BeforeClass
    public void setup(String config) {
        TestBase.config.set(config);
        System.out.println(TestBase.config.get());
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        JavascriptExecutor executor = null;
        AppiumDriver driverInstance;

        if (platform.equalsIgnoreCase(MobilePlatform.ANDROID)) {
            driverInstance = getAndroidDriver();
        } else if (platform.equalsIgnoreCase(MobilePlatform.IOS)) {
            driverInstance = getIOSDriver();
        } else {
            driverInstance = getDriver();
        }

        if (driverInstance != null) {
            executor = driverInstance;
        }

        try {
            if (executor != null) {
                executor.executeScript("sauce:job-result=" + (result.isSuccess() ? "passed" : "failed"));
            }
        } finally {
            if (driverInstance != null) {
                driverInstance.quit();
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