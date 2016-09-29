package io.magentys.cinnamon.webdriver.locators;

@SuppressWarnings("serial")
public class LocatorException extends RuntimeException {
    public LocatorException(String string) {
        super(string);
    }

    public LocatorException(Throwable cause) {
        super(cause);
    }

    public LocatorException(String string, Throwable cause) {
        super(string, cause);
    }
}
