package com.saucelabs.yy.cucumber.tests;

import com.saucelabs.yy.Tests.SuperTestBase;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
import io.cucumber.testng.TestNGCucumberRunner;
import org.openqa.selenium.MutableCapabilities;
import org.testng.annotations.*;

import java.net.URL;
import java.time.Duration;
import java.util.Objects;

@CucumberOptions(
        features = "src/test/java/com/saucelabs/yy/cucumber/feature",
        glue = {"com.saucelabs.yy.cucumber.steps.Android"},
        plugin = {
                "summary",
                "pretty",
                "html:target/cucumber-reports/cucumber-pretty.html",
                "json:target/cucumber-reports/CucumberTestReport.json"
        })

public class RunAndroidTests extends SuperTestBase {
    private TestNGCucumberRunner testNGCucumberRunner;

    public static ThreadLocal<AndroidDriver> driver = new ThreadLocal<>();
    public String buildTag = System.getenv("BUILD_TAG");

    @BeforeClass(alwaysRun = true)
    public void setUpCucumber() {
        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
    }

    @BeforeMethod(alwaysRun = true)
    @Parameters({"deviceName", "platformName"})
    public void setUpClass(String deviceName, String platformName) throws Exception {
        MutableCapabilities capabilities = new MutableCapabilities();
        MutableCapabilities sauceCapabilities = new MutableCapabilities();
        capabilities.setCapability("appium:deviceName", deviceName);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
        capabilities.setCapability("appium:app", "storage:filename=" + "Android-MyDemoAppRN.apk");

        sauceCapabilities.setCapability("build", Objects.requireNonNullElseGet(buildTag, () -> "YiMin-Local-Java-Appium-Mobile-App-BDD-" + SuperTestBase.localBuildTag));
        capabilities.setCapability("sauce:options", sauceCapabilities);

        driver.set(new AndroidDriver(new URL("https://" + username + ":" + accesskey + "@ondemand.eu-central-1.saucelabs.com/wd/hub"), capabilities));
        driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    @Test(groups = "cucumber", description = "Run Cucumber Features.", dataProvider = "scenarios")
    public void scenario(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper) {
        testNGCucumberRunner.runScenario(pickleWrapper.getPickle());
    }

    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return testNGCucumberRunner.provideScenarios();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDownMethod() {
        if (driver.get() != null) {
            System.out.println(driver.get().getCapabilities().getCapability("testobject_test_report_url"));
        }
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() {
        if (driver.get() != null) {
            System.out.println(driver.get().getCapabilities().getCapability("testobject_test_report_url"));
        }
        testNGCucumberRunner.finish();
    }
}
