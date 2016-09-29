package io.magentys.cinnamon.webdriver.actions.basic;

import io.magentys.cinnamon.webdriver.actions.Action;
import io.magentys.cinnamon.webdriver.actions.Delayable;
import org.openqa.selenium.WebElement;

public class ReplaceTextAction implements Action, Delayable {

    private final ClearAction clearAction;
    private final TypeTextAction typeTextAction;

    public ReplaceTextAction(final CharSequence... keysToSend) {
        clearAction = new ClearAction();
        typeTextAction = new TypeTextAction(keysToSend);
    }

    public static ReplaceTextAction replaceTextAction(final CharSequence... keysToSend) {
        return new ReplaceTextAction(keysToSend);
    }

    @Override
    public void perform(final WebElement target) {
        clearAction.perform(target);
        typeTextAction.perform(target);
    }

    @Override
    public Action withDelayMillis(final long delayMillis) {
        typeTextAction.withDelayMillis(delayMillis);
        return this;
    }
}