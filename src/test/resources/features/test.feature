Feature: Web Test Scenario

  Background:
    Given Setup Driver "chrome"

  Scenario: Test
    Given I wait for "2" seconds
    And I go to "https://prepweb.flypgs.com/booking"
    And I click the "Icon" element