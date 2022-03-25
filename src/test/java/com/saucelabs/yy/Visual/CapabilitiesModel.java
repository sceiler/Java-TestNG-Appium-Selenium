package com.saucelabs.yy.Visual;

import org.openqa.selenium.Capabilities;

public class CapabilitiesModel {
    public String getTestReportURL() {
        return TEST_REPORT_URL;
    }

    public String getDeviceName() {
        return DEVICE_NAME;
    }

    public String getUDID() {
        return UDID;
    }

    public String getPlatformVersion() {
        return PLATFORM_VERSION;
    }

    private final String TEST_REPORT_URL;
    private final String DEVICE_NAME;
    private final String UDID;
    private final String PLATFORM_VERSION;

    public CapabilitiesModel(Capabilities capabilities) {
        this.TEST_REPORT_URL = String.valueOf(capabilities.getCapability("testobject_test_report_url"));
        this.DEVICE_NAME = String.valueOf(capabilities.getCapability("testobject_device"));
        this.UDID = String.valueOf(capabilities.getCapability("udid"));
        this.PLATFORM_VERSION = String.valueOf(capabilities.getCapability("platformVersion"));
    }
}
