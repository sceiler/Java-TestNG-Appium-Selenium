package com.saucelabs.yy.Tests;

public enum Region {
    EU("@ondemand.eu-central-1.saucelabs.com/wd/hub"),
    US("@ondemand.saucelabs.com/wd/hub"),
    HEADLESS("@ondemand.us-east-1.saucelabs.com/wd/hub");

    public final String label;

    private Region(String label) {
        this.label = label;
    }
}
