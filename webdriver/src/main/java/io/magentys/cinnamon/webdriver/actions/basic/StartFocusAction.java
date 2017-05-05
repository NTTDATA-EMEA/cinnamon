package io.magentys.cinnamon.webdriver.actions.basic;

import io.magentys.cinnamon.webdriver.actions.Action;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class StartFocusAction implements Action {

    private final TypeTextAction typeTextAction;

    public StartFocusAction(CharSequence keys) {
        typeTextAction = new TypeTextAction(keys);
    }

    public static StartFocusAction startFocusAction() {
        CharSequence keys = Keys.HOME;
        return new StartFocusAction(keys);
    }

    @Override
    public void perform(WebElement target) {
        typeTextAction.perform(target);
    }

}
