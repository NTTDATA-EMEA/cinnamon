package io.magentys.cinnamon.webdriver.actions.basic;

import io.magentys.cinnamon.webdriver.actions.Action;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class EndFocusAction implements Action {

    private final TypeTextAction typeTextAction;

    public EndFocusAction(CharSequence keys) {
        typeTextAction = new TypeTextAction(keys);
    }

    public static EndFocusAction endFocusAction() {
        CharSequence keys = Keys.END;
        return new EndFocusAction(keys);
    }

    @Override
    public void perform(WebElement target) {
        typeTextAction.perform(target);
    }
}
