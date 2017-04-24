package io.magentys.cinnamon.webdriver.actions;

import io.magentys.cinnamon.webdriver.actions.absolute.OffsetActions;
import io.magentys.cinnamon.webdriver.actions.basic.KeyStrokeIntervalActions;
import io.magentys.cinnamon.webdriver.actions.basic.SelectActionImpl;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static io.magentys.cinnamon.webdriver.actions.basic.ClearAction.clearAction;
import static io.magentys.cinnamon.webdriver.actions.basic.ClickAction.clickAction;
import static io.magentys.cinnamon.webdriver.actions.basic.DeleteContentAction.deleteContentAction;
import static io.magentys.cinnamon.webdriver.actions.basic.DoubleClickAction.doubleClickAction;
import static io.magentys.cinnamon.webdriver.actions.basic.HoverAction.hoverAction;
import static io.magentys.cinnamon.webdriver.actions.basic.PrependTextAction.prependTextAction;
import static io.magentys.cinnamon.webdriver.actions.basic.ReplaceTextAction.replaceTextAction;
import static io.magentys.cinnamon.webdriver.actions.basic.ScrollIntoViewAction.scrollIntoViewAction;
import static io.magentys.cinnamon.webdriver.actions.basic.TypeTextAction.typeTextAction;

class DefaultActions implements Actions {

    protected final WebDriver webDriver;

    public DefaultActions(final WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    @Override
    public PointActions byOffset(final WebElement target, final int x, final int y) {
        return new OffsetActions(webDriver, target, x, y);
    }

    @Override
    public KeyStrokeActions withKeyStrokeInterval(final WebElement target, final long intervalMillis) {
        return new KeyStrokeIntervalActions(this, target, intervalMillis);
    }

    @Override
    public SelectAction select(final WebElement target) {
        return new SelectActionImpl(target);
    }

    @Override
    public void click(final WebElement target) { clickAction().perform(target);  }

    @Override
    public void deleteContent(final WebElement target) {
        deleteContentAction().perform(target);
    }

    @Override
    public void clear(final WebElement target) {
        clearAction().perform(target);
    }

    @Override
    public void typeText(final WebElement target, final CharSequence... keysToSend) {
        typeTextAction(keysToSend).perform(target);
    }

    @Override
    public void replaceText(final WebElement target, final CharSequence... keysToSend) {
        replaceTextAction(keysToSend).perform(target);
    }

    @Override
    public void doubleClick(final WebElement target) {
        doubleClickAction(webDriver).perform(target);
    }

    @Override
    public void scrollIntoView(final WebElement target) {
        scrollIntoViewAction(webDriver).perform(target);
    }

    @Override
    public void hoverOver(final WebElement target) {
        hoverAction(webDriver).perform(target);
    }

    @Override
    public void prependText(final WebElement target, final CharSequence... keysToSend) {
        prependTextAction(keysToSend).perform(target);
    }
}