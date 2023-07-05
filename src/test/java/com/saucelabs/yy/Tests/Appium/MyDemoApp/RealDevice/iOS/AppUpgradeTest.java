package com.saucelabs.yy.Tests.Appium.MyDemoApp.RealDevice.iOS;

import com.google.common.collect.ImmutableMap;
import com.saucelabs.yy.Tests.Appium.TestBase;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.OutputType;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;

public class AppUpgradeTest extends TestBase {
    @Test(dataProvider = "Devices")
    public void appUpgrade(String platformName, String deviceName, String platformVersion, Method testMethod) throws MalformedURLException {
        createDriver(platformName, deviceName, platformVersion, false, true, testMethod.getName(), IPAOld);

        annotate("Check that app version is 1.3.0");
        annotate("Open menu");
        getIOSDriver().findElement(AppiumBy.iOSNsPredicateString("label == \"Menu, tab, 3 of 3\"")).click();
        annotate("Click About");
        getIOSDriver().findElement(AppiumBy.iOSNsPredicateString("label == \"About\"")).click();
        annotate("Check version");
        String oldVersion = getIOSDriver().findElement(AppiumBy.iOSNsPredicateString("label == \"V.1.3.0-build 162 by \"")).getText();
        Assert.assertTrue(oldVersion.contains("1.3.0"));
        getIOSDriver().getScreenshotAs(OutputType.FILE);

        annotate("Do app upgrade");
        getIOSDriver().executeScript("mobile:installApp", ImmutableMap.of("appPath", "storage:filename=" + IPA));
        getIOSDriver().activateApp("com.saucelabs.mydemoapp.rn");

        annotate("Check that app version is 1.5.0");
        annotate("Open menu");
        getIOSDriver().findElement(AppiumBy.iOSNsPredicateString("label == \"Menu, tab, 3 of 3\"")).click();
        annotate("Click About");
        getIOSDriver().findElement(AppiumBy.iOSNsPredicateString("label == \"About\"")).click();
        annotate("Check version");
        String newVersion = getIOSDriver().findElement(AppiumBy.iOSNsPredicateString("label == \"V.1.5.0-build 191 by \"")).getText();
        Assert.assertTrue(newVersion.contains("1.5.0"));
        getIOSDriver().getScreenshotAs(OutputType.FILE);
    }
}