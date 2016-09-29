package io.magentys.cinnamon.webdriver;

import io.magentys.cinnamon.events.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.Augmentable;
import org.openqa.selenium.remote.Augmenter;

public class Screenshot implements Attachment, TakesScreenshot {
    private final WebDriver webDriver;
    private final String mimeType;
    private final byte[] bytes;

    public Screenshot(final WebDriver webDriver) {
        this(webDriver, "image/png");
    }

    public Screenshot(final WebDriver webDriver, String mimeType) {
        this.webDriver = webDriver;
        this.mimeType = mimeType;
        this.bytes = getScreenshotAs(OutputType.BYTES);
    }

    public byte[] getBytes() {
        return bytes;
    }

    public String getMimeType() {
        return mimeType;
    }

    public <T> T getScreenshotAs(OutputType<T> target) throws WebDriverException {
        try {
            TakesScreenshot takesScreenshot = webDriver.getClass().isAnnotationPresent(Augmentable.class) ?
                    (TakesScreenshot) new Augmenter().augment(webDriver) :
                    (TakesScreenshot) webDriver;
            return takesScreenshot.getScreenshotAs(target);
        } catch (ClassCastException e) {
            throw new WebDriverException(
                    "Taking screenshots is not supported by the concrete implementation of WebDriver [" + webDriver.getClass() + "].");
        }
    }
}
