package com.saucelabs.yy.Tests.ATD.Pages.iOS.Slow;

import org.openqa.selenium.By;

public class Page {
    public static By txtLogin = By.xpath("//XCUIElementTypeTextField[@name=\"test-Username\"]");
    public static By txtPassword = By.xpath("//XCUIElementTypeSecureTextField[@name=\"test-Password\"]");
    public static By btnLogin = By.xpath("//XCUIElementTypeOther[@name=\"test-LOGIN\"]");
    public static By eleFirstItem = By.xpath("(//XCUIElementTypeOther[@name=\"test-ADD TO CART\"])[1]");
    public static By eleSecondItem = By.xpath("(//XCUIElementTypeOther[@name=\"test-ADD TO CART\"])[2]");
    public static By eleCart = By.xpath("(//XCUIElementTypeOther[@name=\"2\"])[4]");
    public static By eleCheckout = By.xpath("//XCUIElementTypeOther[@name=\"test-CHECKOUT\"]");
    public static By txtFirstName = By.xpath("//XCUIElementTypeTextField[@name=\"test-First Name\"]");
    public static By txtLastName = By.xpath("//XCUIElementTypeTextField[@name=\"test-Last Name\"]");
    public static By txtZipCode = By.xpath("//XCUIElementTypeTextField[@name=\"test-Zip/Postal Code\"]");
    public static By btnContinue = By.xpath("//XCUIElementTypeOther[@name=\"test-CONTINUE\"]");
    public static By btnFinish = By.xpath("//XCUIElementTypeOther[@name=\"test-FINISH\"]");
    public static By eleThankYou = By.xpath("//XCUIElementTypeStaticText[@name=\"THANK YOU FOR YOU ORDER\"]");
    public static By btnBackHome = By.xpath("//XCUIElementTypeOther[@name=\"test-BACK HOME\"]");
    public static By btnRemove = By.xpath("//XCUIElementTypeOther[@name=\"test-REMOVE\"]");
}
