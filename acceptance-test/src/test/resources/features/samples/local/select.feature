@local
Feature: Select
  In order to test applications
  As a tester
  I want to choose options from a select element

  Background: On page with select element
	Given I have navigated to the local page "/select.html"

  @complete
  Scenario: Choose item by value
	When I choose value 3
	Then I should see "You selected value 3"
	Then I should see "You selected text Three"

  @complete
  Scenario: Choose item by text
	When I choose text "Two"
	Then I should see "You selected value 2"
	Then I should see "You selected text Two"

  @complete
  Scenario: Choose item by index
	When I choose index 1
	Then I should see "You selected value 4"
	Then I should see "You selected text Four"
	
  @complete
  Scenario Outline: Choose item by text contains - <rule>
	When I choose text contains "<substring>"
	Then I should see "You selected value <value>"
	Then I should see "You selected text <text>"
	
	Examples:
	  | substring     | value           | text                       | rule                               |
	  | Tw            | 2               | Two                        | only partial match                 |
	  | T             | 3               | Three                      | multiple matches picks first       |
	  | has spaces    | spaces          | This has spaces            | match item with spaces             |
	  | over multiple | spacesMultiline | Spread over multiple lines | match text spanning multiple lines |
