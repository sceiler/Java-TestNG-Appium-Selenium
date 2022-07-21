package com.saucelabs.yy.Utility;

import org.openqa.selenium.By;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.AbstractDriverOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

public class RemoteWebDriverExtended extends RemoteWebDriver {

    public RemoteWebDriverExtended(URL driverURL, AbstractDriverOptions browserOptions) {
        super(driverURL, browserOptions);
    }

    public RemoteWebDriverExtended(URL driverURL, MutableCapabilities capabilities) {
        super(driverURL, capabilities);
    }

    @Override
    public WebElement findElement(By locator) {
        return super.findElement(locator);
        // new WebDriverWait(this, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(locator));
    }
}
