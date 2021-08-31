package com.saucelabs.yy;

import java.util.stream.Stream;

public enum Region {
    EU("@ondemand.eu-central-1.saucelabs.com/wd/hub", "https://eu-central-1.saucelabs.com/", "https://api.eu-central-1.saucelabs.com/", "https://app.eu-central-1.saucelabs.com/"),
    US("@ondemand.saucelabs.com/wd/hub", "https://saucelabs.com/", "https://api.us-west-1.saucelabs.com/", "https://app.saucelabs.com/"),
    APAC("@ondemand.apac-southeast-1.saucelabs.com/wd/hub", "", "", ""),
    HEADLESS("@ondemand.us-east-1.saucelabs.com/wd/hub", "", "", "");

    public final String hub;

    public String getServer() {
        return server;
    }

    public String getApiServer() {
        return apiServer;
    }

    public String getAppServer() {
        return appServer;
    }

    public final String server;
    public final String apiServer;
    public final String appServer;

    Region(String hub, String server, String apiServer, String appServer) {
        this.hub = hub;
        this.server = server;
        this.apiServer = apiServer;
        this.appServer = appServer;
    }

    public static Region fromString(String region) {
        return Stream.of(values()).filter(dc -> dc.name().equalsIgnoreCase(region)).findFirst().orElse(EU);
    }
}
