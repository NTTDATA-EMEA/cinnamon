package io.magentys.cinnamon.webdriver;

import org.openqa.selenium.Alert;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import static io.magentys.cinnamon.webdriver.Timeouts.defaultTimeout;
import static org.openqa.selenium.support.ui.ExpectedConditions.alertIsPresent;
import static org.openqa.selenium.support.ui.ExpectedConditions.not;

public class CinnamonAlert implements Alert {

    private final WebDriver webDriver;
    private final Alert alert;

    public CinnamonAlert(final WebDriver webDriver, final Alert alert) {
        this.webDriver = webDriver;
        this.alert = alert;
    }

    @Override
    public void accept() {
        alert.accept();
        waitUntilAlertIsNotPresent();
    }

    @Override
    public void dismiss() {
        alert.dismiss();
        waitUntilAlertIsNotPresent();
    }

    @Override
    public String getText() {
        return alert.getText();
    }

    @Override
    public void sendKeys(String text) {
        alert.sendKeys(text);
    }

    private void waitUntilAlertIsNotPresent() {
        try {
            final FluentWait<WebDriver> wait = new WebDriverWait(webDriver, defaultTimeout().getSeconds());
            wait.until(not(alertIsPresent()));
        } catch (TimeoutException e) {
            throw new UnhandledAlertException(e.getMessage());
        }
    }
}
