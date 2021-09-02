package com.saucelabs.yy.Tests.Appium.Devices.Apple;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.Locale;

public class DeviceTests extends TestBase {

    /**
     * Run the test twice to increase chance to also hit the same device model but different device
     */
    @Test(dataProvider = "RDCDataProvider")
    public void checkForSignedInAppleID(String platform, String deviceName, String platformVersion, Method methodName) throws MalformedURLException {
        createDriver(platform, deviceName, platformVersion, methodName.getName());

        driver.get().activateApp("com.apple.Preferences");
        ((JavascriptExecutor) getRemoteWebDriver()).executeScript("sauce:context=Start OCR");
        String result = ocr.get().getText(driver.get().getScreenshotAs(OutputType.FILE)).toLowerCase(Locale.ROOT);
        ((JavascriptExecutor) getRemoteWebDriver()).executeScript("sauce:context=Stopped OCR");

        Assert.assertTrue(result.contains("sign in to") || result.contains("testobject"), deviceName + System.lineSeparator() + result);
    }
}
