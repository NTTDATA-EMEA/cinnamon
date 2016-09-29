package io.magentys.cinnamon.converter;

import cucumber.runtime.table.CamelCaseStringConverter;

public class CamelCaseFieldNameConverter implements Converter<String, String> {

    @Override
    public String convert(final String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }

        return new CamelCaseStringConverter().map(input.replace("/", " "));
    }
}
