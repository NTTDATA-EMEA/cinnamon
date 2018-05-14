package io.magentys.cinnamon.webdriver;

import io.appium.java_client.AppiumDriver;

public final class App {

    /**
     * Appium function - will close the running app
     */
    public static void close() {
        getAppiumDriver().closeApp();
    }

    /**
     * Appium function - will start the app provided in the webdriver conf
     */
    public static void start() {
        getAppiumDriver().launchApp();
    }

    /**
     * Appium function - will rest and restart the running app
     */
    public static void reset() {
        getAppiumDriver().resetApp();
    }

    static AppiumDriver getAppiumDriver() {
        return (AppiumDriver) Browser.getWebDriver();
    }
}