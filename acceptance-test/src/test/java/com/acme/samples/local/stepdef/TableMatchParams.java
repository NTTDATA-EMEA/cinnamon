package com.acme.samples.local.stepdef;

import java.util.Map;

public class TableMatchParams {
    private final String row;
    private final String column;
    private final String value;

    public TableMatchParams(final String row, final String column, final String value) {
        this.row = row;
        this.column = column;
        this.value = value;
    }

    public String getRowHeading() {
        return row;
    }

    public String getColumnHeading() {
        return column;
    }

    public String getValue() {
        return value;
    }

    public static TableMatchParams fromMap(Map<String, String> map) {
        return new TableMatchParams(map.get("row"), map.get("column"), map.get("value"));
    }
}