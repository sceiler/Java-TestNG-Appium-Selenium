package com.saucelabs.yy.Visual;

import org.openqa.selenium.OutputType;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;

public class VisualTests extends TestBase {

    @Test(dataProvider = "RDC")
    public void visualTest(String platformName, String deviceName, String platformVersion, Method testMethod) throws IOException {
        createDriver(platformName, deviceName, platformVersion, testMethod.getName());

        VisualUtility visualUtility = new VisualUtility(getDriver().getScreenshotAs(OutputType.FILE), getDriver().getCapabilities());

        Assert.assertTrue(visualUtility.compare(), String.valueOf(getDriver().getCapabilities().getCapability("testobject_test_report_url")));
    }
}
