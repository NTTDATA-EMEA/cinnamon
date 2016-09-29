package io.magentys.cinnamon.webdriver;

import org.openqa.selenium.Alert;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.security.Credentials;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.magentys.cinnamon.webdriver.Timeouts.defaultTimeout;
import static org.openqa.selenium.support.ui.ExpectedConditions.alertIsPresent;
import static org.openqa.selenium.support.ui.ExpectedConditions.not;

public class CinnamonAlert implements Alert {

    private final Logger logger = LoggerFactory.getLogger(getClass());
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

    @Override
    public void setCredentials(Credentials credentials) {
        alert.setCredentials(credentials);
    }

    @Override
    public void authenticateUsing(Credentials credentials) {
        alert.authenticateUsing(credentials);
        waitUntilAlertIsNotPresent();
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
