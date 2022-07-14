package com.saucelabs.yy.Visual;

import org.openqa.selenium.Capabilities;

import java.time.Instant;

public class CapabilitiesModel {
    public String getTestReportURL() {
        return TEST_REPORT_URL;
    }

    public String getDevice() {
        return DEVICE;
    }

    public String getUDID() {
        return UDID;
    }

    public String getPlatformVersion() {
        return PLATFORM_VERSION;
    }

    public String getDeviceAPILevel() {
        return DEVICE_API_LEVEL;
    }

    public String getDeviceModel() {
        return DEVICE_MODEL;
    }

    public String getDeviceName() {
        return DEVICE_NAME;
    }

    public String getSessionID() {
        return SESSION_ID;
    }

    public String getJobId() {
        return JOB_ID;
    }

    public String getDeviceSessionId() {
        return DEVICE_SESSION_ID;
    }

    private final String TEST_REPORT_URL;
    private final String DEVICE;
    private final String UDID;
    private final String PLATFORM_VERSION;
    private final String DEVICE_API_LEVEL;
    private final String DEVICE_MODEL;
    private final String DEVICE_NAME;
    private final String SESSION_ID;
    private final String PLATFORM_NAME;
    private final String JOB_ID;
    private final String DEVICE_SESSION_ID;

    public CapabilitiesModel(Capabilities capabilities) {
        this.TEST_REPORT_URL = String.valueOf(capabilities.getCapability("testobject_test_report_url"));
        this.DEVICE = String.valueOf(capabilities.getCapability("testobject_device"));
        this.UDID = String.valueOf(capabilities.getCapability("udid"));
        this.PLATFORM_VERSION = String.valueOf(capabilities.getCapability("platformVersion"));
        this.DEVICE_API_LEVEL = String.valueOf(capabilities.getCapability("deviceApiLevel"));
        this.DEVICE_MODEL = String.valueOf(capabilities.getCapability("deviceModel"));
        this.DEVICE_NAME = String.valueOf(capabilities.getCapability("testobject_device_name"));
        this.SESSION_ID = String.valueOf(capabilities.getCapability("testobject_device_session_id"));
        this.PLATFORM_NAME = String.valueOf(capabilities.getCapability("platformName"));
        this.DEVICE_SESSION_ID = String.valueOf(capabilities.getCapability("testobject_device_session_id"));
        this.JOB_ID = TEST_REPORT_URL.substring(TEST_REPORT_URL.lastIndexOf("/") + 1);
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s", Instant.now().toString(), SESSION_ID, DEVICE_NAME, DEVICE, PLATFORM_NAME, PLATFORM_VERSION, DEVICE_MODEL, DEVICE_API_LEVEL, TEST_REPORT_URL);
    }
}
