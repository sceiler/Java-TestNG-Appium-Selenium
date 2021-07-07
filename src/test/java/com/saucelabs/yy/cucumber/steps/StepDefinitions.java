package com.saucelabs.yy.cucumber.steps;

import com.saucelabs.yy.cucumber.tests.RunTest;
import io.appium.java_client.AppiumDriver;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.java.Status;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

public class StepDefinitions extends RunTest {

    public ThreadLocal<AppiumDriver> driver = RunTest.driver;

    @Given("I open the iOS application")
    public void iOpenTheIOSApplication() {
        //do nothing
    }

    @Then("I should see Swag Labs login page")
    public void iShouldSeeSwagLabsLoginPage() {
        //WebElement loginField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("test-LOGIN")));
        Assert.assertTrue("Should see login field", driver.get().findElement(By.id("test-LOGIN")).isDisplayed());
    }

    @After
    public void teardown(Scenario scenario) {
        driver.get().executeScript("sauce:job-name=" + scenario.getName());
        ((JavascriptExecutor) driver.get()).executeScript("sauce:job-result=" + (scenario.getStatus().equals(Status.PASSED) ? "passed" : "failed"));
        driver.get().quit();
    }
}
