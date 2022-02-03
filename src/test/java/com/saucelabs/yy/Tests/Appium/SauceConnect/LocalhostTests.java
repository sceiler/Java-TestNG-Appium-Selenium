package com.saucelabs.yy.Tests.Appium.SauceConnect;

import com.saucelabs.saucerest.DataCenter;
import com.saucelabs.yy.Tests.Appium.TestBase;
import org.openqa.selenium.MutableCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;

public class LocalhostTests extends TestBase {
    private SauceConnectUtil util;
    private final String TUNNEL_NAME = "myTunnel";
    private String tunnelID; // id in JSON response

    @BeforeSuite
    public void setup() throws InterruptedException {
        this.util = new SauceConnectUtil(DataCenter.EU);
        util.startSauceConnect();

        do {
            tunnelID = util.getTunnelID(TUNNEL_NAME);
            Thread.sleep(1000);
        } while (util.tunnelID == null);
    }

    @Test(dataProvider = "emulatorBrowserDataProvider")
    public void openLocalRouterLoginPageOnEmulator(String platform, String deviceName, String platformVersion, Method methodName) throws MalformedURLException, InterruptedException {
        MutableCapabilities caps = new MutableCapabilities();
        caps.setCapability("tunnelName", TUNNEL_NAME);
        createDriver(platform, deviceName, platformVersion, methodName.getName(), caps);

        getAndroidDriver().get("http://192.168.178.1");

        // wait for website to finish loading...
        Thread.sleep(10000);
        Assert.assertEquals(getAndroidDriver().getTitle(), "FRITZ!Box");
    }

    @Test(dataProvider = "RDCBrowserDataProvider")
    public void openLocalRouterLoginPageOnRDC(String platform, String deviceName, Method methodName) throws MalformedURLException, InterruptedException {
        MutableCapabilities caps = new MutableCapabilities();
        caps.setCapability("tunnelName", TUNNEL_NAME);
        createDriver(platform, deviceName, methodName.getName(), caps);

        getAndroidDriver().get("http://192.168.178.1");

        // wait for website to finish loading...
        Thread.sleep(10000);
        Assert.assertEquals(getAndroidDriver().getTitle(), "FRITZ!Box");
    }

    @AfterSuite
    public void teardown() throws IOException {
        util.stopSauceConnect();
        System.out.println("Delete tunnel with ID '" + tunnelID + "'");
        util.deleteTunnel(tunnelID);
    }
}
