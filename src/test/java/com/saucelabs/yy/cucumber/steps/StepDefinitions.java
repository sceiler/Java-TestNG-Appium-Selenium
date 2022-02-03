package com.saucelabs.yy.cucumber.steps;

import com.saucelabs.yy.cucumber.tests.RunTest;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.java.Status;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.testng.Assert;

public class StepDefinitions extends RunTest {

    public ThreadLocal<AppiumDriver> driver = RunTest.driver;

    @Given("I open the iOS application")
    public void iOpenTheIOSApplication() {
        //do nothing
    }

    @Then("I should see Swag Labs login page")
    public void iShouldSeeSwagLabsLoginPage() {
        Assert.assertTrue(driver.get().findElement(By.id("test-LOGIN")).isDisplayed());
    }

    @When("I enter valid login credentials")
    public void iEnterValidLoginCredentials() {
        driver.get().findElement(By.id("test-Username")).sendKeys("standard_user");
        driver.get().findElement(By.id("test-Password")).sendKeys("secret_sauce");
    }

    @When("I enter invalid login credentials")
    public void iEnterInvalidLoginCredentials() {
        driver.get().findElement(By.id("test-Username")).sendKeys("bla");
        driver.get().findElement(By.id("test-Password")).sendKeys("bla");
    }

    @And("I click on login button")
    public void iClickOnLoginButton() {
        driver.get().findElement(By.id("test-LOGIN")).click();
    }

    @Then("take a screenshot")
    public void takeAScreenshot() {
        driver.get().getScreenshotAs(OutputType.FILE);
    }

    @Then("I am on the Inventory page")
    public void iAmOnTheInventoryPage() {
        Platform platform = driver.get().getCapabilities().getPlatformName();

        if (platform.is(Platform.ANDROID)) {
            Assert.assertTrue(driver.get().findElement(By.xpath("(//android.view.ViewGroup[@content-desc=\"test-ADD TO CART\"])[1]")).isDisplayed());
        } else if (platform.is(Platform.IOS)) {
            Assert.assertTrue(driver.get().findElement(By.xpath("(//XCUIElementTypeOther[@name=\"test-ADD TO CART\"])[1]")).isDisplayed());
        }
    }

    @Then("I have got an login error message")
    public void iHaveGotAnLoginErrorMessage() {
        Assert.assertTrue(driver.get().findElement(new AppiumBy.ByAccessibilityId("test-Error message")).isDisplayed());
    }

    @After
    public void teardown(Scenario scenario) {
        driver.get().executeScript("sauce:job-name=" + scenario.getName());
        ((JavascriptExecutor) driver.get()).executeScript("sauce:job-result=" + (scenario.getStatus().equals(Status.PASSED) ? "passed" : "failed"));
        driver.get().quit();
    }
}
