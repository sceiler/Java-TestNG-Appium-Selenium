package com.saucelabs.yy.Visual;

import org.openqa.selenium.OutputType;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;

public class VisualTests extends TestBase {

    @Test(dataProvider = "RDC")
    public void visualTest(String platformName, String deviceName, String platformVersion, Method testMethod) throws MalformedURLException {
        createDriver(platformName, deviceName, platformVersion, testMethod.getName());

        getDriver().getScreenshotAs(OutputType.FILE);
    }
}
