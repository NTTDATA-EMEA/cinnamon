package io.magentys.cinnamon.webdriver.actions.basic;

import io.magentys.cinnamon.webdriver.actions.Action;
import io.magentys.cinnamon.webdriver.actions.Delayable;
import org.openqa.selenium.WebElement;

public class TrimStartCharsAction implements Action, Delayable {

    private final ClearAction clearAction;
    private final TypeTextAction typeTextAction;

    public TrimStartCharsAction(String trimmedString) {
        clearAction = new ClearAction();
        typeTextAction = new TypeTextAction(trimmedString);
    }

    public static TrimStartCharsAction trimStartCharsAction(final WebElement target, final int numChar)
    {
        String trimmedString = target.getText();
        trimmedString = trimmedString.substring(numChar, trimmedString.length());

        return new TrimStartCharsAction(trimmedString);
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
