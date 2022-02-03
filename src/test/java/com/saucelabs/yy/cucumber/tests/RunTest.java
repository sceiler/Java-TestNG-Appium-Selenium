package com.saucelabs.yy.cucumber.tests;

import com.saucelabs.yy.Tests.SuperTestBase;
import io.appium.java_client.AppiumDriver;
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
        glue = {"com.saucelabs.yy.cucumber.steps"},
        plugin = {
                "summary",
                "pretty",
                "html:target/cucumber-reports/cucumber-pretty.html",
                "json:target/cucumber-reports/CucumberTestReport.json"
        })

public class RunTest extends SuperTestBase {
    private TestNGCucumberRunner testNGCucumberRunner;

    public static ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();
    public String buildTag = System.getenv("BUILD_TAG");

    @BeforeClass(alwaysRun = true)
    public void setUpCucumber() {
        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
    }

    @BeforeMethod(alwaysRun = true)
    @Parameters({"deviceName", "platformName"})
    public void setUpClass(String deviceName, String platformName) throws Exception {
        MutableCapabilities capabilities = new MutableCapabilities();
        capabilities.setCapability("deviceName", deviceName);
        capabilities.setCapability("platformName", platformName);
        capabilities.setCapability("browserName", "");

        if (platformName.equals("iOS")) {
            capabilities.setCapability("app", "storage:filename=" + "iOS.RealDevice.SauceLabs.Mobile.Sample.app.2.7.1.ipa");
        } else if (platformName.equals("Android")) {
            capabilities.setCapability("app", "storage:filename=" + "Android.SauceLabs.Mobile.Sample.app.2.7.1.apk");
        }

        capabilities.setCapability("build", Objects.requireNonNullElseGet(buildTag, () -> "YiMin-Local-Java-Appium-Mobile-App-BDD-" + SuperTestBase.localBuildTag));

        driver.set(new AppiumDriver(new URL("https://" + username + ":" + accesskey + "@ondemand.eu-central-1.saucelabs.com/wd/hub"), capabilities));
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
