package io.magentys.cinnamon.webdriver.events.handlers;

import com.google.common.eventbus.Subscribe;
import io.magentys.cinnamon.events.Attachment;
import io.magentys.cinnamon.events.TestStepFinishedEvent;
import io.magentys.cinnamon.webdriver.WebDriverContainer;
import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AttachScreenshot {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final WebDriverContainer webDriverContainer;

    public AttachScreenshot(final WebDriverContainer webDriverContainer) {
        this.webDriverContainer = webDriverContainer;
    }

    @Subscribe
    public void handleEvent(final TestStepFinishedEvent event) {
        try {
            if (event.isFailed()) {
                Attachment attachment = webDriverContainer.takeScreenshot();
                event.attach(attachment);
            }
        } catch (WebDriverException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
