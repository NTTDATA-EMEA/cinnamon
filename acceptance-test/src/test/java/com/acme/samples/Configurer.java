package com.acme.samples;

import cucumber.api.TypeRegistry;
import cucumber.api.TypeRegistryConfigurer;
import io.cucumber.cucumberexpressions.ParameterType;
import io.cucumber.cucumberexpressions.Transformer;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static java.util.Locale.ENGLISH;

public class Configurer implements TypeRegistryConfigurer {

    @Override
    public Locale locale() {
        return ENGLISH;
    }

    @Override
    public void configureTypeRegistry(TypeRegistry typeRegistry) {
        typeRegistry.defineParameterType(new ParameterType<>("nth", "first|last", String.class, (Transformer<String>) String::new));
        typeRegistry.defineParameterType(new ParameterType<>("maybe", "should|should not", String.class, (Transformer<String>) String::new));
        typeRegistry.defineParameterType(new ParameterType<>("keys", ".*?", List.class, new Transformer<List>() {
            @Override
            public List transform(String string) throws Throwable {
                return Arrays.stream(string.replaceAll("\"", "").trim().split(",")).map(String::trim).collect(Collectors.toList());
            }
        }));
    }
}