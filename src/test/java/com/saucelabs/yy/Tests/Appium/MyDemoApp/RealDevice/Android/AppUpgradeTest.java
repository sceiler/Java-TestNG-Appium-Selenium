package com.saucelabs.yy.Tests.Appium.MyDemoApp.RealDevice.Android;

import com.google.common.collect.ImmutableMap;
import com.saucelabs.yy.Tests.Appium.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;

public class AppUpgradeTest extends TestBase {
    @Test(dataProvider = "Devices")
    public void appUpgrade(String deviceName, String platformVersion, String platformName, Method testMethod) throws MalformedURLException {
        createDriver(platformName, deviceName, platformVersion, false, true, testMethod.getName(), APKOld);

        annotate("Check that app version is 1.3.0");
        annotate("Open menu");
        getAndroidDriver().findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"open menu\"]/android.widget.ImageView")).click();
        annotate("Click About");
        getAndroidDriver().findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"menu item about\"]/android.widget.TextView")).click();
        annotate("Check version");
        String oldVersion = getAndroidDriver().findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"about screen\"]/android.widget.ScrollView/android.view.ViewGroup/android.widget.TextView")).getText();
        Assert.assertTrue(oldVersion.contains("1.3.0"));
        getAndroidDriver().getScreenshotAs(OutputType.FILE);

        annotate("Do app upgrade");
        getAndroidDriver().executeScript("mobile:installApp", ImmutableMap.of("appPath", "storage:filename=" + APK));
        getAndroidDriver().activateApp("com.saucelabs.mydemoapp.rn");

        annotate("Check that app version is 1.5.0");
        annotate("Open menu");
        getAndroidDriver().findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"open menu\"]/android.widget.ImageView")).click();
        annotate("Click About");
        getAndroidDriver().findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"menu item about\"]/android.widget.TextView")).click();
        annotate("Check version");
        String newVersion = getAndroidDriver().findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"about screen\"]/android.widget.ScrollView/android.view.ViewGroup/android.widget.TextView")).getText();
        Assert.assertTrue(newVersion.contains("1.5.0"));
        getAndroidDriver().getScreenshotAs(OutputType.FILE);

        getAndroidDriver().quit();
    }
}