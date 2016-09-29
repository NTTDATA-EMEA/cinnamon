@local @switch_window
Feature: Switch to window
  In order to test applications
  As a tester
  I want the ability to switch to window

  Background: On page with link that opens new window
    Given I have navigated to the local page "/switch_window_first.html"

  @complete
  Scenario: Switch to the last opened window
    Given a new window has been opened
    When I switch to the new window
    Then I should see the text "Last" in the new window

  @complete
  Scenario: Switch to the last opened window with more than 2 windows open
    Given two new windows have been opened
    When I switch to the newest window
    Then I should see the text "Last" in the new window

  @complete
  Scenario Outline: Switch to first window
    Given a new window has been opened
    When I close the new window
    And I switch to the <nth> window
    Then I should see the text "First" in the first window

    Examples: 
      | nth   |
      | first |
      | last  |

  @complete
  Scenario: Switch to the window by title
    Given a new window has been opened
    When I switch to the window with title "Last Window"
    Then I should see the text "Last" in the new window

  @complete
  Scenario: Switch to the window by title regular expression
    Given a new window has been opened
    When I switch to the window with partial title "Last"
    Then I should see the text "Last" in the new window
