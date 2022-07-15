Feature: Logout flows

  Scenario: Logout of app
    When I click on the hamburger menu icon
    And I click on the Logout menu item
    And I confirm the alert
    And I confirm the alert
    And I enter username
    And I enter password
    And I click the Login button
    Then I see the payment screen
