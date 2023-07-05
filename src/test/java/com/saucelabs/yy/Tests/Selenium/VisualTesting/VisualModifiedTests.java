package com.saucelabs.yy.Tests.Selenium.VisualTesting;

import com.saucelabs.visual.Region;
import com.saucelabs.visual.VisualApi;
import com.saucelabs.yy.Tests.Selenium.TestBase;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;

public class VisualModifiedTests extends TestBase {
    @Test(dataProvider = "Browsers")
    public void visualModifiedTest(String browser, String version, String os, Method method) throws MalformedURLException {
        createDriver(browser, version, os, method.getName());

        VisualApi visual = new VisualApi(getRemoteWebDriver(), Region.EU_CENTRAL_1, System.getenv("SAUCE_USERNAME"), System.getenv("SAUCE_ACCESS_KEY"));

        getRemoteWebDriver().get("https://www.saucedemo.com/");
        visual.check("Login Page");

        getRemoteWebDriver().get("https://www.saucedemo.com/inventory.html");
        getRemoteWebDriver().findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        visual.check("Inventory Page");
    }
}