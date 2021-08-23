Feature: Login flows

  Scenario: Valid Login
    Given I open the iOS application
    When I enter valid login credentials
    And I click on login button
    Then I am on the Inventory page

  Scenario: Invalid Login
    Given I open the iOS application
    When I enter invalid login credentials
    And I click on login button
    Then I should see Swag Labs login page
    And I have got an login error message

  Scenario: Blank credentials
    Given I open the iOS application
    And I click on login button
    Then I should see Swag Labs login page
    And I have got an login error message