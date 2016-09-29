#encoding: ISO-8859-1
@google
Feature: Google search
	In order to retrieve search results that are relevant
	As a user
	I want to be able to enter a filter

  Background:
    Given I have opened google
    Then I should see the home page
	
  @complete
  Scenario: Users can perform a search
    When I search for "magentys"
    Then I should see the results page
    And I should see only relevant results
  
  @complete
  Scenario: Perform search without filter
    When I search for ""
    Then I should not see the results page
	
  @complete
  Scenario: Perform search without filter (inline table)
    When I search for:
      | term     |
      |          |
    Then I should not see the results page
   
  @complete
  Scenario Outline: Perform search without filter (inline + examples table)
    When I search for:
      | term   |
      | <term> |
    Then I should not see the results page
    
    Examples:
      | term       |
      |            |
      