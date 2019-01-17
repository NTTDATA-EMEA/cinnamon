package com.acme.samples.local.stepdef;

public class TableMatchParams {
    private String row;
    private String column;
    private String value;

    public TableMatchParams() {

    }

    public TableMatchParams(String row, String column, String value) {
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
}
