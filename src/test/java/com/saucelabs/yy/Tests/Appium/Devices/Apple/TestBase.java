package com.saucelabs.yy.Tests.Appium.Devices.Apple;

import com.saucelabs.yy.Tests.Appium.Devices.OCR;
import com.saucelabs.yy.Tests.SuperTestBase;
import com.saucelabs.yy.Utility.SauceRESTHelper;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.remote.CapabilityType;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
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
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class TestBase extends SuperTestBase {
    public String buildTag = System.getenv("BUILD_TAG");
    private ThreadLocal<String> sessionId = new ThreadLocal<>();
    protected ThreadLocal<OCR> ocr = new ThreadLocal<>();
    private ArrayList<String> signedInDevices = new ArrayList<>();

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

    private void setSessionId(String id) {
        sessionId.set(id);
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
        capabilities.setCapability("newCommandTimeout", "90");

        if (platformName.equals("Android")) {
            capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
        } else {
            capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
        }

        if (caps != null) {
            capabilities.merge(caps);
        }

        capabilities.setCapability("build", Objects.requireNonNullElseGet(buildTag, () -> "YiMin-Local-Java-Appium-Device-Check-" + localBuildTag));

        driver.set(new AppiumDriver<>(createDriverURL(), capabilities));
        System.out.println("Setting SessionID:" + driver.get().getSessionId());
        setSessionId(driver.get().getSessionId().toString());
        driver.get().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        //ocr.set(new OCR());
    }

    @AfterMethod(alwaysRun = true)
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
                    String output = String.format("%s,%s,%s,%s", udid, deviceName, platformVersion, url + "#4");
                    writer.write(output + System.lineSeparator());
                    signedInDevices.add(output);
                }
            }
            System.out.println("Quitting driver with SessionID:" + getSessionId());
            driver.get().quit();
        } else {
            System.out.println("Driver is null for whatever reason.");
        }
    }

    @AfterClass
    public void tearDown() {
        System.out.println("About to tearDown after class");
        for (String signedInDevice : signedInDevices) {
            System.out.println(signedInDevice);
        }
    }
}
