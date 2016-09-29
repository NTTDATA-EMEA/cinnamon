package io.magentys.cinnamon.webdriver.elements;

import io.magentys.cinnamon.webdriver.actions.synthetic.DomEvent;

import java.util.Map;

import io.magentys.cinnamon.webdriver.conditions.Condition;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventListenerRegisteredCondition extends Condition<WebElement> {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final DomEvent event;

    public EventListenerRegisteredCondition(final DomEvent event) {
        this.event = event;
    }

    @Override
    public boolean apply(final WebElement element) {
        try {
            WebDriver driver = ((WrapsDriver) element).getWrappedDriver();
            JavascriptExecutor js = (JavascriptExecutor) driver;
            Boolean jQuery = (Boolean) js.executeScript("return (typeof jQuery !== 'undefined');");
            if (!jQuery) {
                logger.warn("Unable to apply the EventListenerRegisteredCondition as jQuery is undefined.");
                return true;
            }
            @SuppressWarnings("unchecked")
            Map<String, Object> registeredListeners = (Map<String, Object>) js.executeScript("return $._data($(arguments[0])[0], 'events')", element);
            if (registeredListeners == null)
                return false;
            for (Map.Entry<String, Object> listener : registeredListeners.entrySet()) {
                logger.debug("DomEvent listener [" + listener.getKey() + "] registered.");
                if (listener.getKey().equals(event.getName())) {
                    return true;
                }
            }
            return false;
        } catch (NullPointerException | WebDriverException e) {
            return false;
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this)
            return true;
        if (!(o instanceof EventListenerRegisteredCondition))
            return false;

        EventListenerRegisteredCondition rhs = (EventListenerRegisteredCondition) o;
        return new EqualsBuilder().append(event, rhs.event).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(event).toHashCode();
    }

    @Override
    public String toString() {
        return "event listener registered: " + event.getName();
    }

}
