package io.magentys.cinnamon.webdriver.actions;

import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.magentys.cinnamon.webdriver.actions.basic.HoverAction.hoverAction;
import static io.magentys.cinnamon.webdriver.actions.jquery.JQueryHoverAction.jQueryHoverAction;
import static io.magentys.cinnamon.webdriver.actions.synthetic.SyntheticHoverAction.syntheticHoverAction;

class InternetExplorerActions extends DefaultActions {

    private final JavascriptExecutor js;
    private final HasCapabilities hasCapabilities;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public InternetExplorerActions(final WebDriver webDriver) {
        super(webDriver);
        js = (JavascriptExecutor) webDriver;
        this.hasCapabilities = (HasCapabilities) webDriver;
    }

    @Override
    public void hoverOver(final WebElement target) {
        final Boolean jQuery = (Boolean) js.executeScript("return (typeof jQuery !== 'undefined');");
        // This only works if both nativeEvents and requireWindowFocus capabilities are set to true. These capabilities
        // are required for sites that use the :hover CSS pseudo-class.
        if (hasCapabilities.getCapabilities().is(InternetExplorerDriver.NATIVE_EVENTS) && hasCapabilities.getCapabilities()
                .is(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS)) {
            logger.debug("Performing a hover for Internet Explorer");
            hoverAction(webDriver).perform(target);
        } else if (jQuery) {
            jQueryHoverAction(js).perform(target);
        } else {
            syntheticHoverAction(webDriver).perform(target);
        }
    }
}