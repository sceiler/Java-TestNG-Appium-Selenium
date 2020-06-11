package com.saucelabs.yy.Tests;

import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {
    @Override
    public void onTestSuccess(ITestResult result) {
        String out = "";

        out = result.getInstanceName() + "." + result.getName() + "[";
        out += result.getParameters()[0] + ",";
        out += result.getParameters()[1] + ",";
        out += result.getParameters()[2] + "]";

        System.out.println("PASSED: " + out);
    }

}
