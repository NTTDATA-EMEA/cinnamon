package io.magentys.cinnamon.converter;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ListConverter<S, T> implements Converter<List<S>, List<T>> {

    private final Converter<S, T> converter;

    public ListConverter(final Converter<S, T> converter) {
        this.converter = converter;
    }

    @Override
    public List<T> convert(final List<S> source) {

        final List<T> converted = source.stream().map(converter::convert).collect(Collectors.toCollection(LinkedList::new));

        return converted;
    }

}
