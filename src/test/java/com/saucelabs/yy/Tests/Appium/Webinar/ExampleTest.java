package com.saucelabs.yy.Tests.Appium.Webinar;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.MutableCapabilities;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class ExampleTest {

    @Test
    public void useAPITestAndroid() throws MalformedURLException, InterruptedException {
        MutableCapabilities capabilities = new MutableCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "12");

        // Use any real device that starts with Google
        capabilities.setCapability("appium:deviceName", "Google.*");

        // Install uploaded app by referencing its filename
        capabilities.setCapability("appium:app", "storage:filename=Android-MyDemoAppRN.apk");

        MutableCapabilities sauceCapabilities = new MutableCapabilities();

        // Set test name
        sauceCapabilities.setCapability("name", "Mobile App Test with API calls");

        capabilities.setCapability("sauce:options", sauceCapabilities);

        URL sauceLabsURL = new URL("https://" + System.getenv("SAUCE_USERNAME") + ":" + System.getenv("SAUCE_ACCESS_KEY") + "@ondemand.eu-central-1.saucelabs.com/wd/hub");
        AndroidDriver driver = new AndroidDriver(sauceLabsURL, capabilities);

        // Navigate to API calls view
        driver.findElement(AppiumBy.accessibilityId("open menu")).click();
        driver.findElement(AppiumBy.accessibilityId("menu item api calls")).click();
        driver.findElement(AppiumBy.xpath("//android.view.ViewGroup[@content-desc=\"api calls screen\"]/android.view.ViewGroup[3]")).click();
        Thread.sleep(2000);
        driver.findElement(AppiumBy.xpath("//android.view.ViewGroup[@content-desc=\"api calls screen\"]/android.view.ViewGroup[2]")).click();
        Thread.sleep(2000);
        driver.findElement(AppiumBy.xpath("//android.view.ViewGroup[@content-desc=\"api calls screen\"]/android.view.ViewGroup[4]")).click();
        Thread.sleep(2000);
        driver.findElement(AppiumBy.xpath("//android.view.ViewGroup[@content-desc=\"api calls screen\"]/android.view.ViewGroup[5]")).click();
        Thread.sleep(2000);
        driver.findElement(AppiumBy.xpath("//android.view.ViewGroup[@content-desc=\"api calls screen\"]/android.view.ViewGroup[2]")).click();
        Thread.sleep(5000);

        driver.quit();
    }
}
