package com.saucelabs.yy.Tests.Appium.Devices.Apple;

import com.saucelabs.yy.Region;
import com.saucelabs.yy.Tests.Appium.Devices.OCR;
import org.openqa.selenium.OutputType;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.Locale;

public class DeviceTests extends TestBase {

    @Test(dataProvider = "RDCDataProviderEU")
    public void checkForSignedInAppleIdOnEU(String platform, String deviceName, String platformVersion, Method methodName) throws MalformedURLException, InterruptedException {
        createDriver(platform, deviceName, platformVersion, methodName.getName());

        iosDriver.get().activateApp("com.apple.Preferences");
        Thread.sleep(1000);
        OCR ocr = new OCR();
        String result = ocr.getText(iosDriver.get().getScreenshotAs(OutputType.FILE)).toLowerCase(Locale.ROOT);

        Assert.assertTrue(assertion(result));
    }

    @Test(dataProvider = "RDCDataProviderUS", enabled = false)
    public void checkForSignedInAppleIdOnUS(String platform, String deviceName, String platformVersion, Method methodName) throws MalformedURLException, InterruptedException {
        createDriver(platform, deviceName, platformVersion, methodName.getName(), Region.US);

        iosDriver.get().activateApp("com.apple.Preferences");
        Thread.sleep(1000);
        OCR ocr = new OCR();
        String result = ocr.getText(iosDriver.get().getScreenshotAs(OutputType.FILE)).toLowerCase(Locale.ROOT);

        Assert.assertTrue(assertion(result));
    }

    private boolean assertion(String text) {
        if (text.contains("sign in to") || text.contains("signin to")) {
            return text.contains("testobject");
        } else {
            return false;
        }
    }
}
