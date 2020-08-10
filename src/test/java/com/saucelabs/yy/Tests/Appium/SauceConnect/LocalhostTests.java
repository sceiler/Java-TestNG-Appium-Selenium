package com.saucelabs.yy.Tests.Appium.SauceConnect;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.saucelabs.saucerest.DataCenter;
import com.saucelabs.saucerest.SauceREST;
import com.saucelabs.yy.Tests.Appium.TestBase;
import org.openqa.selenium.MutableCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;

public class LocalhostTests extends TestBase {

    public ThreadLocal<SauceConnectHelper> sauceConnectHelper = new ThreadLocal<>();
    private String tunnelName = "";
    private String tunnelID = "";
    private boolean keepTrying = true;
    private SauceREST sauceREST;

    @BeforeClass
    public void setup() throws InterruptedException {
        sauceREST = new SauceREST(username, accesskey, DataCenter.EU);
        sauceConnectHelper.set(new SauceConnectHelper());
        sauceConnectHelper.get().startSauceConnect();
        String tunnels = sauceREST.getTunnels();
        Gson gson = new Gson();

        do {
            // no tunnels yet
            while (gson.fromJson(tunnels, String[].class).length == 0) {
                System.out.println("No tunnel yet. Wait and try again.");
                Thread.sleep(1000);
                tunnels = sauceREST.getTunnels();
            }

            for (String id : gson.fromJson(tunnels, String[].class)) {
                System.out.println("Tunnel(s) existing. See if it is the correct one and if it is running.");
                Thread.sleep(1000);
                JsonParser parser = new JsonParser();
                JsonObject object = parser.parse(sauceREST.getTunnelInformation(id)).getAsJsonObject();

                if (object.get("tunnel_identifier").getAsString().equals("myTunnel") && object.get("status").getAsString().equals("running")) {
                    System.out.println("Correct tunnel found and running.");
                    tunnelName = object.get("tunnel_identifier").getAsString();
                    tunnelID = id;
                    keepTrying = false;
                    break;
                }
            }
        } while (keepTrying);
    }

    @Test(dataProvider = "emulatorBrowserDataProvider")
    public void openLocalRouterLoginPage(String platform, String deviceName, String platformVersion, Method methodName) throws MalformedURLException {
        MutableCapabilities caps = new MutableCapabilities();
        caps.setCapability("tunnelIdentifier", tunnelName);
        createDriver(platform, deviceName, platformVersion, methodName.getName(), caps);

        getAndroidDriver().get("http://192.168.178.1");

        Assert.assertEquals(getAndroidDriver().getTitle(), "FRITZ!Box");
    }

    @AfterSuite
    public void teardown() {
        sauceConnectHelper.get().stopSauceConnect();
        System.out.println("Delete tunnel.");
        sauceREST.deleteTunnel(tunnelID);
    }
}
