package io.magentys.cinnamon.converter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectionsToMapConverter {

    public Map<String, String> convert(final List<String> keys, final List<String> values) {

        if (keys == null || values == null || keys.size() != values.size()) {
            throw new IllegalArgumentException(String.format("Keys and values must be non null and the same size: keys:%s, values:%s", keys, values));
        }

        final Map<String, String> converted = new HashMap<>();

        for (int i = 0; i < keys.size(); i++) {
            final String key = keys.get(i);
            final String value = values.get(i);

            converted.put(key, value);
        }

        return converted;
    }

}
