package io.magentys.cinnamon.conf;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EnvTest {

    @Test
    public void testSystemPropertySubstitutionWorks() {
        System.setProperty("env", "example");
        System.setProperty("example.host", "foobar");

        Env env = Env.INSTANCE;
        assertEquals("foobar", env.config.getString("host"));

        System.clearProperty("env");
        System.clearProperty("example.host");
    }
}
