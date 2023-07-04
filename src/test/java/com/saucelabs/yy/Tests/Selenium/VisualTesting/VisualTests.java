package com.saucelabs.yy.Tests.Selenium.VisualTesting;

import com.saucelabs.visual.VisualApi;
import com.saucelabs.yy.Tests.Selenium.TestBase;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;

public class VisualTests extends TestBase {
    @Test(dataProvider = "Browsers")
    public void visualTest(String browser, String version, String os, Method method) throws MalformedURLException {
        createDriver(browser, version, os, method.getName());

        VisualApi visual = VisualApi.forProductionEu(getRemoteWebDriver());

        getRemoteWebDriver().get("https://www.saucedemo.com/");
        visual.check("Login Page");

        getRemoteWebDriver().get("https://www.saucedemo.com/inventory.html");
        visual.check("Inventory Page");
    }
}