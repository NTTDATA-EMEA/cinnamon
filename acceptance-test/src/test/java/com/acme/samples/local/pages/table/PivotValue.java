package com.acme.samples.local.pages.table;

public class PivotValue {

    public PivotValue(){

    }

    public PivotValue(final String colour, final String year, final String value) {
        super();
        this.colour = colour;
        this.year = year;
        this.value = value;
    }

    String colour;
    String year;
    String value;
}
