package com.saucelabs.yy.Tests.Appium.SwagLabsApp.iOS;

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
            } else
            {
                APK_FILE_ID = "e867fa19-3f85-4bde-af8f-5f2655230a9a";
                break;
            }
        }

        APK_FILE_ID = "e867fa19-3f85-4bde-af8f-5f2655230a9a";
    }

    @Test(dataProvider = "iOSRealDevices")
    public void blankCredentials(String platformName, String deviceName, Method testMethod) throws MalformedURLException {
        createDriver(platformName, deviceName, testMethod.getName(), APK_FILE_ID);

        getIOSDriver().findElementByAccessibilityId("test-LOGIN").click();
        Assert.assertTrue(getIOSDriver().findElementByAccessibilityId("test-Error message").isDisplayed());
        getIOSDriver().getScreenshotAs(OutputType.FILE);
    }

    @Test(dataProvider = "iOSRealDevices")
    public void validCredentials(String platformName, String deviceName, Method testMethod) throws MalformedURLException {
        createDriver(platformName, deviceName, testMethod.getName(), APK_FILE_ID);

        getIOSDriver().findElementByAccessibilityId("test-Username").sendKeys("standard_user");
        getIOSDriver().findElementByAccessibilityId("test-Password").sendKeys("secret_sauce");
        getIOSDriver().findElementByAccessibilityId("test-LOGIN").click();
        getIOSDriver().getScreenshotAs(OutputType.FILE);
        //Assert.assertTrue(getIOSDriver().findElementByXPath("(//android.view.ViewGroup[@content-desc=\"test-Item\"])[1]/android.view.ViewGroup/android.widget.ImageView").isDisplayed());
    }
}
