package io.magentys.cinnamon.webdriver.elements;

import io.magentys.cinnamon.webdriver.conditions.Condition;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import static io.magentys.cinnamon.webdriver.WebDriverUtils.unwrapDriver;

public class ObscuredCondition extends Condition<WebElement> {

    @Override
    public boolean apply(final WebElement element) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) unwrapDriver(element);
            return (Boolean) js.executeScript(
                    "var element = document.elementFromPoint(arguments[1], arguments[2]);" + "return element.parentNode !== arguments[0].parentNode",
                    element, element.getLocation().x + element.getSize().width / 2, element.getLocation().y + element.getSize().height / 2);
//                    element, element.getLocation().x, element.getLocation().y);
        } catch (NullPointerException | WebDriverException e) {
            return false;
        }
    }

    @Override
    public boolean equals(final Object o) {
        return o == this || o instanceof ObscuredCondition;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().toHashCode();
    }

    @Override
    public String toString() {
        return "obscured";
    }

}
