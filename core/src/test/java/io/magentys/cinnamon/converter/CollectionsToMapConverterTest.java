package io.magentys.cinnamon.converter;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;

import io.magentys.cinnamon.converter.CollectionsToMapConverter;

import java.util.*;

import org.hamcrest.Matcher;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class CollectionsToMapConverterTest {

    @Parameter(0)
    public boolean isValid;
    @Parameter(1)
    public List<String> keys;
    @Parameter(2)
    public List<String> values;
    @Parameter(3)
    public Matcher<Map<String, String>>[] expected;

    @Parameters
    public static Collection<Object[]> params() {
        final Object[][] data = {
                // nulls aren't allowed
                { false, null, Collections.emptyList(), null },
                { false, Collections.singletonList("key"), null, null },
                { false, null, null, null },
                // keys and values must be the same size
                { false, Collections.singletonList("toManyKeys"), Collections.emptyList(), null },
                { false, Arrays.asList("tooManyKeys", "toManyKeys2"), Collections.singletonList("value"), null },
                { false, Collections.emptyList(), Collections.singletonList("tooManyValues"), null },
                { false, Collections.singletonList("key"), Arrays.asList("tooManyValues", "tooManyValues2"), null },

                // empty lists are ok
                { true, Collections.emptyList(), Collections.emptyList(), new Matcher[] {} },

                // Duplicate values are ok
                { true, Arrays.asList("key1", "key2", "key3"), Arrays.asList("value1", "value1", "value1"),
                        new Matcher[] { hasEntry("key1", "value1"), hasEntry("key2", "value1"), hasEntry("key3", "value1") } },
                // Duplicate keys are overwritten
                { true, Arrays.asList("key1", "key1", "key1"), Arrays.asList("value1", "value2", "value3"),
                        new Matcher[] { hasEntry("key1", "value3") } },

                // sunny day
                { true, Arrays.asList("key1", "key2", "key3"), Arrays.asList("value1", "value2", "value3"),
                        new Matcher[] { hasEntry("key1", "value1"), hasEntry("key2", "value2"), hasEntry("key3", "value3") } },

        };
        return Arrays.asList(data);
    }

    @Test
    public void testValidConversion() {
        Assume.assumeTrue(isValid);

        final Map<String, String> actual = new CollectionsToMapConverter().convert(keys, values);

        Assert.assertThat(actual.size(), equalTo(expected.length));
        Assert.assertThat(actual, allOf(expected));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidConversionThrowsException() {
        Assume.assumeFalse(isValid);

        new CollectionsToMapConverter().convert(keys, values);
    }

}
