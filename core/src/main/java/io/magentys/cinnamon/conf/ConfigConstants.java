package io.magentys.cinnamon.conf;

public final class ConfigConstants {

    public static final String PROJECT_DIR = System.getProperty("project.dir", ".");
    public static final String TARGET_DIR = "target";
    public static final String ENV_CONF_FILE = "env.conf";
    public static final String ENV_PROPERTY = System.getProperty("env");

    private ConfigConstants() {
    }
}
