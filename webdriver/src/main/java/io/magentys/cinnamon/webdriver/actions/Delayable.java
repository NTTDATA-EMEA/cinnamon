package io.magentys.cinnamon.webdriver.actions;

/**
 * Indicates an action can be performed with a delay
 */
public interface Delayable {

    /**
     * Indicates an action is to be performed with the given delay
     *
     * @param millis the delay in milliseconds
     * @return the action
     */
    Action withDelayMillis(long millis);
}