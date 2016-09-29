package io.magentys.cinnamon.webdriver.actions.basic;

import io.magentys.cinnamon.webdriver.actions.Action;
import org.openqa.selenium.WebElement;

public class ClickAction implements Action {

    public static ClickAction clickAction() {
        return new ClickAction();
    }

    @Override
    public void perform(final WebElement target) {
        target.click();
    }
}