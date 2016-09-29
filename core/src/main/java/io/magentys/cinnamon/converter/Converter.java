package io.magentys.cinnamon.converter;

public interface Converter<S, T> {

    T convert(S source);
}
