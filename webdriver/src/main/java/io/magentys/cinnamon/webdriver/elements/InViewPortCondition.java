package io.magentys.cinnamon.webdriver.elements;

import io.magentys.cinnamon.webdriver.conditions.Condition;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsDriver;

public class InViewPortCondition extends Condition<WebElement> {

    @Override
    public boolean apply(final WebElement element) {
        try {
            WebDriver driver = ((WrapsDriver) element).getWrappedDriver();
            JavascriptExecutor js = (JavascriptExecutor) driver;
            return (Boolean) js
                    .executeScript(
                            "var rect = arguments[0].getBoundingClientRect();"
                                    + "return (rect.top >= 0 && rect.left >= 0 && rect.bottom <= (window.innerHeight || document.documentElement.clientHeight) && rect.right <= (window.innerWidth || document.documentElement.clientWidth));",
                            element);
        } catch (NullPointerException | WebDriverException e) {
            return false;
        }
    }

    @Override
    public boolean equals(final Object o) {
        return o == this || o instanceof InViewPortCondition;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().toHashCode();
    }

    @Override
    public String toString() {
        return "in view port";
    }

}
