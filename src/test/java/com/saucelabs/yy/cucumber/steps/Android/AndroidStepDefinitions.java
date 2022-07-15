package com.saucelabs.yy.cucumber.steps.Android;

import com.saucelabs.yy.cucumber.tests.RunAndroidTests;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.java.Status;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;

public class AndroidStepDefinitions extends RunAndroidTests {

    public ThreadLocal<AndroidDriver> driver = RunAndroidTests.driver;

    @When("I click on the hamburger menu icon")
    public void iClickOnTheHamburgerMenuIcon() {
        driver.get().findElement(AppiumBy.xpath("//android.view.ViewGroup[@content-desc=\"open menu\"]/android.widget.ImageView")).click();
    }

    @And("I click on the Logout menu item")
    public void iClickOnTheLogoutMenuItem() {
        driver.get().findElement(AppiumBy.accessibilityId("menu item log out")).click();
    }

    @And("I confirm the alert")
    public void iConfirmTheAlert() {
        driver.get().findElement(AppiumBy.id("android:id/button1")).click();
    }

    @And("I enter username")
    public void iEnterUsername() {
        driver.get().findElement(AppiumBy.accessibilityId("Username input field")).sendKeys("bob@example.com");
    }

    @And("I enter password")
    public void iEnterPassword() {
        driver.get().findElement(AppiumBy.accessibilityId("Password input field")).sendKeys("10203040");
    }

    @And("I click the Login button")
    public void iClickTheLoginButton() {
        driver.get().findElement(AppiumBy.accessibilityId("Login button")).click();
    }

    @Then("I see the payment screen")
    public void iSeeThePaymentScreen() {
        Assert.assertEquals(driver.get().findElement(AppiumBy.accessibilityId("To Payment button")).getAttribute("content-desc"), "To Payment button");
    }

    @After
    public void teardown(Scenario scenario) {
        driver.get().executeScript("sauce:job-name=" + scenario.getName());
        ((JavascriptExecutor) driver.get()).executeScript("sauce:job-result=" + (scenario.getStatus().equals(Status.PASSED) ? "passed" : "failed"));
        driver.get().quit();
    }
}
