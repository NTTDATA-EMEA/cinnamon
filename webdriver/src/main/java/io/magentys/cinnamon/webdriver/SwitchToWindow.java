package io.magentys.cinnamon.webdriver;

import org.openqa.selenium.WebDriver;

public interface SwitchToWindow {

    WebDriver first();

    WebDriver last();

    WebDriver byTitle(final String title);

    WebDriver byPartialTitle(final String partialTitle);
}
