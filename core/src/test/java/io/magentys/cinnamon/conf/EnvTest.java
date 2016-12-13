package io.magentys.cinnamon.conf;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EnvTest {

    @Before
    public void setUp() {
        System.setProperty("env", "example");
    }

    @Test
    public void testSystemPropertySubstitutionWorks() {
        System.setProperty("example.host", "foobar");

        Env env = Env.INSTANCE;
        assertEquals("foobar", env.config.getString("host"));

        System.clearProperty("env");
        System.clearProperty("example.host");
    }

    @Test
    public void testDefaultValueOfConfigItemWhenNoSystemPropertyOverrideIsSet() {
        Env env = Env.INSTANCE;
        assertEquals(8080, env.config.getInt("port"));
    }
}
