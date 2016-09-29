package io.magentys.cinnamon.webdriver.actions.chrome;

import io.magentys.cinnamon.webdriver.actions.Action;
import io.magentys.cinnamon.webdriver.actions.synthetic.SyntheticEvent;
import io.magentys.cinnamon.webdriver.elements.PositionUnchangedCondition;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.magentys.cinnamon.webdriver.Timeouts.defaultTimeout;

public class ScrollBeforeClickAction implements Action {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final SyntheticEvent syntheticEvent;

    public ScrollBeforeClickAction(final WebDriver webDriver) {
        this.syntheticEvent = new SyntheticEvent(webDriver);
    }

    public static ScrollBeforeClickAction scrollBeforeClickAction(final WebDriver webDriver) {
        return new ScrollBeforeClickAction(webDriver);
    }

    @Override
    public void perform(final WebElement element) {
        try {
            element.click();
        } catch (final WebDriverException e) {
            logger.warn("Unable to click using element.click(). Performing a click workaround for Chrome.");
            syntheticEvent.scrollIntoView(element, true);
            waitUntilPositionUnchanged(element);
            element.click();
        }
    }

    private void waitUntilPositionUnchanged(final WebElement element) {
        final long startTimeInMillis = System.currentTimeMillis();
        do {
            final PositionUnchangedCondition positionUnchangedCondition = new PositionUnchangedCondition(100);
            if (positionUnchangedCondition.apply(element)) {
                return;
            }
        } while (System.currentTimeMillis() - startTimeInMillis < defaultTimeout().getMillis());
    }
}
