package io.magentys.cinnamon.webdriver.actions;

import org.openqa.selenium.WebElement;

/**
 * Perform actions on an element
 */
public interface Actions {

    /**
     * Returns a PointActions instance which will produce actions performed with the given offset from the top left
     * corner of an element.
     * <p>
     * Example: <code>
     * action().byOffset(10,15).click().perform(element)
     * </code>
     * <p>
     * This will perform a click operation at the point equivalent to (element.getLocation().x+10,
     * element.getLocation().y+15)
     *
     * @param target the target element for the offset action
     * @param x      the horizontal offset from the top-left corner
     * @param y      the vertical offset from the top-left corner
     * @return a PointActions instance
     */
    PointActions byOffset(WebElement target, int x, int y);

    KeyStrokeActions withKeyStrokeInterval(WebElement target, long intervalMillis);

    /**
     * Clicks on an element
     *
     * @param target the element to click
     */
    void click(WebElement target);

    /**
     * Returns a SelectAction that enables interactions with a Select element
     *
     * @param target the select element
     * @return An action that enables interaction with a Select element
     */
    SelectAction select(WebElement target);

    /**
     * Deletes the text from an element. The text is deleted one character at a time.
     *
     * @param target The element to delete text from
     */
    void deleteContent(WebElement target);

    /**
     * Clear text from an element
     *
     * @param target The element to clear text from
     */
    void clear(WebElement target);

    /**
     * Returns a SendKeysAction that sends keys to the element in sequence with an optional keystroke delay.
     *
     * @param target     The element to send keys
     * @param keysToSend The keys to send
     */
    void typeText(WebElement target, CharSequence... keysToSend);

    void replaceText(WebElement target, CharSequence... keysToSend);

    /**
     * Double-clicks on the given element
     *
     * @param target the element to double-click
     */
    void doubleClick(WebElement target);

    /**
     * Scrolls the given element into the viewport
     *
     * @param target the element to scroll
     */
    void scrollIntoView(WebElement target);

    /**
     * Hovers over the given element
     *
     * @param target the element to hover over
     */
    void hoverOver(WebElement target);
}