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

    public String buildTag = System.getenv("BUILD_TAG");
    public String tags = System.getenv("TAGS");
    public Region region;
    private static String config;

    @DataProvider(name = "Browsers", parallel = true)
    public static Object[][] Browsers(Method testMethod) {
        String browserConfig = config;
        Object[][] configs;

        switch (browserConfig) {
            case "GCP":
                configs = new Object[][]{
                        new Object[]{Constants.BROWSER.CHROME.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.WINDOWS10.label()},
                        new Object[]{Constants.BROWSER.FIREFOX.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.WINDOWS10.label()},
                        new Object[]{Constants.BROWSER.MICROSOFTEDGE.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.WINDOWS10.label()},
                        new Object[]{Constants.BROWSER.CHROME.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.WINDOWS81.label()},
                        new Object[]{Constants.BROWSER.FIREFOX.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.WINDOWS81.label()},

                        new Object[]{Constants.BROWSER.CHROME.label(), Constants.VERSION.LATEST1.label(), Constants.PLATFORM.WINDOWS10.label()},
                        new Object[]{Constants.BROWSER.FIREFOX.label(), Constants.VERSION.LATEST1.label(), Constants.PLATFORM.WINDOWS10.label()},
                        new Object[]{Constants.BROWSER.MICROSOFTEDGE.label(), Constants.VERSION.LATEST1.label(), Constants.PLATFORM.WINDOWS10.label()},
                        new Object[]{Constants.BROWSER.CHROME.label(), Constants.VERSION.LATEST1.label(), Constants.PLATFORM.WINDOWS81.label()},
                        new Object[]{Constants.BROWSER.FIREFOX.label(), Constants.VERSION.LATEST1.label(), Constants.PLATFORM.WINDOWS81.label()},

                        new Object[]{Constants.BROWSER.CHROME.label(), Constants.VERSION.LATEST2.label(), Constants.PLATFORM.WINDOWS10.label()},
                        new Object[]{Constants.BROWSER.FIREFOX.label(), Constants.VERSION.LATEST2.label(), Constants.PLATFORM.WINDOWS10.label()},
                        new Object[]{Constants.BROWSER.MICROSOFTEDGE.label(), Constants.VERSION.LATEST2.label(), Constants.PLATFORM.WINDOWS10.label()},
                        new Object[]{Constants.BROWSER.CHROME.label(), Constants.VERSION.LATEST2.label(), Constants.PLATFORM.WINDOWS81.label()},
                        new Object[]{Constants.BROWSER.FIREFOX.label(), Constants.VERSION.LATEST2.label(), Constants.PLATFORM.WINDOWS81.label()},

                        new Object[]{Constants.BROWSER.CHROME.label(), Constants.VERSION.LATEST3.label(), Constants.PLATFORM.WINDOWS10.label()},
                        new Object[]{Constants.BROWSER.FIREFOX.label(), Constants.VERSION.LATEST3.label(), Constants.PLATFORM.WINDOWS10.label()},
                        new Object[]{Constants.BROWSER.MICROSOFTEDGE.label(), Constants.VERSION.LATEST3.label(), Constants.PLATFORM.WINDOWS10.label()},
                        new Object[]{Constants.BROWSER.CHROME.label(), Constants.VERSION.LATEST3.label(), Constants.PLATFORM.WINDOWS81.label()},
                        new Object[]{Constants.BROWSER.FIREFOX.label(), Constants.VERSION.LATEST3.label(), Constants.PLATFORM.WINDOWS81.label()},

                        new Object[]{Constants.BROWSER.CHROME.label(), Constants.VERSION.LATEST4.label(), Constants.PLATFORM.WINDOWS10.label()},
                        new Object[]{Constants.BROWSER.FIREFOX.label(), Constants.VERSION.LATEST4.label(), Constants.PLATFORM.WINDOWS10.label()},
                        new Object[]{Constants.BROWSER.MICROSOFTEDGE.label(), Constants.VERSION.LATEST4.label(), Constants.PLATFORM.WINDOWS10.label()},
                        new Object[]{Constants.BROWSER.CHROME.label(), Constants.VERSION.LATEST4.label(), Constants.PLATFORM.WINDOWS81.label()},
                        new Object[]{Constants.BROWSER.FIREFOX.label(), Constants.VERSION.LATEST4.label(), Constants.PLATFORM.WINDOWS81.label()},
                };
                return configs;
            case "Smoke":
                configs = new Object[][]{
                        new Object[]{Constants.BROWSER.CHROME.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.WINDOWS10.label()},
                        new Object[]{Constants.BROWSER.CHROME.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.MACOSMONTEREY.label()},
                };
                return configs;
            case "OnlyChrome":
                configs = new Object[][]{
                        new Object[]{Constants.BROWSER.CHROME.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.WINDOWS11.label()},
                };
                return configs;
            case "Integration":
                configs = new Object[][]{
                        new Object[]{Constants.BROWSER.CHROME.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.WINDOWS11.label()},
                        new Object[]{Constants.BROWSER.CHROME.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.WINDOWS10.label()},
                        new Object[]{Constants.BROWSER.CHROME.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.MACOSMONTEREY.label()},
                        new Object[]{Constants.BROWSER.FIREFOX.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.WINDOWS11.label()},
                        new Object[]{Constants.BROWSER.FIREFOX.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.WINDOWS10.label()},
                        new Object[]{Constants.BROWSER.MICROSOFTEDGE.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.WINDOWS11.label()},
                        new Object[]{Constants.BROWSER.MICROSOFTEDGE.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.WINDOWS10.label()},
                        new Object[]{Constants.BROWSER.SAFARI.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.MACOSMONTEREY.label()},
                        new Object[]{Constants.BROWSER.SAFARI.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.MACOSBIGSUR.label()},
                };
                return configs;
            default:
                configs = new Object[][]{
                        new Object[]{Constants.BROWSER.CHROME.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.WINDOWS11.label()},
                        new Object[]{Constants.BROWSER.CHROME.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.WINDOWS10.label()},
                        new Object[]{Constants.BROWSER.CHROME.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.WINDOWS81.label()},
                        new Object[]{Constants.BROWSER.CHROME.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.MACOSMONTEREY.label()},
                        new Object[]{Constants.BROWSER.CHROME.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.MACOSBIGSUR.label()},
                        new Object[]{Constants.BROWSER.CHROME.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.MACOSCATALINA.label()},

                        new Object[]{Constants.BROWSER.FIREFOX.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.WINDOWS11.label()},
                        new Object[]{Constants.BROWSER.FIREFOX.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.WINDOWS10.label()},
                        new Object[]{Constants.BROWSER.FIREFOX.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.WINDOWS81.label()},
                        new Object[]{Constants.BROWSER.FIREFOX.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.MACOSMONTEREY.label()},
                        new Object[]{Constants.BROWSER.FIREFOX.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.MACOSBIGSUR.label()},
                        new Object[]{Constants.BROWSER.FIREFOX.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.MACOSCATALINA.label()},

                        new Object[]{Constants.BROWSER.MICROSOFTEDGE.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.MACOSMONTEREY.label()},
                        new Object[]{Constants.BROWSER.MICROSOFTEDGE.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.MACOSBIGSUR.label()},
                        new Object[]{Constants.BROWSER.MICROSOFTEDGE.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.MACOSCATALINA.label()},

                        new Object[]{Constants.BROWSER.SAFARI.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.MACOSMONTEREY.label()},
                        new Object[]{Constants.BROWSER.SAFARI.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.MACOSBIGSUR.label()},
                        new Object[]{Constants.BROWSER.SAFARI.label(), Constants.VERSION.LATEST.label(), Constants.PLATFORM.MACOSCATALINA.label()},
                };
                return configs;
        }
    }

    protected void createDriver(String browser, String version, String os, String methodName, Region region) throws MalformedURLException {
        AbstractDriverOptions browserOptions = switch (Constants.BROWSER.valueOf(browser.toUpperCase(Locale.ROOT))) {
            case CHROME -> new ChromeOptions();
            case FIREFOX -> new FirefoxOptions();
            case MICROSOFTEDGE -> new EdgeOptions();
            case SAFARI -> new SafariOptions();
            default -> null;
        };

        browserOptions.setPlatformName(os);
        browserOptions.setBrowserVersion(version);

        Map<String, Object> sauceOptions = new HashMap<>();

        sauceOptions.put("name", methodName);
        sauceOptions.put("build", Objects.requireNonNullElseGet(buildTag, () -> "YiMin-Local-Java-Selenium-Web-" + localBuildTag));

        if (tags != null) {
            sauceOptions.put("tags", Arrays.asList(tags.split(",")));
        }

        browserOptions.setCapability("sauce:options", sauceOptions);

        remoteWebDriver.set(new RemoteWebDriverExtended(createDriverURL(region), browserOptions));
        getRemoteWebDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        getRemoteWebDriver().get("https://www.saucedemo.com");
        annotate("Sending valid username");
        getRemoteWebDriver().findElement(By.cssSelector("#user-name")).sendKeys("standard_user");
        annotate("Sending valid password");
        getRemoteWebDriver().findElement(By.cssSelector("#password")).sendKeys("secret_sauce");
        annotate("Clicking login button");
        getRemoteWebDriver().findElement(By.cssSelector("#login-button")).click();
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