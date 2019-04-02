package com.acme.samples.local.pages.table;

import java.util.HashMap;
import java.util.Map;

public class TranslationTable {

    private String lol;
    private String english;
    private String pirate;

    public Map<String, String> asMap() {
        return new HashMap<String, String>() {{
            put("lol", lol);
            put("english", english);
            put("pirate", pirate);
        }};
    }
}