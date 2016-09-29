package io.magentys.cinnamon.webdriver.actions.basic;

import io.magentys.cinnamon.webdriver.actions.SelectAction;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class SelectActionImpl implements SelectAction {

    private final Select select;

    public SelectActionImpl(final WebElement target) {
        this.select = new Select(target);
    }

    @Override
    public void byIndex(final int idx) {
        select.selectByIndex(idx);
    }

    @Override
    public void byValue(final String value) {
        select.selectByValue(value);
    }

    @Override
    public void byVisibleText(final String text) {
        select.selectByVisibleText(text);
    }

    @Override
    public void byVisibleTextContains(final String text) {
        select.selectByVisibleTextContains(text);
    }
}