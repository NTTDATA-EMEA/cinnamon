package io.magentys.cinnamon.webdriver.collections;

import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Indicates that there are underlying elements that can be used
 */
public interface WrapsElements {

    List<WebElement> getWrappedElements();
}
