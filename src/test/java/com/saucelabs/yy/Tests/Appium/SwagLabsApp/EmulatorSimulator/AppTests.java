package com.saucelabs.yy.Tests.Appium.SwagLabsApp.EmulatorSimulator;

import org.openqa.selenium.OutputType;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;

public class AppTests extends TestBase {

    @Test(dataProvider = "EmulatorSimulatorDataProvider")
    public void installApp(String platform, String deviceName, String platformVersion, Method methodName) throws MalformedURLException {
        createDriver(platform, deviceName, platformVersion, methodName.getName());
// TODO: uncomment when Appium implements these methods again

//        if (platform.equals("Android")) {
//            getDriver().installApp("https://github.com/saucelabs/sample-app-mobile/releases/download/2.7.1/Android.SauceLabs.Mobile.Sample.app.2.7.1.apk");
//            getDriver().activateApp("com.swaglabsmobileapp");
//            getDriver().context("NATIVE_APP");
//            Assert.assertTrue(getDriver().isAppInstalled("com.swaglabsmobileapp"));
//        } else if (platform.equals("iOS")) {
//            getDriver().installApp("https://github.com/saucelabs/sample-app-mobile/releases/download/2.7.1/iOS.Simulator.SauceLabs.Mobile.Sample.app.2.7.1.zip");
//            getDriver().activateApp("com.saucelabs.SwagLabsMobileApp");
//            getDriver().context("NATIVE_APP");
//            Assert.assertTrue(getDriver().isAppInstalled("com.saucelabs.SwagLabsMobileApp"));
//        }
        getDriver().getScreenshotAs(OutputType.FILE);
    }
}
