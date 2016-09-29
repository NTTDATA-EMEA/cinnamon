package io.magentys.cinnamon.webdriver.actions.basic;

import io.magentys.cinnamon.webdriver.actions.Action;
import io.magentys.cinnamon.webdriver.actions.Delayable;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class DeleteContentAction implements Action, Delayable {

    private long delayMillis;

    public static DeleteContentAction deleteContentAction() {
        return new DeleteContentAction();
    }

    @Override
    public void perform(final WebElement target) {
        final String value = target.getAttribute("value");
        for (int character = 0; character < value.length(); character++) {
            try {
                target.sendKeys(Keys.BACK_SPACE);
                Thread.sleep(delayMillis);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Will delay each keystroke by the given amount
     *
     * @param delayMillis The delay, in milliseconds, between each keystroke
     */
    @Override
    public synchronized Action withDelayMillis(final long delayMillis) {
        this.delayMillis = delayMillis;
        return this;
    }
}