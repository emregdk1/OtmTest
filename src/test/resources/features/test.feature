Feature: Web Test Scenario

  Background:
    Given Setup Driver "chrome"

  Scenario: Test
    And Go to "https://webct.flypgs.com/" address
    And Wait for the element "BUTTON" in "Home Page"
    And Click to element "BUTTON" in "Home Page"
    And Check if element "ICON" exists and log the current text in "Home Page"
    And User

    