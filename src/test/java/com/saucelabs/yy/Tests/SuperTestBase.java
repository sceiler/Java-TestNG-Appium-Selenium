package com.saucelabs.yy.Tests;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.net.MalformedURLException;
import java.net.URL;

public class SuperTestBase implements ITestListener {
    public String username = System.getenv("SAUCE_USERNAME");
    public String accesskey = System.getenv("SAUCE_ACCESS_KEY");
    //public String driverCreation = System.getenv("SAUCE_DRIVER_CREATION");
    public ThreadLocal<AndroidDriver> androidDriver = new ThreadLocal<>();
    public ThreadLocal<IOSDriver> iosDriver = new ThreadLocal<>();
    public ThreadLocal<RemoteWebDriver> remoteWebDriver = new ThreadLocal<>();

    public URL createDriverURL(Region region) throws MalformedURLException {
        switch (region) {
            case EU: return new URL("https://" + username + ":" + accesskey + Region.EU.label);
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
}