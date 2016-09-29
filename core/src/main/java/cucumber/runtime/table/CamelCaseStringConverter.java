/*
 * Copyright (c) 2008-2014 The Cucumber Organisation
 * 
 * Fork of https://github.com/cucumber/cucumber-jvm/blob/master/core/src/main/java/cucumber/runtime/table/CamelCaseStringConverter.java
 */

package cucumber.runtime.table;

import java.util.regex.Pattern;

public class CamelCaseStringConverter implements StringConverter {

    private static final String WHITESPACE = " ";
    private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\s+");

    @Override
    public String map(String string) {
        String[] splitted = normalizeSpace(string).split(WHITESPACE);
        splitted[0] = uncapitalize(splitted[0]);
        for (int i = 1; i < splitted.length; i++) {
            splitted[i] = capitalize(splitted[i]);
        }
        return join(splitted);
    }

    private String join(String[] splitted) {
        StringBuilder sb = new StringBuilder();
        for (String s : splitted) {
            sb.append(s);
        }
        return sb.toString();
    }

    private String normalizeSpace(String originalHeaderName) {
        return WHITESPACE_PATTERN.matcher(originalHeaderName.trim()).replaceAll(WHITESPACE);
    }

    private String capitalize(String string) {
        return String.valueOf(Character.toTitleCase(string.charAt(0))) + string.substring(1);
    }

    private String uncapitalize(String string) {
        return String.valueOf(Character.toLowerCase(string.charAt(0))) + string.substring(1);
    }

}
