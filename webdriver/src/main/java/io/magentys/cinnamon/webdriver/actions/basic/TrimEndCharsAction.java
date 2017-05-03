package io.magentys.cinnamon.webdriver.actions.basic;

import io.magentys.cinnamon.webdriver.actions.Action;
import io.magentys.cinnamon.webdriver.actions.Delayable;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class TrimEndCharsAction implements Action, Delayable {

    private final TypeTextAction typeTextAction;

    public TrimEndCharsAction(CharSequence... keysToSend) {
        typeTextAction = new TypeTextAction(keysToSend);
    }

    public static TrimEndCharsAction trimEndCharsAction(final int numChar)
    {
        Keys[] keys = new Keys[numChar+1];
        keys[0] = Keys.END;
        for(int i = 0; i < numChar; i++){
            keys[i+1] = Keys.BACK_SPACE;
        }
        return new TrimEndCharsAction(keys);
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
