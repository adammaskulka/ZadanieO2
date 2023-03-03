Feature: Scratch

  Background:
    Given I open app
    And I am on dashboard
    And I click on Scratch button on dashboard

  @scratch
  Scenario: Scratch card
    Given I am on Scratch screen
    And I see Scratch init state
    When I click on Scratch button
    Then I see Scratch loading state
    When Scratching of card succeeds
    Then I see Scratch success state
    When I go back to dashboard
    Then I see card state "state_scratched"
    And scratch button is "disabled"
    And activate button is "enabled"