package com.saucelabs.yy.Tests.Appium.SwagLabsApp.RealDevice.iOS;

import com.saucelabs.yy.Tests.Appium.SwagLabsApp.RealDevice.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;

public class LoginTests extends TestBase {

    @Test(dataProvider = "iOSRealDevices")
    public void blankCredentials(String platformName, String deviceName, Method testMethod) throws MalformedURLException {
        createDriver(platformName, deviceName, testMethod.getName());

        getDriver().findElement(By.id("test-LOGIN")).click();
        Assert.assertTrue(getDriver().findElement(By.id("test-Error message")).isDisplayed());
        getDriver().getScreenshotAs(OutputType.FILE);
    }

    @Test(dataProvider = "iOSRealDevices")
    public void validCredentials(String platformName, String deviceName, Method testMethod) throws MalformedURLException {
        createDriver(platformName, deviceName, testMethod.getName());

        getDriver().findElement(By.id("test-Username")).sendKeys("standard_user");
        getDriver().findElement(By.id("test-Password")).sendKeys("secret_sauce");
        getDriver().findElement(By.id("test-LOGIN")).click();
        getDriver().getScreenshotAs(OutputType.FILE);
    }

    @Test(dataProvider = "iOSRealDevices")
    public void JWPTest(String platformName, String deviceName, Method testMethod) throws MalformedURLException {
        createJWPDriver(platformName, deviceName, testMethod.getName());

        getDriver().findElement(By.id("test-Username")).sendKeys("standard_user");
        getDriver().findElement(By.id("test-Password")).sendKeys("secret_sauce");
        getDriver().findElement(By.id("test-LOGIN")).click();
        getDriver().getScreenshotAs(OutputType.FILE);
    }
}
