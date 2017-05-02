package io.magentys.cinnamon.webdriver.actions.basic;

import io.magentys.cinnamon.webdriver.actions.Action;
import io.magentys.cinnamon.webdriver.actions.Delayable;
import org.openqa.selenium.WebElement;

public class TrimEndCharsAction implements Action, Delayable {

    private final ClearAction clearAction;
    private final TypeTextAction typeTextAction;

    public TrimEndCharsAction(final WebElement target, final int numChar) {
        clearAction = new ClearAction();
        typeTextAction = new TypeTextAction();
    }

    public static TrimEndCharsAction trimEndCharsAction(final WebElement target, final int numChar)
    {
        return new TrimEndCharsAction(target, numChar);
    }

    @Override
    public void perform(final WebElement target) {

        clearAction.perform(target);
        typeTextAction.perform(target);
    }

    @Override
    public Action withDelayMillis(final long millis) {
        typeTextAction.withDelayMillis(millis);
        return null;
    }
}
