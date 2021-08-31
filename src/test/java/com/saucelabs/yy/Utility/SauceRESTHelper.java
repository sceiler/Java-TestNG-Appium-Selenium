package com.saucelabs.yy.Utility;

import com.saucelabs.yy.Region;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Base64;

public class SauceRESTHelper {

    public static ArrayList<DeviceModel> getDevices(Region region) throws IOException, URISyntaxException, InterruptedException {
        ArrayList<DeviceModel> deviceModelArrayList = new ArrayList<>();
        var client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        var request = HttpRequest.newBuilder()
                .uri(new URI(region.apiServer + "v1/rdc/devices"))
                .header("Authorization", basicAuth(System.getenv("SAUCE_USERNAME"), System.getenv("SAUCE_ACCESS_KEY")))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JSONArray jsonArray = new JSONArray(response.body());

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String os, osVersion, id;

            os = jsonObject.getString("os");
            osVersion = jsonObject.getString("osVersion");
            id = jsonObject.getString("id");

            if (os.equalsIgnoreCase("IOS")) {
                deviceModelArrayList.add(new DeviceModel(os, osVersion, id));
            }
        }

        return deviceModelArrayList;
    }

    public static ArrayList<DeviceModel> getDevices() throws IOException, URISyntaxException, InterruptedException {
        return getDevices(Region.EU);
    }

    private static String basicAuth(String username, String password) {
        return "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
    }
}
