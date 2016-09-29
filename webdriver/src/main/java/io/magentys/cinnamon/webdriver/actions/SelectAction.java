package io.magentys.cinnamon.webdriver.actions;

public interface SelectAction {

    /**
     * Selects the option with the given index
     *
     * @param idx The index to select: idx >= 0
     * @return
     */
    void byIndex(int idx);

    /**
     * Selects the option by the "value" attribute
     *
     * @param value The value to select
     * @return
     */
    void byValue(String value);

    /**
     * Selects the option where the visible text matches the given text completely
     *
     * @param text The text to match
     * @return
     */
    void byVisibleText(String text);

    /**
     * An action that selects the option where the visible text contains the given text completely, as defined by
     * {@link String#contains(CharSequence)}
     *
     * @param text The text to match
     * @return
     */
    void byVisibleTextContains(String text);
}