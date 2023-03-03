Feature: Activation

  Background:
    Given I open app
    And I am on dashboard
    And I click on Scratch button on dashboard
    And I am on Scratch screen
    And I click on Scratch button
    And Scratching of card succeeds
    And I go back to dashboard
    And I click on Activate button on dashboard

  @activate
  Scenario: Activate card - Success code
    Given I am on Activate screen
    And I see Activate init state
    When I click on Activate button
    Then I see Activate loading state
    When Activation of card succeeds with code "99999"
    Then I see Activate success state
    When I go back to dashboard
    Then I see card state "state_activated"
    And scratch button is "disabled"
    And activate button is "disabled"
    And reset button is "visible"
    When I click on Reset button on dashboard
    Then I see card state "state_new"

  @activate
  Scenario: Activate card - Error response code + Retry
    Given I am on Activate screen
    And I see Activate init state
    When I click on Activate button
    Then I see Activate loading state
    When Activation of card succeeds with code "1111"
    Then I see Activate error state
    When I click on Retry button
    Then I see Activate loading state
    When Activation of card succeeds with code "80001"
    Then I see Activate success state