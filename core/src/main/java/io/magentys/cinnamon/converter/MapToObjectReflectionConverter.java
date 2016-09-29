package io.magentys.cinnamon.converter;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Map.Entry;

public class MapToObjectReflectionConverter<T> implements Converter<Map<String, String>, T> {

    private final Class<T> type;

    public MapToObjectReflectionConverter(final Class<T> type) {
        this.type = type;
    }

    @Override
    public T convert(final Map<String, String> source) {

        try {
            final T mappedType = type.newInstance();

            for (final Entry<String, String> entry : source.entrySet()) {

                try {
                    final Field fieldName = type.getDeclaredField(entry.getKey());

                    fieldName.setAccessible(true);
                    fieldName.set(mappedType, entry.getValue());
                } catch (final NoSuchFieldException e) {
                    // ignore
                } catch (final SecurityException e) {
                    throw new AssertionError("Failed to populate type: " + type, e);
                }
            }

            return mappedType;
        } catch (final InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException(type.getClass().getName() + " should have a public no-arguments constructor.", e);
        }

    }

}
