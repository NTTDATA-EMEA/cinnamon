package io.magentys.cinnamon.converter;

import static org.junit.Assert.assertEquals;

import io.magentys.cinnamon.converter.CamelCaseFieldNameConverter;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class CamelCaseFieldNameConverterTest {

    @Parameter(0)
    public String input;
    @Parameter(1)
    public String expected;

    @Parameters
    public static Collection<Object[]> params() {
        // @formatter:off
        final Object[][] data = { { "foo", "foo" }, { "Foo", "foo" },

                // Camel case
                { "FooBarBaz", "fooBarBaz" },

                // Spaces to camel case
                { "foo bar baz", "fooBarBaz" },

                // ignore empty string/null
                { "", "" }, { null, "" },
                
                // illegal characters
                { "foo/Bar", "fooBar" },
                { "foo / Bar", "fooBar" },
                { "foo/bar/baz", "fooBarBaz" },

        };
        // @formatter:on
        return Arrays.asList(data);
    }

    @Test
    public void testConversion() {

        final String actual = new CamelCaseFieldNameConverter().convert(input);
        assertEquals(expected, actual);
    }

}
