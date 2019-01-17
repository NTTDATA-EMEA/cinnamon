package com.acme.samples.local.pages.table;

public class TranslationTable {

    private String lol;
    private String english;
    private String pirate;

    public TranslationTable() {

    }

    public TranslationTable(String lol, String english, String pirate) {
        this.lol = lol;
        this.english = english;
        this.pirate = pirate;
    }

    public String toString() {
        return "[lol: " + lol + ", english: " + english + ", pirate: " + pirate + "]";
    }
}
