package com.saucelabs.yy.Tests.Appium.SauceConnect;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.saucelabs.saucerest.DataCenter;
import com.saucelabs.saucerest.SauceREST;
import com.saucelabs.yy.Tests.Appium.TestBase;

import java.io.IOException;

public class SauceConnectUtil extends TestBase {
    private SauceREST sauceREST;
    private ThreadLocal<SauceConnectHelper> sauceConnectHelper = new ThreadLocal<>();
    private Gson gson;
    private String[] tunnels;
    public String tunnelID;

    public SauceConnectUtil() {
        //empty ctor
    }

    public SauceConnectUtil(DataCenter dataCenter) {
        this.sauceREST = new SauceREST(username, accesskey, dataCenter);
        sauceConnectHelper.set(new SauceConnectHelper());
        gson = new Gson();
    }

    private String getTunnels() {
        return sauceREST.getTunnels();
    }

    private String getTunnelInformation(String id) {
        return sauceREST.getTunnelInformation(id);
    }

    public void deleteTunnel(String id) throws IOException {
        sauceREST.deleteTunnel(id);
    }

    public void stopSauceConnect() {
        sauceConnectHelper.get().stopSauceConnect();
    }

    public void startSauceConnect() {
        if (getTunnelID("myTunnel") == null) {
            sauceConnectHelper.get().startSauceConnect();
        }
    }

    public String getTunnelID(String tunnelName) {
        if (tunnelsExist()) {
            System.out.println("Tunnel(s) existing. See if it is the correct one and if it is running.");
            for (String id : tunnels) {
                JsonObject object = JsonParser.parseString(getTunnelInformation(id)).getAsJsonObject();

                if (object.get("tunnel_identifier").getAsString().equals(tunnelName) && object.get("status").getAsString().equals("running")) {
                    tunnelID = object.get("id").getAsString();
                    System.out.println("Correct tunnel found, running with ID: " + tunnelID);
                    return tunnelID;
                }
            }
            System.out.println("Correct tunnel not found.");
        } else {
            System.out.println("No tunnels found.");
        }
        return null;
    }

    private boolean tunnelsExist() {
        String[] tunnels = gson.fromJson(getTunnels(), String[].class);

        if (tunnels.length != 0) {
            this.tunnels = tunnels;
            return true;
        }
        return false;
    }
}
