package io.magentys.cinnamon.webdriver.actions.basic;

import io.magentys.cinnamon.webdriver.actions.Action;
import io.magentys.cinnamon.webdriver.actions.Delayable;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class PrependTextAction implements Action, Delayable {

    private final TypeTextAction typeTextAction;

    public PrependTextAction(final CharSequence... keysToSend) {
        typeTextAction = new TypeTextAction(keysToSend);
    }

    public static PrependTextAction prependTextAction(final CharSequence... keysToSend) {
        String keys = new String();
        for (CharSequence charSequence : keysToSend) {
            keys = keys.concat(charSequence.toString());
        }
        return new PrependTextAction(Keys.HOME + keys);
    }

    @Override
    public void perform(final WebElement target) {
        typeTextAction.perform(target);
    }

    @Override
    public Action withDelayMillis(final long delayMillis) {
        typeTextAction.withDelayMillis(delayMillis);
        return this;
    }
}
