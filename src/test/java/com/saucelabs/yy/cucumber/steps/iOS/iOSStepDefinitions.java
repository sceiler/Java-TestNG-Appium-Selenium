package com.saucelabs.yy.cucumber.steps.iOS;

import com.saucelabs.yy.cucumber.tests.RunAndroidTests;
import com.saucelabs.yy.cucumber.tests.RuniOSTests;
import io.appium.java_client.ios.IOSDriver;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.java.Status;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.JavascriptExecutor;

public class iOSStepDefinitions extends RunAndroidTests {

    public ThreadLocal<IOSDriver> driver = RuniOSTests.driver;

    @Given("I open the iOS application")
    public void iOpenTheIOSApplication() {
        //do nothing
    }

    @Then("I should see Swag Labs login page")
    public void iShouldSeeSwagLabsLoginPage() {
    }

    @When("I enter valid login credentials")
    public void iEnterValidLoginCredentials() {
    }

    @When("I enter invalid login credentials")
    public void iEnterInvalidLoginCredentials() {
    }

    @And("I click on login button")
    public void iClickOnLoginButton() {

    }

    @Then("take a screenshot")
    public void takeAScreenshot() {

    }

    @Then("I am on the Inventory page")
    public void iAmOnTheInventoryPage() {

    }

    @Then("I have got an login error message")
    public void iHaveGotAnLoginErrorMessage() {

    }

    @After
    public void teardown(Scenario scenario) {
        driver.get().executeScript("sauce:job-name=" + scenario.getName());
        ((JavascriptExecutor) driver.get()).executeScript("sauce:job-result=" + (scenario.getStatus().equals(Status.PASSED) ? "passed" : "failed"));
        driver.get().quit();
    }
}
