@local
Feature: Offset Actions
  In order to test applications
  As a tester
  I want to perform operations on an action using an offset

  Background: On page with elements with absolute position
	Given I have navigated to the local page "/absolute.html"
	
  @complete
  Scenario: Click on element with positive offset
	When I choose to click button "b1" with offset (110,0)
	Then I should see "You clicked button: B2"
