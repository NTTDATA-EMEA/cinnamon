package io.magentys.cinnamon.converter;

import io.magentys.cinnamon.converter.MapToObjectReflectionConverter;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import com.google.common.collect.ImmutableMap;

@RunWith(Parameterized.class)
public class MapToObjectReflectionConverterTest {

    @Parameter(0)
    public Map<String, String> source;
    @Parameter(1)
    public MyObject expected;

    @Parameters
    public static Collection<Object[]> params() {
        final Object[][] data = {

                // Unknown fields are ignored
                { ImmutableMap.of("unknown", "unexpected", "val1", "1", "val2", "2", "val3", "3"), new MyObject("1", "2", "3") },

                // Partial population is allowed
                { ImmutableMap.of("val1", "1"), new MyObject("1", null, null) }, { ImmutableMap.of("val2", "1"), new MyObject(null, "1", null) },
                { ImmutableMap.of("val3", "1"), new MyObject(null, null, "1") },

        };
        return Arrays.asList(data);
    }

    @Test
    public void test() {

        final MapToObjectReflectionConverter<MyObject> converter = new MapToObjectReflectionConverter<>(MyObject.class);

        final MyObject actual = converter.convert(source);

        Assert.assertEquals(expected, actual);

    }

    @Test(expected = IllegalArgumentException.class)
    public void convertedClassesMustHaveNoArgsConstructor() {
        new MapToObjectReflectionConverter<>(ClassWithoutNoArgsConstructor.class).convert(source);
    }

    private static class ClassWithoutNoArgsConstructor {
        @SuppressWarnings("unused")
        public ClassWithoutNoArgsConstructor(final String argument) {
        }
    }

    private static class MyObject {

        @SuppressWarnings("unused")
        public MyObject() {
        }

        public MyObject(final String val1, final String val2, final String val3) {
            super();
            this.val1 = val1;
            this.val2 = val2;
            this.val3 = val3;
        }

        private String val1;
        private String val2;
        private String val3;

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((val1 == null) ? 0 : val1.hashCode());
            result = prime * result + ((val2 == null) ? 0 : val2.hashCode());
            result = prime * result + ((val3 == null) ? 0 : val3.hashCode());
            return result;
        }

        @Override
        public boolean equals(final Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            final MyObject other = (MyObject) obj;
            if (val1 == null) {
                if (other.val1 != null)
                    return false;
            } else if (!val1.equals(other.val1))
                return false;
            if (val2 == null) {
                if (other.val2 != null)
                    return false;
            } else if (!val2.equals(other.val2))
                return false;
            if (val3 == null) {
                if (other.val3 != null)
                    return false;
            } else if (!val3.equals(other.val3))
                return false;
            return true;
        }

        @Override
        public String toString() {
            return "MyObject [val1=" + val1 + ", val2=" + val2 + ", val3=" + val3 + "]";
        }
    }

}
