@local @text
Feature: Inspect Text
  In order to inspect text
  As a tester
  I want to retrieve text from a page element

  Background:
	Given I have navigated to the local page "/text.html"
	
  @complete
  Scenario: Div element
	Then inspecting text of the "div" element should return "text"

  @complete
  Scenario: Hidden by CSS div element
	Then inspecting text of the "hidden by CSS div" element should return "hidden text"
	
  @complete
  Scenario: Hidden by attribute div element
	Then inspecting text of the "hidden by CSS div" element should return "hidden text"

  @complete
  Scenario: Span within div element
	Then inspecting text of the "span within div" element should return "text"

  @complete
  Scenario: Hidden by CSS span within div element
	Then inspecting text of the "hidden by CSS span within div" element should return "hidden text"
	
  @complete
  Scenario: Hidden by attribute span within div element
	Then inspecting text of the "hidden by attribute span within div" element should return "hidden text"
	
  @complete
  Scenario: Input element
    Then inspecting text of the "input" element should return "default"
    When I enter text "xyz" into "input"
	Then inspecting text of the "input" element should return "xyz"
	
  @complete
  Scenario: Hidden by CSS input element
	Then inspecting text of the "hidden by CSS input" element should return "hidden text"
	
  @complete
  Scenario: Hidden by attribute input element
	Then inspecting text of the "hidden by attribute input" element should return "hidden text"
	
  @complete
  Scenario: Textarea element
    Then inspecting text of the "textarea" element should return "text"
    When I enter text "abcdefg" into "textarea"
	Then inspecting text of the "textarea" element should return "abcdefg"
	
  @complete
  Scenario: Hidden by CSS textarea element
	Then inspecting text of the "hidden by CSS textarea" element should return "hidden text"
	
  @complete
  Scenario: Hidden by attribute textarea element
	Then inspecting text of the "hidden by attribute textarea" element should return "hidden text"
