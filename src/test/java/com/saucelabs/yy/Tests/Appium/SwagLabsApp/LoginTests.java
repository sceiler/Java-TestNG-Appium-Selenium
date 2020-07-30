package com.saucelabs.yy.Tests.Appium.SwagLabsApp;

import org.openqa.selenium.OutputType;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.rmi.UnexpectedException;

public class LoginTests extends TestBase {
    @Test(dataProvider = "hardCodedBrowsers")
    public void blankCredentials(String platformName, String deviceName, Method testMethod) throws MalformedURLException, UnexpectedException {
        createDriver(platformName, deviceName, testMethod.getName());

        getAndroidDriver().findElementByAccessibilityId("test-LOGIN").click();
        Assert.assertTrue(getAndroidDriver().findElementByAccessibilityId("test-Error message").isDisplayed());
        getAndroidDriver().getScreenshotAs(OutputType.FILE);
    }

    @Test(dataProvider = "hardCodedBrowsers")
    public void validCredentials(String platformName, String deviceName, Method testMethod) throws MalformedURLException, UnexpectedException {
        createDriver(platformName, deviceName, testMethod.getName());

        getAndroidDriver().findElementByAccessibilityId("test-Username").sendKeys("standard_user");
        getAndroidDriver().findElementByAccessibilityId("test-Password").sendKeys("secret_sauce");
        getAndroidDriver().findElementByAccessibilityId("test-LOGIN").click();
        getAndroidDriver().getScreenshotAs(OutputType.FILE);
        Assert.assertTrue(getAndroidDriver().findElementByXPath("(//android.view.ViewGroup[@content-desc=\"test-Item\"])[1]/android.view.ViewGroup/android.widget.ImageView").isDisplayed());
    }
}
