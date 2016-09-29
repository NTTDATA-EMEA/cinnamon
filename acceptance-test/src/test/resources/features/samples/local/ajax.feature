@local @ajax
Feature: Ajax Finished Condition
  In order to test the AjaxFinishedCondition
  As a tester
  I want to trigger a high latency ajax call

  Background:
	Given I have navigated to the local page "/ajax.html"
	
  @complete
  Scenario: Ajax finishes within timeout
	Given I have triggered a high latency ajax call
	Then I should see response text if the timeout for ajax to finish is set to 10 seconds

  @complete
  Scenario: Ajax does not finish within timeout
	Given I have triggered a high latency ajax call
	Then I should not see response text if the timeout for ajax to finish is set to 2 seconds
	