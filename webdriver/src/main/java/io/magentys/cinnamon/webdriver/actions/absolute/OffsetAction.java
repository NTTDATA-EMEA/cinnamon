package io.magentys.cinnamon.webdriver.actions.absolute;

import io.magentys.cinnamon.webdriver.actions.Action;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

public class OffsetAction implements Action {

    private final Point offset;
    private final AbsolutePointAction delegate;

    public OffsetAction(final int x, final int y, final AbsolutePointAction delegate) {
        this.offset = new Point(x, y);
        this.delegate = delegate;
    }

    @Override
    public void perform(final WebElement target) {
        delegate.perform(target.getLocation().moveBy(offset.x, offset.y));
    }
}