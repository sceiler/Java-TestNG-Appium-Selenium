package com.saucelabs.yy.Tests;

import com.saucelabs.yy.Region;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestListener;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

public class SuperTestBase implements ITestListener {
    public String username = System.getenv("SAUCE_USERNAME");
    public String accesskey = System.getenv("SAUCE_ACCESS_KEY");
    public String visualAccessKey = System.getenv("SCREENER_API_KEY");
    public ThreadLocal<AndroidDriver> androidDriver = new ThreadLocal<>();
    public ThreadLocal<IOSDriver> iosDriver = new ThreadLocal<>();
    public ThreadLocal<RemoteWebDriver> remoteWebDriver = new ThreadLocal<>();
    public ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();
    public ThreadLocal<JavascriptExecutor> javascriptExecutor = new ThreadLocal<>();
    public final static String localBuildTag = String.valueOf(new Random(System.currentTimeMillis()).nextInt()).replace("-", "");

    public URL createDriverURL(Region region) throws MalformedURLException {
        switch (region) {
            case EU:
                return new URL("https://" + username + ":" + accesskey + Region.EU.hub);
            case US:
                return new URL("https://" + username + ":" + accesskey + Region.US.hub);
            case APAC:
                return new URL("https://" + username + ":" + accesskey + Region.APAC.hub);
            case HEADLESS:
                return new URL("https://" + username + ":" + accesskey + Region.HEADLESS.hub);
        }
        return null;
    }

    /**
     * Default returned driver URL will be EU.
     *
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

    public JavascriptExecutor getJavaScriptExecutor() {
        return javascriptExecutor.get();
    }
}