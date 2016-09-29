package io.magentys.cinnamon.webdriver.conditions;

import io.magentys.cinnamon.webdriver.conditions.Condition;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

public class AjaxFinishedCondition extends Condition<WebDriver> {

    @Override
    public boolean apply(WebDriver webDriver) {
        try {
            final JavascriptExecutor js = (JavascriptExecutor) webDriver;
            return (Boolean) js.executeScript("return (typeof jQuery !== 'undefined' ? jQuery.active == 0 : true);");
        } catch (WebDriverException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return "ajax finished";
    }

}
