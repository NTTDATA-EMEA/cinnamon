package io.magentys.cinnamon.webdriver.actions.basic;

import io.magentys.cinnamon.webdriver.actions.Action;
import io.magentys.cinnamon.webdriver.actions.Delayable;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class TrimCharsAction implements Action, Delayable{

    private final TypeTextAction typeTextAction;

    public TrimCharsAction(CharSequence... keysToSend) {
        typeTextAction = new TypeTextAction(keysToSend);
    }

    public static TrimCharsAction trimCharsAction(final int numChar)
    {
        Keys[] keys = new Keys[numChar * 2];
        for(int i = 0; i < numChar; i++){
            keys[i] = Keys.DELETE;
            keys[numChar+i] = Keys.BACK_SPACE;
        }
        return new TrimCharsAction(keys);
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
