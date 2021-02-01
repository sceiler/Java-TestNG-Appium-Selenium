package com.saucelabs.yy.Tests.VisualTesting;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class ScreenerTest {
    protected WebDriver driver;
    public Boolean result;
    public static JavascriptExecutor js;
    @BeforeTest
    public void setup() throws MalformedURLException {
        String username = System.getenv("SAUCE_USERNAME");
        String accessKey = System.getenv("SAUCE_ACCESS_KEY");
        String visualApiKey = System.getenv("SCREENER_API_KEY");
        String methodName = "testInfo.getDisplayName();";
        ///*
        MutableCapabilities sauceVisual = new MutableCapabilities();
        sauceVisual.setCapability("apiKey", visualApiKey);
        sauceVisual.setCapability("projectName", "VisualBeta");
        sauceVisual.setCapability("viewportSize", "1280x1024");
        //*/
        MutableCapabilities sauceOpts = new MutableCapabilities();
        sauceOpts.setCapability("build", "SwagLabsVisualBeta");
        sauceOpts.setCapability("name", methodName);
        sauceOpts.setCapability("seleniumVersion", "3.141.59");
        sauceOpts.setCapability("tags", "");
        sauceOpts.setCapability("username", username);
        sauceOpts.setCapability("accessKey", accessKey);
        sauceOpts.setCapability("screenResolution", "1280x1024");
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("sauce:options", sauceVisual);
        caps.setCapability("sauce:options", sauceOpts);
        caps.setCapability("browserName", "chrome");
        caps.setCapability("browserVersion", "latest");
        caps.setCapability("platformName", "windows 10");
        caps.setCapability("sauce:visual", sauceVisual);
        String sauceUrl = "http://hub.screener.io:80/wd/hub";
        //String sauceUrl = "https://ondemand.saucelabs.com:443/wd/hub";
        URL url = new URL(sauceUrl);
        driver = new RemoteWebDriver(url, caps);
        js = (JavascriptExecutor) driver;
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
    }

    //@DisplayName("SauceDemoVisualBetaTest()")
    @Test
    public void SauceDemoVisualBetaTest() throws AssertionError {
        driver.get("https://www.saucedemo.com");
        String getTitle = driver.getTitle();
        //Get the Visual Snapshot
        //JavascriptExecutor js = (JavascriptExecutor) driver;
        System.out.println("**Visual Init**");
        js.executeScript("/*@visual.init*/", "Init");
        System.out.println("**Visual Snapshot 1**");
        js.executeScript("/*@visual.snapshot*/", "Swag Labs - Login Page");
        Assert.assertEquals(getTitle, "Swag Labs");
        if (getTitle.equals("Swag Labs")) {
            result = true;
        }else result = false;
        System.out.println("Logging in...");
        System.out.println("entering username");
        WebElement el1 = driver.findElement(By.id("user-name"));
        el1.sendKeys("standard_user");
        System.out.println("entering password");
        el1 = null;
        el1 = driver.findElement(By.id("password"));
        //el1.sendKeys("secret_sauce");
        System.out.println("clicking login");
        el1 = null;
        el1 = driver.findElement(By.id("login-button"));
        el1.click();
        System.out.println("**Visual Snapshot 2**");
        js.executeScript("/*@visual.snapshot*/", "Swag Labs - Product Page");
    }

    //@DisplayName("SauceDemoVisualBetaTest()")
    @Test
    public void SauceDemoVisualBetaTestChanged() throws AssertionError {
        driver.get("https://www.saucedemo.com");
        String getTitle = driver.getTitle();

        // Remove logo to cause visual defect
        js.executeScript("document.getElementsByClassName('login_logo')[0].remove();");

        //Get the Visual Snapshot
        //JavascriptExecutor js = (JavascriptExecutor) driver;
        System.out.println("**Visual Init**");
        js.executeScript("/*@visual.init*/", "Init");
        System.out.println("**Visual Snapshot 1**");
        js.executeScript("/*@visual.snapshot*/", "Swag Labs - Login Page");
        Assert.assertEquals(getTitle, "Swag Labs");
        if (getTitle.equals("Swag Labs")) {
            result = true;
        }else result = false;
        System.out.println("Logging in...");
        System.out.println("entering username");
        WebElement el1 = driver.findElement(By.id("user-name"));
        el1.sendKeys("standard_user");
        System.out.println("entering password");
        el1 = null;
        el1 = driver.findElement(By.id("password"));
        //el1.sendKeys("secret_sauce");
        System.out.println("clicking login");
        el1 = null;
        el1 = driver.findElement(By.id("login-button"));
        el1.click();
        System.out.println("**Visual Snapshot 2**");
        js.executeScript("/*@visual.snapshot*/", "Swag Labs - Product Page");
    }

    @AfterTest
    public void teardown() {
        ((JavascriptExecutor) driver).executeScript("sauce:job-result=" + (result ? "passed" : "failed"));
        //JavascriptExecutor js = (JavascriptExecutor) driver;
        System.out.println("**Visual Test End**");
        js.executeScript("/*@visual.end*/");
        driver.quit();
    }
}
