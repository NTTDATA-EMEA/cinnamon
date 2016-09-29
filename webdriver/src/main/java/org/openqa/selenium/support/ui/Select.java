/*
Copyright 2007-2009 Selenium committers

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package org.openqa.selenium.support.ui;

import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

/**
 * Models a SELECT tag, providing helper methods to select and deselect options.
 */
public class Select {

    private final WebElement element;
    private final boolean isMulti;

    /**
     * Constructor. A check is made that the given element is, indeed, a SELECT tag. If it is not, then an
     * UnexpectedTagNameException is thrown.
     * 
     * @param element SELECT element to wrap
     * @throws UnexpectedTagNameException when element is not a SELECT
     */
    public Select(final WebElement element) {
        final String tagName = element.getTagName();

        if (null == tagName || !"select".equals(tagName.toLowerCase())) {
            throw new UnexpectedTagNameException("select", tagName);
        }

        this.element = element;

        final String value = element.getAttribute("multiple");

        // The atoms normalize the returned value, but check for "false"
        isMulti = (value != null && !"false".equals(value));
    }

    /**
     * @return Whether this select element support selecting multiple options at the same time? This is done by checking
     *         the value of the "multiple" attribute.
     */
    public boolean isMultiple() {
        return isMulti;
    }

    /**
     * @return All options belonging to this select tag
     */
    public List<WebElement> getOptions() {
        return element.findElements(By.tagName("option"));
    }

    /**
     * @return All selected options belonging to this select tag
     */
    public List<WebElement> getAllSelectedOptions() {
        final List<WebElement> toReturn = getOptions().stream().filter(WebElement::isSelected).collect(Collectors.toList());

        return toReturn;
    }

    /**
     * @return The first selected option in this select tag (or the currently selected option in a normal select)
     * @throws NoSuchElementException If no option is selected
     */
    public WebElement getFirstSelectedOption() {
        for (final WebElement option : getOptions()) {
            if (option.isSelected()) {
                return option;
            }
        }

        throw new NoSuchElementException("No options are selected");
    }

    /**
     * Select all options that display text containing the argument. That is, when given "Bar" this would select an
     * option like:
     * 
     * &lt;option value="foo"&gt;Bar&lt;/option&gt; &lt;option value="bar"&gt;Bargain&lt;/option&gt; &lt;option
     * value="baz"&gt;Foo Bar&lt;/option&gt;
     * 
     * @param text The visible text to match against
     * @throws NoSuchElementException If no matching option elements are found
     */
    public void selectByVisibleTextContains(final String text) {
        selectByVisibleTextStrategy(new VisibleTextMatcher() {

            @Override
            public By matching() {
                return By.xpath(".//option[contains( normalize-space(.)," + escapeQuotes(text) + ")]");
            }

            @Override
            public boolean match(final WebElement option) {
                return option.getText().contains(text);
            }

            @Override
            public String getTextToMatch() {
                return text;
            }
        });
    }

    /**
     * Select all options that display text matching the argument. That is, when given "Bar" this would select an option
     * like:
     * 
     * &lt;option value="foo"&gt;Bar&lt;/option&gt;
     * 
     * @param text The visible text to match against
     * @throws NoSuchElementException If no matching option elements are found
     */
    public void selectByVisibleText(final String text) {

        selectByVisibleTextStrategy(new VisibleTextMatcher() {

            @Override
            public By matching() {
                return By.xpath(".//option[normalize-space(.) = " + escapeQuotes(text) + "]");
            }

            @Override
            public boolean match(final WebElement option) {
                return text.equals(option.getText());
            }

            @Override
            public String getTextToMatch() {
                return text;
            }
        });

    }

    private void selectByVisibleTextStrategy(final VisibleTextMatcher matcher) {
        // try to find the option via XPATH ...
        final List<WebElement> options = element.findElements(matcher.matching());

        boolean matched = false;
        for (final WebElement option : options) {
            setSelected(option);
            if (!isMultiple()) {
                return;
            }
            matched = true;
        }

        final String text = matcher.getTextToMatch();
        if (options.isEmpty() && text.contains(" ")) {
            final String subStringWithoutSpace = getLongestSubstringWithoutSpace(text);
            List<WebElement> candidates;
            if ("".equals(subStringWithoutSpace)) {
                // hmm, text is either empty or contains only spaces - getWebDriver all options ...
                candidates = element.findElements(By.tagName("option"));
            } else {
                // getWebDriver candidates via XPATH ...
                candidates = element.findElements(By.xpath(".//option[contains(., " + escapeQuotes(subStringWithoutSpace) + ")]"));
            }
            for (final WebElement option : candidates) {
                if (matcher.match(option)) {
                    setSelected(option);
                    if (!isMultiple()) {
                        return;
                    }
                    matched = true;
                }
            }
        }

        if (!matched) {
            throw new NoSuchElementException("Cannot locate element with text: " + text);
        }
    }

    private interface VisibleTextMatcher {
        /**
         * @return a By that will attempt to find elements matching this condition
         */
        By matching();

