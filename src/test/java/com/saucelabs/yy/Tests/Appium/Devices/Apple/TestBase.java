package com.saucelabs.yy.Tests.Appium.Devices.Apple;

import com.saucelabs.yy.Tests.Appium.Devices.OCR;
import com.saucelabs.yy.Tests.SuperTestBase;
import com.saucelabs.yy.Utility.SauceRESTHelper;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.remote.CapabilityType;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class TestBase extends SuperTestBase {
    public String buildTag = System.getenv("BUILD_TAG");
    private ThreadLocal<String> sessionId = new ThreadLocal<>();
    protected ThreadLocal<OCR> ocr = new ThreadLocal<>();

    @DataProvider(name = "RDCDataProvider", parallel = true)
    public static Object[][] RDCDataProvider(Method testMethod) throws IOException, URISyntaxException, InterruptedException {
        var deviceList = SauceRESTHelper.getDevices();
        Object[][] objects = new Object[deviceList.size()][];

        for (int i = 0; i < deviceList.size(); i++) {
            objects[i] = new Object[]{deviceList.get(i).getOs(), deviceList.get(i).getId(), deviceList.get(i).getOsVersion()};
        }

        return objects;
    }

    public String getSessionId() {
        return sessionId.get();
    }

    protected void createDriver(String platformName, String deviceName, String platformVersion, String methodName) throws MalformedURLException {
        createDriver(platformName, deviceName, platformVersion, methodName, null);
    }

    protected void createDriver(String platformName, String deviceName, String methodName, MutableCapabilities caps) throws MalformedURLException {
        createDriver(platformName, deviceName, "", methodName, caps);
    }

    protected void createDriver(String platformName, String deviceName, String platformVersion, String methodName, MutableCapabilities caps) throws MalformedURLException {
        MutableCapabilities capabilities = new MutableCapabilities();
        capabilities.setCapability(CapabilityType.PLATFORM_NAME, platformName);

        if (!platformVersion.equals("")) {
            capabilities.setCapability("platformVersion", platformVersion);
        }

        capabilities.setCapability("deviceName", deviceName);
        capabilities.setCapability("name", methodName);
        capabilities.setCapability("testobject_session_creation_timeout", "90000");

        if (platformName.equals("Android")) {
            capabilities.setCapability(CapabilityType.BROWSER_NAME, "Chrome");
        } else {
            capabilities.setCapability(CapabilityType.BROWSER_NAME, "Safari");
        }

        if (caps != null) {
            capabilities.merge(caps);
        }

        capabilities.setCapability("build", Objects.requireNonNullElseGet(buildTag, () -> "YiMin-Local-Java-Appium-Device-Check-" + localBuildTag));

        driver.set(new AppiumDriver<>(createDriverURL(), capabilities));
        driver.get().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        ocr.set(new OCR());
    }

    @AfterMethod
    public void tearDown(ITestResult result) throws IOException {
        if (driver.get() != null) {
            ((JavascriptExecutor) driver.get()).executeScript("sauce:job-result=" + (result.isSuccess() ? "passed" : "failed"));

            if (!result.isSuccess()) {
                var caps = driver.get().getCapabilities();
                String udid = (String) caps.getCapability("udid");
                String deviceName = (String) caps.getCapability("testobject_device");
                String platformVersion = (String) caps.getCapability("platformVersion");
                String url = (String) caps.getCapability("testobject_test_report_url");

                File yourFile = new File("/Users/yimin.yang/Downloads/devices.txt");
                yourFile.createNewFile(); // if file already exists will do nothing
                FileOutputStream oFile = new FileOutputStream(yourFile, true);
                try (Writer writer = new BufferedWriter(new OutputStreamWriter(oFile, StandardCharsets.UTF_8))) {
                    // add #4 to directly jump to the test report step where one can see the Settings app
                    writer.write(String.format("%s,%s,%s,%s" + System.lineSeparator(), udid, deviceName, platformVersion, url + "#4"));
                }
            }

            driver.get().quit();
        }
    }
}
