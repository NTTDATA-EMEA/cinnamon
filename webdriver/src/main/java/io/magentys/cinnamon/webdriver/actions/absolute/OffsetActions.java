package io.magentys.cinnamon.webdriver.actions.absolute;

import io.magentys.cinnamon.webdriver.actions.Action;
import io.magentys.cinnamon.webdriver.actions.PointActions;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class OffsetActions implements PointActions {

    private final Point point;
    private final WebDriver webDriver;
    private final WebElement target;

    public OffsetActions(final WebDriver webDriver, final WebElement target, final int x, final int y) {
        this.webDriver = webDriver;
        this.target = target;
        this.point = new Point(x, y);
    }

    @Override
    public void click() {
        offsetAction(new AbsoluteClickAction(webDriver)).perform(target);
    }

    private Action offsetAction(final AbsolutePointAction action) {
        return new OffsetAction(point.x, point.y, action);
    }
}