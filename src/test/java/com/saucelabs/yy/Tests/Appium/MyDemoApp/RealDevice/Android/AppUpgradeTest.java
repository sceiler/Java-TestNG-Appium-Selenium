package com.saucelabs.yy.Tests.Appium.MyDemoApp.RealDevice.Android;

import com.google.common.collect.ImmutableMap;
import com.saucelabs.yy.Tests.Appium.TestBase;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.OutputType;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

public class AppUpgradeTest extends TestBase {
    @Test(dataProvider = "Devices")
    public void appUpgrade(String deviceName, String platformVersion, String platformName, Method testMethod) throws MalformedURLException {
        MutableCapabilities capabilities = new MutableCapabilities();
        capabilities.setCapability("platformName", platformName);
        capabilities.setCapability("platformVersion", platformVersion);
        capabilities.setCapability("appium:deviceName", deviceName);
        capabilities.setCapability("appium:app", "storage:filename=" + APKOld);
        capabilities.setCapability("appium:automationName", "UiAutomator2");

        MutableCapabilities sauceOptions = new MutableCapabilities();
        sauceOptions.setCapability("name", testMethod.getName());
        sauceOptions.setCapability("username", System.getenv("SAUCE_USERNAME"));
        sauceOptions.setCapability("accessKey", System.getenv("SAUCE_ACCESS_KEY"));

        capabilities.setCapability("sauce:options", sauceOptions);

        AndroidDriver driver = new AndroidDriver(new URL("https://ondemand.eu-central-1.saucelabs.com/wd/hub"), capabilities);

        annotate("Check that app version is 1.3.0");
        annotate("Open menu");
        driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"open menu\"]/android.widget.ImageView")).click();
        annotate("Click About");
        driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"menu item about\"]/android.widget.TextView")).click();
        annotate("Check version");
        String oldVersion = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"about screen\"]/android.widget.ScrollView/android.view.ViewGroup/android.widget.TextView")).getText();
        Assert.assertTrue(oldVersion.contains("1.3.0"));
        driver.getScreenshotAs(OutputType.FILE);

        annotate("Do app upgrade");
        driver.executeScript("mobile:installApp", ImmutableMap.of("appPath", "storage:filename=" + APK));
        driver.activateApp("com.saucelabs.mydemoapp.rn");

        annotate("Check that app version is 1.5.0");
        annotate("Open menu");
        driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"open menu\"]/android.widget.ImageView")).click();
        annotate("Click About");
        driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"menu item about\"]/android.widget.TextView")).click();
        annotate("Check version");
        String newVersion = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"about screen\"]/android.widget.ScrollView/android.view.ViewGroup/android.widget.TextView")).getText();
        Assert.assertTrue(newVersion.contains("1.5.0"));
        driver.getScreenshotAs(OutputType.FILE);

        driver.quit();
    }
}