package com.saucelabs.yy.Tests;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestListener;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class SuperTestBase implements ITestListener {
    public String username = System.getenv("SAUCE_USERNAME");
    public String accesskey = System.getenv("SAUCE_ACCESS_KEY");
    //public String driverCreation = System.getenv("SAUCE_DRIVER_CREATION");
    public ThreadLocal<AndroidDriver> androidDriver = new ThreadLocal<>();
    public ThreadLocal<IOSDriver> iosDriver = new ThreadLocal<>();
    public ThreadLocal<RemoteWebDriver> remoteWebDriver = new ThreadLocal<>();
    public ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();
    public String dateTime = DateTimeFormatter.ISO_INSTANT.format(Instant.now().truncatedTo(ChronoUnit.MINUTES).atZone(ZoneId.systemDefault()));

    public URL createDriverURL(Region region) throws MalformedURLException {
        switch (region) {
            case EU: return new URL("https://" + username + ":" + accesskey + Region.EU.label);
            case EU_RDC: return new URL("https://" + username + ":" + accesskey + Region.EU_RDC.label);
            case US: return new URL("https://" + username + ":" + accesskey + Region.US.label);
            case HEADLESS: return new URL("https://" + username + ":" + accesskey + Region.HEADLESS.label);
        }
        return null;
    }

    /**
     * Default returned driver URL will be EU.
     * @return EU driver URL
     * @throws MalformedURLException
     */
    public URL createDriverURL() throws MalformedURLException {
        return createDriverURL(Region.EU);
    }

    public AndroidDriver getAndroidDriver() {
        return androidDriver.get();
    }

    public IOSDriver getIOSDriver() {
        return iosDriver.get();
    }

    public RemoteWebDriver getRemoteWebDriver() {
        return remoteWebDriver.get();
    }

    public AppiumDriver getDriver() {
        return driver.get();
    }
}