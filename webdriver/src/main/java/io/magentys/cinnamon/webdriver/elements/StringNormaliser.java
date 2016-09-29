package io.magentys.cinnamon.webdriver.elements;

public final class StringNormaliser {

    // Suppresses default constructor, ensuring non-instantiability.
    private StringNormaliser() {
    }

    /**
     * Normalises text as follows:
     * <p>
     * <ul>
     * <li>trims leading and trailing whitespace</li>
     * <li>replaces any kind of hyphen or dash character with a dash from a default character set</li>
     * <li>replaces all carriage return characters with a single space</li>
     * <li>replaces all new line characters with a single space</li>
     * <li>replaces all multiple whitespace with a single space</li>
     * <li>removes all non-ASCII characters</li>
     * </ul>
     *
     * @param text the text to be normalised
     * @return Normalised text
     */
    public static String normalise(String text) {
        return text.trim().replaceAll("\\p{Pd}", "-").replaceAll("\\r", " ").replaceAll("\\n", " ").replaceAll("\\s+", " ")
                .replaceAll("[^\\x20-\\x7e]", "");
    }
}