package io.magentys.cinnamon.webdriver.actions.basic;

import io.magentys.cinnamon.webdriver.actions.Actions;
import io.magentys.cinnamon.webdriver.actions.KeyStrokeActions;
import org.openqa.selenium.WebElement;

import static io.magentys.cinnamon.webdriver.actions.basic.DeleteContentAction.deleteContentAction;
import static io.magentys.cinnamon.webdriver.actions.basic.ReplaceTextAction.replaceTextAction;
import static io.magentys.cinnamon.webdriver.actions.basic.TypeTextAction.typeTextAction;

public class KeyStrokeIntervalActions implements KeyStrokeActions {

    private final Actions actions;
    private final WebElement target;
    private final long intervalMillis;

    public KeyStrokeIntervalActions(final Actions actions, final WebElement target, final long intervalMillis) {
        this.actions = actions;
        this.target = target;
        this.intervalMillis = intervalMillis;
    }

    @Override
    public Actions typeText(CharSequence... keysToSend) {
        typeTextAction(keysToSend).withDelayMillis(intervalMillis).perform(target);
        return actions;
    }

    @Override
    public Actions replaceText(CharSequence... keysToSend) {
        replaceTextAction(keysToSend).withDelayMillis(intervalMillis).perform(target);
        return actions;
    }

    @Override
    public Actions deleteContent() {
        deleteContentAction().withDelayMillis(intervalMillis).perform(target);
        return actions;
    }
}