package io.magentys.cinnamon.webdriver.elements;

import io.magentys.cinnamon.webdriver.conditions.Condition;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.Locatable;

public class PositionUnchangedCondition extends Condition<WebElement> {

    private final long delayIntervalMillis;

    public PositionUnchangedCondition(final long delayIntervalMillis) {
        this.delayIntervalMillis = delayIntervalMillis;
    }

    @Override
    public boolean apply(final WebElement element) {
        try {
            Point location = ((Locatable) element).getCoordinates().inViewPort();
            Thread.sleep(delayIntervalMillis);
            Point nextLocation = ((Locatable) element).getCoordinates().inViewPort();
            return location.equals(nextLocation);
        } catch (NullPointerException | WebDriverException e) {
            return false;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this)
            return true;
        if (!(o instanceof PositionUnchangedCondition))
            return false;

        PositionUnchangedCondition rhs = (PositionUnchangedCondition) o;
        return new EqualsBuilder().append(delayIntervalMillis, rhs.delayIntervalMillis).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(delayIntervalMillis).toHashCode();
    }

    @Override
    public String toString() {
        return "position unchanged";
    }

}
