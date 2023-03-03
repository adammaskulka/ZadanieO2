Feature: Dashboard

  @dashboard
  Scenario: Dashboard Init
    Given I open app
    Given I am on dashboard
    And I see card state "state_new"
    And scratch button is "enabled"
    And activate button is "disabled"
    And reset button is "gone"

  @dashboard
  Scenario: Dashboard - open Scratch
    Given I am on dashboard
    When I click on Scratch button on dashboard
    Then I'm redirected to Scratch screen

  @dashboard
  Scenario: Dashboard - open Activation
    Given I am on dashboard
    And I click on Scratch button on dashboard
    And I am on Scratch screen
    And I click on Scratch button
    And Scratching of card succeeds
    And I go back to dashboard
    When I click on Activate button on dashboard
    Then I'm redirected to Activate screen