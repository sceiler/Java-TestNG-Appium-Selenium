package com.saucelabs.yy.Utility;

import com.saucelabs.yy.Region;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

public class SauceRESTHelper {

    public static ArrayList<DeviceModel> getDevices(Region region, boolean isAvailable) throws IOException, URISyntaxException, InterruptedException {
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

            if (os.equalsIgnoreCase("android")) {
                deviceModelArrayList.add(new DeviceModel("Android", osVersion, id));
            }
        }

        if (isAvailable) {
            List<String> listAvailableDevices = getAvailableDevices(region);
            deviceModelArrayList = deviceModelArrayList.stream().filter(x -> listAvailableDevices.contains(x.getId())).collect(Collectors.toCollection(ArrayList::new));
        }

        return deviceModelArrayList;
    }

    public static ArrayList<DeviceModel> getDevices() throws IOException, URISyntaxException, InterruptedException {
        return getDevices(Region.EU, true);
    }

    public static ArrayList<DeviceModel> getDevices(Region region) throws IOException, URISyntaxException, InterruptedException {
        return getDevices(region, true);
    }

    public static ArrayList<String> getAvailableDevices() throws IOException, URISyntaxException, InterruptedException {
        return getAvailableDevices(Region.EU);
    }

    public static ArrayList<String> getAvailableDevices(Region region) throws IOException, URISyntaxException, InterruptedException {
        ArrayList<String> availableDeviceList = new ArrayList<>();
        var client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        var request = HttpRequest.newBuilder()
                .uri(new URI(region.apiServer + "v1/rdc/devices/available"))
                .header("Authorization", basicAuth(System.getenv("SAUCE_USERNAME"), System.getenv("SAUCE_ACCESS_KEY")))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JSONArray jsonArray = new JSONArray(response.body());

        for (int i = 0; i < jsonArray.length(); i++) {
            availableDeviceList.add(jsonArray.get(i).toString());
        }

        return availableDeviceList;
    }

    private static String basicAuth(String username, String password) {
        return "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
    }

    @Test
    public void test() throws IOException, URISyntaxException, InterruptedException {
        getDevices();
    }
}
