package io.magentys.cinnamon.conf;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class EnvTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Env env;

    @Before
    public void setUp() {
        System.setProperty("env", "example");
        System.setProperty("example.host", "foobar");
        env = Env.INSTANCE;
    }

    @Test
    public void shouldGivePrecedenceToSystemProperty() {
        assertEquals("foobar", env.config.getString("host"));
    }

    @Test
    public void shouldUseConfigValueWhenNoSystemPropertyOverrideIsSet() {
        assertEquals(8080, env.config.getInt("port"));
    }

    @Test
    public void shouldThrowExceptionWhenNoEnvFileIsFound() {
        thrown.expect(Error.class);
        env.searchConfigFileInClasspath("nonexistent.conf");
    }
}
