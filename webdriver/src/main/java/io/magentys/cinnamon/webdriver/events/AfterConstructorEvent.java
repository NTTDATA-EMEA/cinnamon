package io.magentys.cinnamon.webdriver.events;

import io.magentys.cinnamon.events.ConstructorEvent;
import org.openqa.selenium.WebDriver;

public class AfterConstructorEvent implements ConstructorEvent {

    private final WebDriver webDriver;

    public AfterConstructorEvent(final WebDriver webDriver) {
                this.webDriver = webDriver;
            }

    @Override
    public WebDriver getThis() {
        return webDriver;
    }
}
