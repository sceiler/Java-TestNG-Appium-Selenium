package com.saucelabs.yy.Tests.Appium.SwagLabsApp.Pages.Android;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

import java.util.Set;

public class SwagLabsPage {

    By usernameEdit = By.id("test-Username");
    By passwordEdit = By.id("test-Password");
    public static By submitButton = By.id("test-LOGIN");
    By ProductTitle = By.xpath("//XCUIElementTypeStaticText[@name=\"PRODUCTS\"]\n");
    By testMenu = By.name("test-Menu");
    By testWebViewItem = By.name("test-WEBVIEW");
    By urlEdit = By.id("test-enter a https url here...");
    By goToSiteBtn = By.id("test-GO TO SITE");
    By usernameInputWeb = By.id("user-name");
    By passwordInputWeb = By.id("password");
    By submitButtonWeb = By.className("btn_action");

    public AndroidDriver driver;

    public SwagLabsPage(AppiumDriver driver) {
        this.driver = (AndroidDriver) driver;

    }

    public void login(String user, String pass) {
        try {
            driver.findElement(usernameEdit).click();
            driver.findElement(usernameEdit).sendKeys(user);

            driver.findElement(passwordEdit).click();
            driver.findElement(passwordEdit).sendKeys(pass);

            driver.findElement(submitButton).click();

        } catch (Exception e) {
            System.out.println("*** Problem to login: " + e.getMessage());
        }
    }

    public boolean isOnProductsPage() {
        return driver.findElement(ProductTitle).isDisplayed();
    }

    public void clickMenu() {
        driver.findElement(testMenu).click();
    }

    public void selectWebViewItem() {
        driver.findElement(testWebViewItem).click();
    }

    public void setWebURL(String urlText) {
        driver.findElement(urlEdit).click();
        driver.findElement(urlEdit).sendKeys(urlText);
        driver.findElement(goToSiteBtn).click();
    }

    public void loginWebView() {
        String currentContext = driver.getContext();
        System.out.println("*** current Context: " + currentContext);
        Set<String> contextNames = driver.getContextHandles();
        boolean bFoundWeb = false;
        for (int i = 1; i< 2; i++)
        {
            if (contextNames.size() == 1) {
                try {
                    System.out.println("i= " + i);
                    Thread.sleep(1000);
                    contextNames = driver.getContextHandles();
                } catch (InterruptedException e) {
                }
            }
            else {
                bFoundWeb = true;
                break;
            }
        }
        for (String contextName : contextNames) {
            System.out.println(contextName); //prints out something like NATIVE_APP \n WEBVIEW_1
        }

        //Object cont = ((JavascriptExecutor) driver).executeScript("mobile: getContexts");
        driver.context(contextNames.toArray()[1].toString()); // set context to WEBVIEW
        currentContext = driver.getContext();
        System.out.println("*** current Context: " + currentContext);
        driver.findElement(usernameInputWeb).sendKeys("eyal");
        // ... Need to add the webView test
        driver.context("NATIVE_APP");
    }
}
