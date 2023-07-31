package com.saucelabs.yy.Tests.Selenium;

import com.saucelabs.yy.Region;
import com.saucelabs.yy.Tests.SuperTestBase;
import com.saucelabs.yy.Utility.RemoteWebDriverExtended;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;
import org.openqa.selenium.safari.SafariOptions;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.time.Duration;
import java.util.*;

public class TestBase extends SuperTestBase {

    private static String config;
    public String buildTag = System.getenv("BUILD_TAG");
    public String tags = System.getenv("TAGS");
    public Region region;

    @DataProvider(name = "Browsers", parallel = true)
    public static Object[][] getBrowserConfigurations(Method testMethod) {
        String browserConfig = config;

        return switch (browserConfig) {
            case "GCP" -> getGcpConfigurations();
            case "Smoke" -> getSmokeConfigurations();
            case "OnlyChrome" -> getOnlyChromeConfigurations();
            case "Integration" -> getIntegrationConfigurations();
            default -> getDefaultConfigurations();
        };
    }

    private static Object[][] getGcpConfigurations() {
        return new Object[][]{
                {Constants.BROWSER.CHROME.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.WINDOWS10.label()},
                {Constants.BROWSER.FIREFOX.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.WINDOWS10.label()},
                {Constants.BROWSER.MICROSOFTEDGE.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.WINDOWS10.label()},
                {Constants.BROWSER.CHROME.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.WINDOWS81.label()},
                {Constants.BROWSER.FIREFOX.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.WINDOWS81.label()},
        };
    }

    private static Object[][] getSmokeConfigurations() {
        return new Object[][]{
                {Constants.BROWSER.CHROME.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.WINDOWS10.label()},
                {Constants.BROWSER.CHROME.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.WINDOWS11.label()},
        };
    }

    private static Object[][] getOnlyChromeConfigurations() {
        return new Object[][]{
                {Constants.BROWSER.CHROME.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.WINDOWS11.label()},
        };
    }

    private static Object[][] getIntegrationConfigurations() {
        return new Object[][]{
                {Constants.BROWSER.CHROME.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.WINDOWS11.label()},
                {Constants.BROWSER.CHROME.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.WINDOWS10.label()},
                {Constants.BROWSER.CHROME.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.MACOSMONTEREY.label()},
                {Constants.BROWSER.FIREFOX.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.WINDOWS11.label()},
                {Constants.BROWSER.FIREFOX.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.WINDOWS10.label()},
                {Constants.BROWSER.MICROSOFTEDGE.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.WINDOWS11.label()},
                {Constants.BROWSER.MICROSOFTEDGE.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.WINDOWS10.label()},
                {Constants.BROWSER.SAFARI.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.MACOSMONTEREY.label()},
                {Constants.BROWSER.SAFARI.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.MACOSBIGSUR.label()},
        };
    }

    private static Object[][] getDefaultConfigurations() {
        return new Object[][]{
                {Constants.BROWSER.CHROME.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.WINDOWS11.label()},
                {Constants.BROWSER.CHROME.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.WINDOWS10.label()},
                {Constants.BROWSER.CHROME.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.WINDOWS81.label()},
                {Constants.BROWSER.CHROME.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.MACOSMONTEREY.label()},
                {Constants.BROWSER.CHROME.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.MACOSBIGSUR.label()},
                {Constants.BROWSER.CHROME.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.MACOSCATALINA.label()},
                {Constants.BROWSER.FIREFOX.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.WINDOWS11.label()},
                {Constants.BROWSER.FIREFOX.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.WINDOWS10.label()},
                {Constants.BROWSER.FIREFOX.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.WINDOWS81.label()},
                {Constants.BROWSER.FIREFOX.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.MACOSMONTEREY.label()},
                {Constants.BROWSER.FIREFOX.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.MACOSBIGSUR.label()},
                {Constants.BROWSER.FIREFOX.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.MACOSCATALINA.label()},
                {Constants.BROWSER.MICROSOFTEDGE.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.MACOSMONTEREY.label()},
                {Constants.BROWSER.MICROSOFTEDGE.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.MACOSBIGSUR.label()},
                {Constants.BROWSER.MICROSOFTEDGE.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.MACOSCATALINA.label()},
                {Constants.BROWSER.SAFARI.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.MACOSMONTEREY.label()},
                {Constants.BROWSER.SAFARI.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.MACOSBIGSUR.label()},
                {Constants.BROWSER.SAFARI.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.MACOSCATALINA.label()},
        };
    }

