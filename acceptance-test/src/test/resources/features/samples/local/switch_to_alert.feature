@local @switch_alert
Feature: Switch to alert
  In order to test applications
  As a tester
  I want the ability to interact with alerts

  Background: On page with link that opens new window
    Given I have navigated to the local page "/switch_alert.html"

  @complete
  Scenario: Accept Alert
    Given a new alert has been created
    When I accept the alert
    Then the alert should be closed

  @complete
  Scenario: Accept slow loading Alert
    Given a new slow loading alert has been created
    When I accept the alert
    Then the alert should be closed

  #https://bugs.chromium.org/p/chromedriver/issues/detail?id=26&colspec=ID%20Status%20Pri%20Owner%20Summary&start=100
  @bug
  Scenario: Dismiss Alert
    Given a new alert has been created
    When I dismiss the alert
    Then the alert should be closed

  @complete
  Scenario: Accept Confirm Alert
    Given a new confirm alert has been created
    When I accept the alert
    Then the alert should be closed
    And the alert return value should should equal "true"

  @complete
  Scenario: Dismiss Confirm Alert
    Given a new confirm alert has been created
    When I dismiss the alert
    Then the alert should be closed
    And the alert return value should should equal "false"

  @complete
  Scenario Outline: Accept Prompt Alert with text input
    Given a new prompt alert has been created
    When I enter the text "<text input>" into the alert
    When I accept the alert
    Then the alert return value should should equal "<return value>"

    Examples:
      | text input | return value |
      | typeText   | typeText     |
      |            |              |

  @complete
  Scenario: Dismiss Prompt Alert with text input
    Given a new prompt alert has been created
    When I enter the text "typeText" into the alert
    When I dismiss the alert
    Then the alert return value should should equal ""
