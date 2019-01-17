@local @table
Feature: Tables
  In order to test applications
  As a tester
  I want to find things in a table

  Background: On table page
	Given I have navigated to the local page "/table.html"

  @complete
  Scenario: Find items dynamically
	When I look in "table1" for "When"
	Then I should find "WEN" in the same row
	But I should not find "bobbins" in the same row
	And I should not find "Given" in the same row

  @complete	
  Scenario: Click on a row
	When I click on the button in table "table3" for row "Text5"
	Then the result "Text5" should be displayed
	
  @complete
  Scenario: Compare a table
	Then table2 should contain:
	  | English | Pirate   		    | Lol     |
	  | Given	| yarrrrr! 		    | bobbins |
	  | When    | avast!	 		| bobbins |
      | Then    | walk the plank!	| bobbins |
    
  @complete
  Scenario: Compare a table, found from a collection
	Then table "table2" should contain:
	  | English | Pirate   		  | Lol     |
	  | Given	| yarrrrr! 		  | bobbins |
	  | When    | avast!	 	  | bobbins |
      | Then    | walk the plank! | bobbins |

  @complete 
  Scenario: Compare a table, found from a collection
	Then table "table1" should contain:
	  | English | Pirate   		    | Lol       |
	  | Given	| Gangway! 		    | I CAN HAZ |
	  | When    | Blimey!	 	    | WEN       |
      | Then    | Let go and haul!  | DEN       |

  @complete
  Scenario Outline: Compare a pivot table, found from a collection
	Then pivot table "<table>" should contain:
	  | colour | year | value |
	  | Red    | 2008 |   1   |
	  | Red    | 2009 |   2   |
	  | Red    | 2010 |   3   |
	  | Blue   | 2008 |   4   |
	  | Blue   | 2009 |   5   |
	  | Blue   | 2010 |   6   |
	
	Examples:
	  | table                  |
	  | pivot                  |
	  | pivotWithThRowHeaders  |
	  | pivotWithNestedCells   |

  @complete
  Scenario: Compare a multicell pivot table, found from a collection
	Then multicell pivot table "pivotColspanMulticell" with colspan of 2 should contain:
	  | colour | year | value |
	  | Red    | 2008 |   1   |
	  | Red    | 2009 |   2   |
	  | Red    | 2010 |   3   |
	  | Blue   | 2008 |   4   |
	  | Blue   | 2009 |   5   |
	  | Blue   | 2010 |   6   |
	
  @complete
  Scenario Outline: Find a matching cell
   When I search for the first cell in "<table>" that matches:
     | row        | column      | value      |
     |<rowMatch>  |<colMatch>   | <cellMatch>|
   Then the found cell should be:
     | row | column | value |
     |<row>|<col>   | <val> |
   
   Examples:
     | table                       | rowMatch | colMatch | cellMatch | row  | col  | val |
     | pivot                       | Red      |          |           | Red  | 2008 | 1   |
     | pivot                       |          | 2009     |           | Red  | 2009 | 2   |
     | pivot                       |          |          | 5         | Blue | 2009 | 5   |
     | pivot                       | Red      | 2010     | 3         | Red  | 2010 | 3   |
     | pivotWithThRowHeaders       | Red      | 2010     | 3         | Red  | 2010 | 3   |
     | pivotWithNestedCells        | Red      | 2010     | 3         | Red  | 2010 | 3   |
     | pivotMulticellColumnHeader  | Red      |          |           | Red  | year | 1   |
     | pivotMulticellColumnHeader  |          | 2009     |           | Red  | 2009 | 2   |
     # FIXME - colspan headers are only passed once
     #| pivotMulticellColumnHeader  |          |          | 5         | Blue | year | 5   |
     | pivotMulticellColumnHeader  | Red      | 2010     | 3         | Red  | 2010 | 3   |
     | pivotMulticellColumnHeader2 | Red      |          |           | Red  | a    | 1   |
     | pivotMulticellColumnHeader2 |          | 2009     |           | Red  | 2009 | 2   |
     | pivotMulticellColumnHeader2 |          |          | 5         | Blue | b    | 5   |
     | pivotMulticellColumnHeader2 | Red      | 2010     | 3         | Red  | 2010 | 3   |

  @complete
  Scenario Outline: Fail to find matching cell
   When I search for the first cell in "<table>" that matches:
     | row        | column      | value      |
     |<rowMatch>  |<colMatch>   | <cellMatch>|
   Then no matching cell shall be found
   
   Examples:
     | table | rowMatch | colMatch | cellMatch | 
     | pivot | Green    |          |           | 
     | pivot |          | 2011     |           | 
     | pivot |          |          | 7         | 
     | pivot | Red      | 2010     | 5         | 

  @complete     
  Scenario: Adapt rows of a table
	When I choose to adapt "table2" using a row adapter
	Then the row adapter shall be called 3 times
	And the adapter shall be passed 3 "th" column headers each time
	And the adapter shall be passed 3 "td" cells each time
