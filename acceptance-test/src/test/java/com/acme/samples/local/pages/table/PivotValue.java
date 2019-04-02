package com.acme.samples.local.pages.table;

import java.util.HashMap;
import java.util.Map;

public class PivotValue {

    PivotValue(final String colour, final String year, final String value) {
        super();
        this.colour = colour;
        this.year = year;
        this.value = value;
    }

    String colour;
    String year;
    String value;

    public Map<String, String> asMap() {
        return new HashMap<String, String>() {{
            put("colour", colour);
            put("year", year);
            put("value", value);
        }};
    }
}