Feature: Web Test Scenario

  Background:
    Given Setup Driver "chrome"

  Scenario: Test
    And Go to "https://webct.flypgs.com/" address
    And Wait for the element "Icon" in "Home Page"
    And User tries to click on the key "Icon" on the page name "Home Page"
    
    