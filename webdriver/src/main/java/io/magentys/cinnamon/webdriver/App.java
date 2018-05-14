package io.magentys.cinnamon.webdriver;

import io.appium.java_client.AppiumDriver;

public final class App {

    /**
     * Appium function - will close the running app
     */
    public static void closeApp() {
        AppiumDriver driver = getAppiumDriver();
        driver.closeApp();
    }

    /**
     * Appium function - will start the app provided in the webdriver conf
     */
    public static void startApp() {
        AppiumDriver driver = getAppiumDriver();
        driver.launchApp();
    }

    /**
     * Appium function - will rest and restart the running app
     */
    public static void resetApp() {
        AppiumDriver driver = getAppiumDriver();
        driver.resetApp();
    }

    static AppiumDriver getAppiumDriver() {
        return (AppiumDriver) Browser.getWebDriver();
    }
}