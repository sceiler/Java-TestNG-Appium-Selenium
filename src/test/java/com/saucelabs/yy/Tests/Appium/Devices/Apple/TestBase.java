package com.saucelabs.yy.Tests.Appium.Devices.Apple;

import com.saucelabs.yy.Region;
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
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class TestBase extends SuperTestBase {
    public String buildTag = System.getenv("BUILD_TAG");
    private final ThreadLocal<String> sessionId = new ThreadLocal<>();
    private final ArrayList<String> signedInDevices = new ArrayList<>();

    @DataProvider(name = "RDCDataProviderEU", parallel = true)
    public static Object[][] RDCDataProviderEU(Method testMethod) throws IOException, URISyntaxException, InterruptedException {
        var deviceList = SauceRESTHelper.getDevices();
        Object[][] objects = new Object[deviceList.size()][];

        for (int i = 0; i < deviceList.size(); i++) {
            objects[i] = new Object[]{deviceList.get(i).getOs(), deviceList.get(i).getId(), deviceList.get(i).getOsVersion()};
        }

        return objects;
    }

    @DataProvider(name = "RDCDataProviderUS", parallel = true)
    public static Object[][] RDCDataProviderUS(Method testMethod) throws IOException, URISyntaxException, InterruptedException {
        var deviceList = SauceRESTHelper.getDevices(Region.US);
        Object[][] objects = new Object[deviceList.size()][];

        for (int i = 0; i < deviceList.size(); i++) {
            objects[i] = new Object[]{deviceList.get(i).getOs(), deviceList.get(i).getId(), deviceList.get(i).getOsVersion()};
        }

        return objects;
    }

    @DataProvider(name = "singleRDCDataProviderEU", parallel = true)
    public static Object[][] singleRDCDataProviderEU(Method testMethod) throws IOException, URISyntaxException, InterruptedException {
        Object[][] objects = RDCDataProviderEU(testMethod);
        Object[][] singleDevice = new Object[1][3];
        singleDevice[0][0] = objects[0][0];
        singleDevice[0][1] = objects[0][1];
        singleDevice[0][2] = objects[0][2];
        return singleDevice;
    }

    @DataProvider(name = "singleRDCDataProviderUS", parallel = true)
    public static Object[][] singleRDCDataProviderUS(Method testMethod) throws IOException, URISyntaxException, InterruptedException {
        Object[][] objects = RDCDataProviderUS(testMethod);
        Object[][] singleDevice = new Object[1][3];
        singleDevice[0][0] = objects[0][0];
        singleDevice[0][1] = objects[0][1];
        singleDevice[0][2] = objects[0][2];
        return singleDevice;
    }

    public String getSessionId() {
        return sessionId.get();
    }

    private void setSessionId(String id) {
        sessionId.set(id);
    }

    protected void createDriver(String platformName, String deviceName, String platformVersion, String methodName) throws MalformedURLException {
        createDriver(platformName, deviceName, platformVersion, methodName, null, Region.EU);
    }

    protected void createDriver(String platformName, String deviceName, String platformVersion, String methodName, Region region) throws MalformedURLException {
        createDriver(platformName, deviceName, platformVersion, methodName, null, region);
    }

    protected void createDriver(String platformName, String deviceName, String methodName, MutableCapabilities caps) throws MalformedURLException {
        createDriver(platformName, deviceName, "", methodName, caps, Region.EU);
    }

    protected void createDriver(String platformName, String deviceName, String platformVersion, String methodName, MutableCapabilities caps, Region region) throws MalformedURLException {
        MutableCapabilities capabilities = new MutableCapabilities();
        MutableCapabilities sauceOptions = new MutableCapabilities();

        capabilities.setCapability(CapabilityType.PLATFORM_NAME, platformName);

        if (!platformVersion.equals("")) {
            capabilities.setCapability("appium:platformVersion", platformVersion);
        }

        capabilities.setCapability("appium:deviceName", deviceName);
        capabilities.setCapability("testobject_session_creation_timeout", "90000");
        capabilities.setCapability("newCommandTimeout", "90");
        capabilities.setCapability("name", methodName);

        if (platformName.equals("Android")) {
            capabilities.setCapability(CapabilityType.BROWSER_NAME, "Chrome");
        } else {
            capabilities.setCapability(CapabilityType.BROWSER_NAME, "Safari");
        }

        if (caps != null) {
            capabilities.merge(caps);
        }

        capabilities.setCapability("build", Objects.requireNonNullElseGet(buildTag, () -> "YiMin-Local-Java-Appium-Device-Check-" + localBuildTag));
        capabilities.setCapability("publicDevicesOnly", true);
        capabilities.setCapability("privateDevicesOnly", false);
        capabilities.setCapability("tags", List.of("device_check"));
        capabilities.setCapability("sauce:options", sauceOptions);

        driver.set(new AppiumDriver<>(createDriverURL(region), capabilities));
        System.out.println("Setting SessionID: " + driver.get().getSessionId());
        setSessionId(driver.get().getSessionId().toString());
        driver.get().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDownMethod(ITestResult result) throws IOException {
        if (driver.get() != null) {
            ((JavascriptExecutor) driver.get()).executeScript("sauce:job-result=" + (result.isSuccess() ? "passed" : "failed"));

            if (!result.isSuccess()) {
                var caps = driver.get().getCapabilities();
                String udid = (String) caps.getCapability("udid");
                String deviceName = (String) caps.getCapability("testobject_device");
                String platformVersion = (String) caps.getCapability("platformVersion");
                String url = (String) caps.getCapability("testobject_test_report_url");

                File yourFile = new File(System.getProperty("java.io.tmpdir") + "/devices.txt");
                yourFile.createNewFile(); // if file already exists will do nothing
                System.out.println("Writing result to: " + yourFile.getAbsolutePath());
                FileOutputStream oFile = new FileOutputStream(yourFile, true);

                // TODO: change in future...
                String datacenter = "";
                if (isEU()) {
                    datacenter = "EU";
                } else {
                    datacenter = "US";
                }

                try (Writer writer = new BufferedWriter(new OutputStreamWriter(oFile, StandardCharsets.UTF_8))) {
                    // add #4 to directly jump to the test report step where one can see the Settings app
                    String output = String.format("%s,%s,%s,%s,%s,%s", Instant.now().toString(), datacenter, udid, deviceName, platformVersion, url + "#4");
                    writer.write(output + System.lineSeparator());
                    signedInDevices.add(output);
                }
            }
            System.out.println("Quitting driver with SessionID: " + getSessionId());
            driver.get().quit();
        } else {
            System.out.println("Driver is null for whatever reason.");
        }
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() {
        System.out.println("About to tearDown after class");
        for (String signedInDevice : signedInDevices) {
            System.out.println(signedInDevice);
        }
    }

    private boolean isEU() {
        return driver.get().getRemoteAddress().getHost().contains("eu-central-1");
    }
}
