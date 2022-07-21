package com.saucelabs.yy.Utility;

import org.openqa.selenium.By;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.AbstractDriverOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.time.Duration;

public class RemoteWebDriverExtended extends RemoteWebDriver {

    public RemoteWebDriverExtended(URL driverURL, AbstractDriverOptions browserOptions) {
        super(driverURL, browserOptions);
    }

    public RemoteWebDriverExtended(URL driverURL, MutableCapabilities capabilities) {
        super(driverURL, capabilities);
    }

    @Override
    public WebElement findElement(By locator) {
        new WebDriverWait(this, Duration.ofSeconds(30), Duration.ofMillis(250)).until(ExpectedConditions.elementToBeClickable(locator));
        return super.findElement(locator);
    }
}
