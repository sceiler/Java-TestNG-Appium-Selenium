package com.saucelabs.yy.Tests.Appium.SwagLabsApp.EmulatorSimulator;

import com.saucelabs.yy.Tests.SuperTestBase;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.remote.CapabilityType;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.Objects;

public class TestBase extends SuperTestBase {

    public String buildTag = System.getenv("BUILD_TAG");
    private ThreadLocal<String> sessionId = new ThreadLocal<>();

    @DataProvider(name = "EmulatorSimulatorDataProvider", parallel = true)
    public static Object[][] emuSimDataProvider(Method testMethod) {
        return new Object[][]{
                new Object[]{"Android", "Android GoogleAPI Emulator", "11.0"},
                new Object[]{"Android", "Android GoogleAPI Emulator", "10.0"},
                new Object[]{"Android", "Google Pixel 3a XL GoogleAPI Emulator", "11.0"},
                new Object[]{"Android", "Google Pixel 3a GoogleAPI Emulator", "10.0"},
                new Object[]{"iOS", "iPhone 12 Simulator", "14.3"},
                new Object[]{"iOS", "iPhone 12 mini Simulator", "14.3"},
                new Object[]{"iOS", "iPhone 12 Pro Simulator", "14.3"},
                new Object[]{"iOS", "iPhone 12 Pro Max Simulator", "14.3"},
                new Object[]{"iOS", "iPhone 11 Simulator", "14.3"},
                new Object[]{"iOS", "iPhone 11 Simulator", "14.0"},
                new Object[]{"iOS", "iPhone 11 Pro Simulator", "14.3"},
                new Object[]{"iOS", "iPhone 11 Pro Simulator", "13.4"},
                new Object[]{"iOS", "iPhone 11 Pro Max Simulator", "14.3"},
                new Object[]{"iOS", "iPhone 11 Pro Max Simulator", "13.2"},
                new Object[]{"iOS", "iPhone XS Simulator", "14.3"},
                new Object[]{"iOS", "iPhone XS Simulator", "12.0"},
                new Object[]{"iOS", "iPhone XS Simulator", "13.0"},
                new Object[]{"iOS", "iPhone XS Max Simulator", "14.3"},
                new Object[]{"iOS", "iPhone XS Max Simulator", "13.4"},
                new Object[]{"iOS", "iPhone XR Simulator", "14.3"},
                new Object[]{"iOS", "iPhone XR Simulator", "14.0"},
                new Object[]{"iOS", "iPhone x Simulator", "14.3"},
                new Object[]{"iOS", "iPhone Simulator", "14.3"},
                new Object[]{"iOS", "iPhone Simulator", "11.0"},
                new Object[]{"iOS", "iPad Pro (11 inch) (2nd generation) Simulator", "14.3"},
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

        capabilities.setCapability(CapabilityType.PLATFORM_NAME, platformName);
        capabilities.setCapability("appium:appiumVersion", "1.20.1");

        if (!platformVersion.equals("")) {
            capabilities.setCapability("appium:platformVersion", platformVersion);
        }

        capabilities.setCapability("appium:deviceName", deviceName);
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "");

        if (platformName.equals("Android")) {
            capabilities.setCapability("app", "storage:filename=Android.SauceLabs.Mobile.Sample.app.2.7.1.apk");
            capabilities.setCapability("appWaitActivity", "com.swaglabsmobileapp.MainActivity");
        } else if (platformName.equals("iOS")) {
            capabilities.setCapability("app", "storage:filename=iOS.Simulator.SauceLabs.Mobile.Sample.app.2.7.1.zip");
        }

        if (caps != null) {
            capabilities.merge(caps);
        }

        sauceOptions.setCapability("name", methodName);
        sauceOptions.setCapability("build", Objects.requireNonNullElseGet(buildTag, () -> "YiMin-Local-Java-Appium-Mobile-App-EmuSim-" + localBuildTag));

        capabilities.setCapability("sauce:options", sauceOptions);

        driver.set(new AppiumDriver(createDriverURL(), capabilities));
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (driver.get() != null) {
            ((JavascriptExecutor) driver.get()).executeScript("sauce:job-result=" + (result.isSuccess() ? "passed" : "failed"));
            driver.get().quit();
        }
    }

    protected void annotate(String text) {
        ((JavascriptExecutor) driver.get()).executeScript("sauce:context=" + text);
    }
}