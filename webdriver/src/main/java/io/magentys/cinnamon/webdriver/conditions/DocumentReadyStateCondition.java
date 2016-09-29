package io.magentys.cinnamon.webdriver.conditions;

import io.magentys.cinnamon.webdriver.ReadyState;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

public class DocumentReadyStateCondition extends Condition<WebDriver> {

    private final ReadyState readyState;

    public DocumentReadyStateCondition(final ReadyState readyState) {
        this.readyState = readyState;
    }

    @Override
    public boolean apply(WebDriver webDriver) {
        try {
            final JavascriptExecutor js = (JavascriptExecutor) webDriver;
            return (Boolean) js.executeScript("return (document.readyState == arguments[0]);", readyState.getLoadingStatus());
        } catch (WebDriverException e) {
            return false;
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this)
            return true;
        if (!(o instanceof DocumentReadyStateCondition))
            return false;

        DocumentReadyStateCondition rhs = (DocumentReadyStateCondition) o;
        return new EqualsBuilder().append(readyState, rhs.readyState).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(readyState).toHashCode();
    }

    @Override
    public String toString() {
        return "readystate: " + readyState.getLoadingStatus();
    }

}
