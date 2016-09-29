package io.magentys.cinnamon.webdriver.support.pagefactory;

import static org.mockito.Mockito.mock;
import io.magentys.cinnamon.webdriver.elements.PageElement;
import io.magentys.cinnamon.webdriver.support.FindByKey;
import io.magentys.cinnamon.webdriver.support.PageFactory;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class PageFactoryTest {
    private static WebDriver webDriver;

    class PerfPage {
        @FindBy(id = "key1")
        protected PageElement element1;
        @FindByKey("key1")
        protected PageElement element2;
        @FindByKey("key1")
        protected PageElement element3;
        @FindByKey("key1")
        protected PageElement element4;
        @FindByKey("key1")
        protected PageElement element5;
        @FindByKey("key1")
        protected PageElement element6;
        @FindByKey("key1")
        protected PageElement element7;
        @FindByKey("key1")
        protected PageElement element8;
        @FindByKey("key1")
        protected PageElement element9;
        @FindByKey("key1")
        protected PageElement element10;
        @FindByKey("key1")
        protected PageElement element11;
        @FindByKey("key1")
        protected PageElement element12;
        @FindByKey("key1")
        protected PageElement element13;
        @FindByKey("key1")
        protected PageElement element14;
        @FindByKey("key1")
        protected PageElement element15;
        @FindByKey("key1")
        protected PageElement element16;
        @FindByKey("key1")
        protected PageElement element17;
        @FindByKey("key1")
        protected PageElement element18;
        @FindByKey("key1")
        protected PageElement element19;
        @FindByKey("key1")
        protected PageElement element20;
        @FindByKey("key1")
        protected PageElement element21;
        @FindByKey("key1")
        protected PageElement element22;
        @FindByKey("key1")
        protected PageElement element23;
        @FindByKey("key1")
        protected PageElement element24;
        @FindByKey("key1")
        protected PageElement element25;

        protected final WebDriver webDriver;

        public PerfPage(final WebDriver webDriver) {
            this.webDriver = webDriver;
        }
    }

    class BasePage {
        @FindByKey("key1")
        protected PageElement element1;

        protected final WebDriver webDriver;

        public BasePage(final WebDriver webDriver) {
            this.webDriver = webDriver;
        }
    }

    class Page extends BasePage {
        @FindByKey("key2")
        private PageElement element2;

        public Page(final WebDriver webDriver) {
            super(webDriver);
        }
    }

    @BeforeClass
    public static void setUp() throws Exception {
        webDriver = mock(WebDriver.class);
    }

    @Test
    public void shouldDecorateAnnotatedFieldsOnSuperclass() {
        final Page page = new Page(webDriver);
        Assert.assertNull("element1 is not null", page.element1);
        Assert.assertNull("element2 is not null", page.element2);
        final PageFactory pageFactory = new PageFactory(webDriver);
        pageFactory.initElements(page);
        Assert.assertNotNull("element1 is null", page.element1);
        Assert.assertNotNull("element2 is null", page.element2);
    }

    public void shouldDecorateInLessThanThresholdTimings() {
        // FIXME Need to ascertain proper benchmarking figures for this test. The thresholds have been set high so that
        // the test does not fail as part of the build.
        final PerfPage perfPage1 = new PerfPage(webDriver);
        final PerfPage perfPage2 = new PerfPage(webDriver);
        final PageFactory pageFactory = new PageFactory(webDriver);

        // First time around the LocatorRegistry is populated. There is an expected overhead due to I/O.
        long startTimeMillis1 = System.currentTimeMillis();
        pageFactory.initElements(perfPage1);
        long decorateTimeMillis1 = System.currentTimeMillis() - startTimeMillis1;
        Assert.assertTrue("The decorate time was: " + decorateTimeMillis1 + "ms", decorateTimeMillis1 < 1000);

        // Second time around should be quicker as the LocatorRegistry is already populated.
        long startTimeMillis2 = System.currentTimeMillis();
        pageFactory.initElements(perfPage2);
        long decorateTimeMillis2 = System.currentTimeMillis() - startTimeMillis2;
        Assert.assertTrue("The decorate time was: " + decorateTimeMillis2 + "ms", decorateTimeMillis2 < 250);
    }
}
