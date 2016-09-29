package io.magentys.cinnamon.webdriver.actions.basic;

import io.magentys.cinnamon.webdriver.actions.Action;
import io.magentys.cinnamon.webdriver.actions.Delayable;
import org.openqa.selenium.WebElement;

public class TypeTextAction implements Action, Delayable {

    private final CharSequence[] keysToSend;
    private long delayMillis;

    public TypeTextAction(final CharSequence... keysToSend) {
        this.keysToSend = keysToSend;
    }

    public static TypeTextAction typeTextAction(final CharSequence... keysToSend) {
        return new TypeTextAction(keysToSend);
    }

    @Override
    public void perform(final WebElement target) {
        if (delayMillis == 0) {
            sendKeysWithoutDelay(target);
        } else {
            sendKeysWithDelay(target);
        }
    }

    private void sendKeysWithoutDelay(final WebElement target) {
        target.sendKeys(keysToSend);
    }

    private void sendKeysWithDelay(final WebElement target) {
        for (final CharSequence charSequence : keysToSend) {
            for (int i = 0; i < charSequence.length(); i++) {
                try {
                    target.sendKeys(String.valueOf(charSequence.charAt(i)));
                    Thread.sleep(delayMillis);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
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