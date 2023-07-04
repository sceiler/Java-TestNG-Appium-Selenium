package com.saucelabs.yy.Tests.Appium.MyDemoApp.EmulatorSimulator.iOS;

import com.saucelabs.yy.Tests.Appium.TestBase;
import io.appium.java_client.AppiumBy;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;

public class LogoutLoginTest extends TestBase {

    @Test(dataProvider = "Devices")
    public void logoutLoginTest(String platformName, String deviceName, String platformVersion, Method testMethod) throws MalformedURLException {
        createDriver(platformName, deviceName, platformVersion, true, true, testMethod.getName(), "");

        // Click on hamburger menu icon
        getIOSDriver().findElement(AppiumBy.iOSNsPredicateString("label == \"Menu, tab, 3 of 3\"")).click();
        // Click on Log Out menu item
        getIOSDriver().findElement(AppiumBy.iOSClassChain("**/XCUIElementTypeOther[`label == \"Log Out\"`]")).click();
        // Confirm logout alert
        getIOSDriver().findElement(AppiumBy.iOSClassChain("**/XCUIElementTypeButton[`label == \"Log Out\"`]")).click();
        // Dismiss alert
        getIOSDriver().findElement(AppiumBy.accessibilityId("OK")).click();
        // Enter username
        getIOSDriver().findElement(AppiumBy.iOSNsPredicateString("name == \"Username input field\"")).sendKeys("bob@example.com");
        // Enter password
        getIOSDriver().findElement(AppiumBy.iOSNsPredicateString("name == \"Password input field\"")).sendKeys("10203040");
        // Click Login button
        getIOSDriver().findElement(AppiumBy.accessibilityId("Login button")).click();
        // Verify we are at checkout screen
        Assert.assertEquals(getIOSDriver().findElement(AppiumBy.accessibilityId("To Payment button")).getAttribute("name"), "To Payment button");
    }
}