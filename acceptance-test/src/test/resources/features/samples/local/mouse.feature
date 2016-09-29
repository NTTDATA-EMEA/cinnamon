@local @mouse
Feature: Mouse actions
  In order to test pages that require the mouse
  As a tester
  I want to move the mouse around screen

  Background: On page with elements responding to mouse events
	Given I have navigated to the local page "/mouse.html"

	
  @complete	
  Scenario: Hover over an element
	When I choose to hover over element "withMouseEnter"
	Then I shall see the text "Element Hovered"

  @complete
  Scenario: Hover over an element with hover pseudo-class
	When I choose to hover over element "withHoverPseudoClass"
	Then I shall see the text "Pseudo Hovered"