        /**
         * Does the given element match this condition
         * 
         * @param e The element
         * @return true if the element matches this condition
         */
        boolean match(WebElement e);

        /**
         * @return The string to match
         */
        String getTextToMatch();
    }

    private String getLongestSubstringWithoutSpace(final String s) {
        String result = "";
        final StringTokenizer st = new StringTokenizer(s, " ");
        while (st.hasMoreTokens()) {
            final String t = st.nextToken();
            if (t.length() > result.length()) {
                result = t;
            }
        }
        return result;
    }

    /**
     * Select the option at the given index. This is done by examing the "index" attribute of an element, and not merely
     * by counting.
     * 
     * @param index The option at this index will be selected
     * @throws NoSuchElementException If no matching option elements are found
     */
    public void selectByIndex(final int index) {
        final String match = String.valueOf(index);

        boolean matched = false;
        for (final WebElement option : getOptions()) {
            if (match.equals(option.getAttribute("index"))) {
                setSelected(option);
                if (!isMultiple()) {
                    return;
                }
                matched = true;
            }
        }
        if (!matched) {
            throw new NoSuchElementException("Cannot locate option with index: " + index);
        }
    }

    /**
     * Select all options that have a value matching the argument. That is, when given "foo" this would select an option
     * like:
     * 
     * &lt;option value="foo"&gt;Bar&lt;/option&gt;
     * 
     * @param value The value to match against
     * @throws NoSuchElementException If no matching option elements are found
     */
    public void selectByValue(final String value) {
        String builder = ".//option[@value = " + escapeQuotes(value) + "]";
        final List<WebElement> options = element.findElements(By.xpath(builder));

        boolean matched = false;
        for (final WebElement option : options) {
            setSelected(option);
            if (!isMultiple()) {
                return;
            }
            matched = true;
        }

        if (!matched) {
            throw new NoSuchElementException("Cannot locate option with value: " + value);
        }
    }

    /**
     * Clear all selected entries. This is only valid when the SELECT supports multiple selections.
     * 
     * @throws UnsupportedOperationException If the SELECT does not support multiple selections
     */
    public void deselectAll() {
        if (!isMultiple()) {
            throw new UnsupportedOperationException("You may only deselect all options of a multi-select");
        }

        getOptions().stream().filter(WebElement::isSelected).forEachOrdered(WebElement::click);
    }

    /**
     * Deselect all options that have a value matching the argument. That is, when given "foo" this would deselect an
     * option like:
     * 
     * &lt;option value="foo"&gt;Bar&lt;/option&gt;
     * 
     * @param value The value to match against
     * @throws NoSuchElementException If no matching option elements are found
     */
    public void deselectByValue(final String value) {
        String builder = ".//option[@value = " + escapeQuotes(value) + "]";
        final List<WebElement> options = element.findElements(By.xpath(builder));
        options.stream().filter(WebElement::isSelected).forEachOrdered(WebElement::click);
    }

    /**
     * Deselect the option at the given index. This is done by examing the "index" attribute of an element, and not
     * merely by counting.
     * 
     * @param index The option at this index will be deselected
     * @throws NoSuchElementException If no matching option elements are found
     */
    public void deselectByIndex(final int index) {
        final String match = String.valueOf(index);

        getOptions().stream().filter(option -> match.equals(option.getAttribute("index")) && option.isSelected()).forEachOrdered(WebElement::click);
    }

    /**
     * Deselect all options that display text matching the argument. That is, when given "Bar" this would deselect an
     * option like:
     * 
     * &lt;option value="foo"&gt;Bar&lt;/option&gt;
     * 
     * @param text The visible text to match against
     * @throws NoSuchElementException If no matching option elements are found
     */
    public void deselectByVisibleText(final String text) {
        String builder = ".//option[normalize-space(.) = " + escapeQuotes(text) + "]";
        final List<WebElement> options = element.findElements(By.xpath(builder));
        options.stream().filter(WebElement::isSelected).forEachOrdered(WebElement::click);
    }

    protected String escapeQuotes(final String toEscape) {
        // Convert strings with both quotes and ticks into: foo'"bar -> concat("foo'", '"', "bar")
        if (toEscape.contains("\"") && toEscape.contains("'")) {
            boolean quoteIsLast = false;
            if (toEscape.lastIndexOf("\"") == toEscape.length() - 1) {
                quoteIsLast = true;
            }
            final String[] substrings = toEscape.split("\"");

            final StringBuilder quoted = new StringBuilder("concat(");
            for (int i = 0; i < substrings.length; i++) {
                quoted.append("\"").append(substrings[i]).append("\"");
                quoted.append(((i == substrings.length - 1) ? (quoteIsLast ? ", '\"')" : ")") : ", '\"', "));
            }
            return quoted.toString();
        }

        // Escape string with just a quote into being single quoted: f"oo -> 'f"oo'
        if (toEscape.contains("\"")) {
            return String.format("'%s'", toEscape);
        }

        // Otherwise return the quoted string
        return String.format("\"%s\"", toEscape);
    }

    private void setSelected(final WebElement option) {
        if (!option.isSelected()) {
            option.click();
        }
    }
}
