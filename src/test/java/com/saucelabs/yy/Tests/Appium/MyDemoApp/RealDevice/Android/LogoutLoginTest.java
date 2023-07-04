package com.saucelabs.yy.Tests.Appium.MyDemoApp.RealDevice.Android;

import com.saucelabs.yy.Tests.Appium.TestBase;
import io.appium.java_client.AppiumBy;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;

public class LogoutLoginTest extends TestBase {

    @Test(dataProvider = "Devices")
    public void logoutLoginTest(String platformName, String deviceName, String platformVersion, Method testMethod) throws MalformedURLException {
        createDriver(platformName, deviceName, platformVersion, testMethod.getName());

        // Click on hamburger menu icon
        getAndroidDriver().findElement(AppiumBy.xpath("//android.view.ViewGroup[@content-desc=\"open menu\"]/android.widget.ImageView\n")).click();
        // Click on Log Out menu item
        getAndroidDriver().findElement(AppiumBy.accessibilityId("menu item log out")).click();
        // Confirm logout alert
        getAndroidDriver().findElement(AppiumBy.id("android:id/button1")).click();
        // Dismiss alert
        getAndroidDriver().findElement(AppiumBy.id("android:id/button1")).click();
        // Enter username
        getAndroidDriver().findElement(AppiumBy.xpath("//android.view.ViewGroup[@content-desc=\"login screen\"]/android.widget.ScrollView/android.view.ViewGroup/android.widget.EditText[1]")).sendKeys("bob@example.com");
        // Enter password
        getAndroidDriver().findElement(AppiumBy.xpath("//android.view.ViewGroup[@content-desc=\"login screen\"]/android.widget.ScrollView/android.view.ViewGroup/android.widget.EditText[2]")).sendKeys("10203040");
        // Click Login button
        getAndroidDriver().findElement(AppiumBy.accessibilityId("Login button")).click();
        // Verify we are at checkout screen
        Assert.assertEquals(getAndroidDriver().findElement(AppiumBy.accessibilityId("To Payment button")).getAttribute("content-desc"), "To Payment button");
    }
}