    protected void createDriver(String browser, String version, String os, String methodName, Region region) throws MalformedURLException {
        AbstractDriverOptions browserOptions = getDriverOptions(browser);
        if (browserOptions == null) {
            throw new IllegalArgumentException("Invalid browser: " + browser);
        }

        browserOptions.setPlatformName(os);
        browserOptions.setBrowserVersion(version);

        Map<String, Object> sauceOptions = new HashMap<>();
        sauceOptions.put("name", methodName);
        sauceOptions.put("build", Objects.requireNonNullElseGet(buildTag, () -> "YiMin-Local-Java-Selenium-Web-" + localBuildTag));

        if (tags != null) {
            sauceOptions.put("tags", Arrays.asList(tags.split(",")));
        }

        browserOptions.setCapability("sauce:options", sauceOptions);

        remoteWebDriver.set(createRemoteWebDriver(region, browserOptions));
        remoteWebDriver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

        performTestSteps(remoteWebDriver.get());
    }

    private AbstractDriverOptions getDriverOptions(String browser) {
        Map<String, AbstractDriverOptions> driverOptionsMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        driverOptionsMap.put(Constants.BROWSER.CHROME.label(), new ChromeOptions());
        driverOptionsMap.put(Constants.BROWSER.FIREFOX.label(), new FirefoxOptions());
        driverOptionsMap.put(Constants.BROWSER.MICROSOFTEDGE.label(), new EdgeOptions());
        driverOptionsMap.put(Constants.BROWSER.SAFARI.label(), new SafariOptions());

        return driverOptionsMap.get(browser.toLowerCase(Locale.ROOT));
    }

    private RemoteWebDriverExtended createRemoteWebDriver(Region region, AbstractDriverOptions options) throws MalformedURLException {
        return new RemoteWebDriverExtended(createDriverURL(region), options);
    }

    private void performTestSteps(RemoteWebDriverExtended remoteWebDriver) {
        remoteWebDriver.get("https://www.saucedemo.com");
        annotate("Sending valid username");
        remoteWebDriver.findElement(By.cssSelector("#user-name")).sendKeys("standard_user");
        annotate("Sending valid password");
        remoteWebDriver.findElement(By.cssSelector("#password")).sendKeys("secret_sauce");
        annotate("Clicking login button");
        remoteWebDriver.findElement(By.cssSelector("#login-button")).click();
    }

    protected void createDriver(String browser, String version, String os, String methodName) throws MalformedURLException {
        if (region != null) {
            createDriver(browser, version, os, methodName, region);
        } else {
            createDriver(browser, version, os, methodName, Region.EU);
        }
    }

    @BeforeSuite
    public void setup(ITestContext context) {
        config = context.getCurrentXmlTest().getParameter("browserConfig");
    }

    @BeforeMethod
    public void setup() {
        // try to use REGION defined in environment variable
        if (System.getenv("REGION") != null && !System.getenv("REGION").isEmpty() && !System.getenv("REGION").isBlank()) {
            region = Region.fromString(System.getenv("REGION"));
        }

        // xml config takes priority over environment variable
        String regionString = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("REGION");
        if (regionString != null && !regionString.isBlank() && !regionString.isEmpty()) {
            region = Region.fromString(regionString);
        }

        // fallback/default if nothing is set
        if (region == null) {
            region = Region.EU;
        }
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (getRemoteWebDriver() != null) {
            ((JavascriptExecutor) getRemoteWebDriver()).executeScript("sauce:job-result=" + (result.isSuccess() ? "passed" : "failed"));
            getRemoteWebDriver().quit();
        }
    }

    protected void annotate(String text) {
        if (getRemoteWebDriver() != null) {
            ((JavascriptExecutor) getRemoteWebDriver()).executeScript("sauce:context=" + text);
        }
    }
}