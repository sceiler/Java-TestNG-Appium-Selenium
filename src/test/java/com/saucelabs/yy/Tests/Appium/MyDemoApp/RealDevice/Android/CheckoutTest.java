package com.saucelabs.yy.Tests.Appium.MyDemoApp.RealDevice.Android;

import com.saucelabs.yy.Tests.Appium.TestBase;
import io.appium.java_client.AppiumBy;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;

public class CheckoutTest extends TestBase {

    @Test(dataProvider = "Devices")
    public void checkoutTest(String platformName, String deviceName, String platformVersion, Method testMethod) throws MalformedURLException {
        createDriver(platformName, deviceName, platformVersion, testMethod.getName());

        // Click on backpack
        getAndroidDriver().findElement(AppiumBy.xpath("(//android.view.ViewGroup[@content-desc=\"store item\"])[1]/android.view.ViewGroup[1]/android.widget.ImageView")).click();
        // Add backpack to cart
        getAndroidDriver().findElement(AppiumBy.accessibilityId("Add To Cart button")).click();
        // Click on shopping cart icon
        getAndroidDriver().findElement(AppiumBy.xpath("//android.view.ViewGroup[@content-desc=\"cart badge\"]/android.widget.ImageView")).click();
        // Click on proceed to checkout button
        getAndroidDriver().findElement(AppiumBy.accessibilityId("Proceed To Checkout button")).click();
        // Click on bob username
        getAndroidDriver().findElement(AppiumBy.xpath("//android.view.ViewGroup[@content-desc=\"bob@example.com-autofill\"]/android.widget.TextView")).click();
        // Click on Login button
        getAndroidDriver().findElement(AppiumBy.accessibilityId("Login button")).click();
        // Enter full name
        getAndroidDriver().findElement(AppiumBy.accessibilityId("Full Name* input field")).sendKeys("Rebecca Winter");
        // Enter street address
        getAndroidDriver().findElement(AppiumBy.accessibilityId("Address Line 1* input field")).sendKeys("Mandorley 112");
        // Enter city name
        getAndroidDriver().findElement(AppiumBy.accessibilityId("City* input field")).sendKeys("Truro");
        // Enter zip code
        getAndroidDriver().findElement(AppiumBy.accessibilityId("Zip Code* input field")).sendKeys("12345");
        // Enter country name
        getAndroidDriver().findElement(AppiumBy.accessibilityId("Country* input field")).sendKeys("United Kingdom");
        // Click on payment button
        getAndroidDriver().findElement(AppiumBy.accessibilityId("To Payment button")).click();
        // Enter full name
        getAndroidDriver().findElement(AppiumBy.accessibilityId("Full Name* input field")).sendKeys("Rebecca Winter");
        // Enter card number
        getAndroidDriver().findElement(AppiumBy.accessibilityId("Card Number* input field")).sendKeys("325812657568789");
        // Enter expiration date
        getAndroidDriver().findElement(AppiumBy.accessibilityId("Expiration Date* input field")).sendKeys("03/25");
        // Enter security code
        getAndroidDriver().findElement(AppiumBy.accessibilityId("Security Code* input field")).sendKeys("123");
        // Click review order button
        getAndroidDriver().findElement(AppiumBy.accessibilityId("Review Order button")).click();
        // Click place order
        getAndroidDriver().findElement(AppiumBy.accessibilityId("Place Order button")).click();
        // Verify we completed checkout
        Assert.assertEquals(getAndroidDriver().findElement(AppiumBy.accessibilityId("Continue Shopping button")).getAttribute("content-desc"), "Continue Shopping button");
        Assert.assertEquals(getAndroidDriver().findElement(AppiumBy.xpath("//android.view.ViewGroup[@content-desc=\"checkout complete screen\"]/android.widget.ScrollView/android.view.ViewGroup/android.widget.TextView[1]")).getText(), "Checkout Complete");
    }
}
