package io.magentys.cinnamon.webdriver.actions.basic;

import io.magentys.cinnamon.webdriver.actions.Action;
import org.openqa.selenium.WebElement;

public class ClearAction implements Action {

    public static ClearAction clearAction() {
        return new ClearAction();
    }

    @Override
    public void perform(final WebElement target) {
        target.clear();
    }
}