package io.magentys.cinnamon.conf;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EnvTest {

    private Env env;

    @Before
    public void setUp() {
        System.setProperty("env", "example");
        System.setProperty("example.host", "foobar");
        env = Env.INSTANCE;
    }

    @Test
    public void testSystemPropertySubstitutionWorks() {
        assertEquals("foobar", env.config.getString("host"));
    }

    @Test
    public void testDefaultValueOfConfigItemWhenNoSystemPropertyOverrideIsSet() {
        assertEquals(8080, env.config.getInt("port"));
    }
}
