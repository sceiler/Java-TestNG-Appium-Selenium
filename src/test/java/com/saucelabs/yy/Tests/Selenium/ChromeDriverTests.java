package com.saucelabs.yy.Tests.Selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class ChromeDriverTests extends TestBase {

    @Test
    public void chromeOptions() throws MalformedURLException {
        MutableCapabilities mutableCapabilities = new MutableCapabilities();
        MutableCapabilities sauceOptions = new MutableCapabilities();
        ChromeOptions chromeOptions = new ChromeOptions();

        mutableCapabilities.setCapability(CapabilityType.BROWSER_NAME, "Chrome");
        mutableCapabilities.setCapability(CapabilityType.BROWSER_VERSION, "latest");
        mutableCapabilities.setCapability(CapabilityType.PLATFORM_NAME, "Windows 10");

        sauceOptions.setCapability("name", "chromeDriverTest");
        sauceOptions.setCapability("build", Objects.requireNonNullElseGet(buildTag, () -> "Local-chromeOptions-Test" + localBuildTag));

        chromeOptions.addArguments("--lang=es");

        mutableCapabilities.setCapability("sauce:options", sauceOptions);
        mutableCapabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);

        RemoteWebDriver remoteWebDriver = new RemoteWebDriver(new URL("<YOUR DRIVER URL>"), mutableCapabilities);

        remoteWebDriver.get("https://google.com");
        remoteWebDriver.get("https://saucedemo.com");
        remoteWebDriver.findElement(By.cssSelector("#user-name")).sendKeys("standard_user");
        remoteWebDriver.findElement(By.cssSelector("#password")).sendKeys("secret_sauce");
        remoteWebDriver.findElement(By.cssSelector("#login-button")).click();

        remoteWebDriver.quit();
    }
}
