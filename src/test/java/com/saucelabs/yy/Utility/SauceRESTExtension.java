package com.saucelabs.yy.Utility;

import com.saucelabs.saucerest.DataCenter;
import com.saucelabs.saucerest.SauceREST;

import java.net.MalformedURLException;
import java.net.URL;

public class SauceRESTExtension extends SauceREST {
    public SauceRESTExtension(String username, String accessKey) {
        super(username, accessKey);
    }

    public SauceRESTExtension(String username, String accessKey, String dataCenter) {
        super(username, accessKey, dataCenter);
    }

    public SauceRESTExtension(String username, String accessKey, DataCenter dataCenter) {
        super(username, accessKey, dataCenter);
    }

    @Override
    public String getStoredFiles() {
        try {
            return this.retrieveResults(new URL("https://api.eu-central-1.saucelabs.com/v1/storage/files"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
