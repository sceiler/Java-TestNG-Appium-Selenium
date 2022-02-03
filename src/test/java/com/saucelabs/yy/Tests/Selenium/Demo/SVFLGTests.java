package com.saucelabs.yy.Tests.Selenium.Demo;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;

public class SVFLGTests extends TestBase {

    @Test(dataProvider = "hardCodedBrowsers")
    public void tests(String browser, String version, String os, Method method) throws MalformedURLException {
        createDriver(browser, version, os, method.getName());
        annotate("Open SVLFG homepage");
        getRemoteWebDriver().get("https://www.svlfg.de/");
        annotate("Check if logo exists");
        Assert.assertTrue(getRemoteWebDriver().findElement(By.cssSelector(".logo__lg")).isDisplayed());
        annotate("Check if search text field exists");
        Assert.assertTrue(getRemoteWebDriver().findElement(By.cssSelector("#section-section--cta-search")).isDisplayed());
    }

    @Test(dataProvider = "hardCodedBrowsers")
    public void search(String browser, String version, String os, Method method) throws MalformedURLException {
        createDriver(browser, version, os, method.getName());
        annotate("Open SVLFG homepage");
        getRemoteWebDriver().get("https://www.svlfg.de/");
        annotate("Search for Kontakt");
        getRemoteWebDriver().findElement(By.cssSelector("#section-section--cta-search")).sendKeys("Kontakt");
        getRemoteWebDriver().findElement(By.cssSelector(".cta__group button")).click();
        Assert.assertTrue(getRemoteWebDriver().getPageSource().contains("Ihr Kontakt zu uns"));
    }
}