@local
Feature: Input Actions
  In order to test applications
  As a tester
  I want to input data into a page

  Background: On page with inputs
	Given I have navigated to the local page "/input.html"

  @complete
  Scenario: Delete the text from an input
	When I choose to delete the text from "withContent"
	Then "withContent" shall contain ""

  @complete
  Scenario: Clear the text from an input
	When I choose to clear the text from "withContent"
	Then "withContent" shall contain ""

  @complete
  Scenario: Type text into input, clearing the input first
	When I choose to type "This is my new content" from "withContent"
	Then "withContent" shall contain "This is my new content"
	
  @complete
  Scenario: Type text into input, with a delay
	When I choose to type "This is my new content" from "withContent" with keystroke delay 50 milliseconds
	Then "withContent" shall contain "This is my new content"
	And it shall take over 1 seconds

  @complete
  Scenario: Type text into input, using Selenium Keys enum
	When I choose to type "This is my con, BACK_SPACE, BACK_SPACE, BACK_SPACE,new content" from "withContent"
	Then "withContent" shall contain "This is my new content"
	
  @complete
  Scenario: Type special characters into input using Selenium Keys enum
   When I choose to send keys "BACK_SPACE,MULTIPLY" into "withContent"
   Then "withContent" shall contain "I contain conten*"
   
  @complete
  Scenario: Double click on an element
	When I choose to click on "b1"
	Then I should see "You double clicked button: NONE"
	When I choose to double click on "b1"
	Then I should see "You double clicked button: B1"

  @complete	
  Scenario: Scroll an element into view
	When I choose to scroll element "offscreen" into view
	Then I should see element "offscreen" in the viewport
	And I should not see element "b1" in the viewport
	
  @complete
  Scenario: Multiple Elements have the same locator but different text / attribute
    Given the following elements:
    	| locator     | name attribute 	| value         |
    	| sameLocator | user            | username 		|
    	| sameLocator | password        | password  	|
    When I choose to type "This is my user name" for element "sameLocator" with "name" "user"
    Then the following elements shall be displayed:
        | locator   	| name attribute 	| value             	|
        | sameLocator 	| user             	| This is my user name 	|
        | sameLocator 	| password         	| password              |
    When I choose to type "This is my password" for element "sameLocator" with "name" "password"
    Then the following elements shall be displayed:
        | locator     | name attribute 	| value      	        |
        | sameLocator | user          	| This is my user name 	|
        | sameLocator | password        | This is my password   |
