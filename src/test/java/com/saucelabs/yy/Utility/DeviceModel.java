package com.saucelabs.yy.Utility;

public class DeviceModel {

    private final String os;
    private final String id;
    private final String osVersion;

    public String getOsVersion() {
        return osVersion;
    }

    public String getId() {
        return id;
    }

    public String getOs() {
        return os;
    }

    DeviceModel(String os, String osVersion, String id) {
        this.os = os;
        this.osVersion = osVersion;
        this.id = id;
    }
}
