package com.saucelabs.yy.Tests.Appium.SwagLabsApp;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.saucelabs.saucerest.DataCenter;
import com.saucelabs.yy.Utility.SauceRESTExtension;
import org.openqa.selenium.OutputType;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;

public class LoginTests extends TestBase {

    private String APK_FILE_ID = "";

    @BeforeClass
    public void setup() {
        SauceRESTExtension sauceRESTExtension = new SauceRESTExtension(username, accesskey, DataCenter.EU);
        String storedFiles = sauceRESTExtension.getStoredFiles();
        JsonArray storedFilesArray = new JsonParser().parse(storedFiles).getAsJsonObject().get("items").getAsJsonArray();

        for (int i = storedFilesArray.size() - 1; i >= 0; i--) {
            String id = storedFilesArray.get(i).getAsJsonObject().get("id").getAsString();
            String name = storedFilesArray.get(i).getAsJsonObject().get("name").getAsString();
            String kind = storedFilesArray.get(i).getAsJsonObject().get("kind").getAsString();

            if (kind.equals("android") && name.equals(APK_FILE_NAME)) {
                APK_FILE_ID = id;
                break;
            }
        }
    }

    @Test(dataProvider = "hardCodedBrowsers")
    public void blankCredentials(String platformName, String deviceName, Method testMethod) throws MalformedURLException {
        createDriver(platformName, deviceName, testMethod.getName(), APK_FILE_ID);

        getAndroidDriver().findElementByAccessibilityId("test-LOGIN").click();
        Assert.assertTrue(getAndroidDriver().findElementByAccessibilityId("test-Error message").isDisplayed());
        getAndroidDriver().getScreenshotAs(OutputType.FILE);
    }

    @Test(dataProvider = "hardCodedBrowsers")
    public void validCredentials(String platformName, String deviceName, Method testMethod) throws MalformedURLException {
        createDriver(platformName, deviceName, testMethod.getName(), APK_FILE_ID);

        getAndroidDriver().findElementByAccessibilityId("test-Username").sendKeys("standard_user");
        getAndroidDriver().findElementByAccessibilityId("test-Password").sendKeys("secret_sauce");
        getAndroidDriver().findElementByAccessibilityId("test-LOGIN").click();
        getAndroidDriver().getScreenshotAs(OutputType.FILE);
        Assert.assertTrue(getAndroidDriver().findElementByXPath("(//android.view.ViewGroup[@content-desc=\"test-Item\"])[1]/android.view.ViewGroup/android.widget.ImageView").isDisplayed());
    }
}
