package io.magentys.cinnamon.webdriver.actions.basic;

import io.magentys.cinnamon.webdriver.actions.Action;
import io.magentys.cinnamon.webdriver.actions.Delayable;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class TrimStartCharsAction implements Action, Delayable {

    private final TypeTextAction typeTextAction;

    public TrimStartCharsAction(CharSequence... keysToSend) {
        typeTextAction = new TypeTextAction(keysToSend);
    }

    public static TrimStartCharsAction trimStartCharsAction(final int numChar)
    {
        Keys[] keys = new Keys[numChar];
        keys[0] = Keys.HOME;
        for(int i = 1; i < numChar; i++){
            keys[i] = Keys.DELETE;
        }
        return new TrimStartCharsAction(keys);
    }

    @Override
    public void perform(final WebElement target) {
        typeTextAction.perform(target);
    }

    @Override
    public Action withDelayMillis(final long millis) {
        typeTextAction.withDelayMillis(millis);
        return null;
    }
}
