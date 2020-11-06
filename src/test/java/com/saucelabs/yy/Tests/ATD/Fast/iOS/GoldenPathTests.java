package com.saucelabs.yy.Tests.ATD.Fast.iOS;

import com.saucelabs.yy.Tests.ATD.Pages.iOS.Fast.Page;
import com.saucelabs.yy.Tests.ATD.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

public class GoldenPathTests extends TestBase {

    @Test(dataProvider = "iOSSimulator")
    public void buyStuffFast(String platformName, String deviceName, String platformVersion, String appiumVersion, Method testMethod) throws MalformedURLException {
        createDriver(platformName, deviceName, platformVersion, appiumVersion, String.format("Appium-%s_iOS-%s_ID", appiumVersion, platformVersion));

        getDriver().findElement(By.id("test-Username")).sendKeys("standard_user");
        getDriver().findElement(By.id("test-Password")).sendKeys("secret_sauce");
        getDriver().findElement(By.id("test-LOGIN")).click();
        getDriver().findElement(By.xpath("(//XCUIElementTypeOther[@name=\"test-ADD TO CART\"])[1]")).click();
        getDriver().findElement(By.xpath("(//XCUIElementTypeOther[@name=\"test-ADD TO CART\"])[1]")).click();
        getDriver().findElement(By.xpath("(//XCUIElementTypeOther[@name=\"2\"])[4]")).click();
        swipeUp();
        swipeUp();
        getDriver().findElement(By.id("test-CHECKOUT")).click();
        getDriver().findElement(By.id("test-First Name")).sendKeys("Agile Testing");
        getDriver().findElement(By.id("test-Last Name")).sendKeys("Days");
        getDriver().findElement(By.id("test-Zip/Postal Code")).sendKeys("123456");
        getDriver().hideKeyboard();
        getDriver().findElement(By.id("test-CONTINUE")).click();
        swipeUp();
        swipeUp();
        getDriver().findElement(By.id("test-FINISH")).click();
        Assert.assertTrue(getDriver().findElement(By.id("THANK YOU FOR YOU ORDER")).isDisplayed());
        getDriver().findElement(By.id("test-BACK HOME")).click();

//        getDriver().findElement(Page.txtLogin).sendKeys("standard_user");
//        getDriver().findElement(Page.txtPassword).sendKeys("secret_sauce");
//        getDriver().findElement(Page.btnLogin).click();
//        getDriver().findElement(Page.eleFirstItem).click();
//        getDriver().findElement(Page.eleSecondItem).click();
//        getDriver().findElement(Page.eleCart).click();
//        getDriver().findElement(Page.eleCheckout).click();
//        getDriver().findElement(Page.txtFirstName).sendKeys("Agile Testing");
//        getDriver().findElement(Page.txtLastName).sendKeys("Days");
//        getDriver().findElement(Page.txtZipCode).sendKeys("123456");
//        getDriver().findElement(Page.btnContinue).click();
//        getDriver().findElement(Page.btnFinish).click();
//        Assert.assertTrue(getDriver().findElement(Page.eleThankYou).isDisplayed());
//        getDriver().findElement(Page.btnBackHome).click();
    }
}
