package com.saucelabs.yy.Tests.ATD.Pages.iOS.Fast;

import org.openqa.selenium.By;

public class Page {
    public static By txtLogin = By.id("test-Username");
    public static By txtPassword = By.id("test-Password");
    public static By btnLogin = By.id("test-LOGIN");
    public static By eleFirstItem = By.xpath("(//XCUIElementTypeOther[@name=\"test-ADD TO CART\"])[1]");
    public static By eleSecondItem = By.xpath("(//XCUIElementTypeOther[@name=\"test-ADD TO CART\"])[2]");
    public static By eleCart = By.xpath("(//XCUIElementTypeOther[@name=\"2\"])[4]");
    public static By eleCheckout = By.id("test-CHECKOUT");
    public static By txtFirstName = By.id("test-First Name");
    public static By txtLastName = By.id("test-Last Name");
    public static By txtZipCode = By.id("test-Zip/Postal Code");
    public static By btnContinue = By.id("test-CONTINUE");
    public static By btnFinish = By.id("test-FINISH");
    public static By eleThankYou = By.id("THANK YOU FOR YOU ORDER");
    public static By btnBackHome = By.id("test-BACK HOME");
    public static By btnRemove = By.id("test-REMOVE");
}
