package io.magentys.cinnamon.webdriver.locators;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Properties;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.openqa.selenium.By.ByCssSelector;
import org.openqa.selenium.By.ById;
import org.openqa.selenium.By.ByXPath;

public class LocatorRegistryTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void shouldLoadValidFlattenedLocatorFile() throws URISyntaxException {
        File locatorFile = new File(LocatorRegistryTest.class.getResource("/conf/flattened/locator.yml").toURI());
        new LocatorRegistry(new Properties(), locatorFile);
    }

    @Test
    public void shouldLoadFlattenedLocatorFileThatHasEmptyProfile() throws URISyntaxException {
        File locatorFile = new File(LocatorRegistryTest.class.getResource("/conf/flattened/empty_profile.yml").toURI());
        new LocatorRegistry(new Properties(), locatorFile);
    }

    @Test
    public void shouldLoadValidHierarchicalLocatorFile() throws URISyntaxException {
        File locatorFile = new File(LocatorRegistryTest.class.getResource("/conf/hierarchical/locator.yml").toURI());
        new LocatorRegistry(new Properties(), locatorFile);
    }

    @Test
    public void shouldRegisterWhenFlattenedLocatorFileIsLoaded() throws URISyntaxException {
        File locatorFile = new File(LocatorRegistryTest.class.getResource("/conf/flattened/locator.yml").toURI());
        LocatorRegistry locatorRegistry = new LocatorRegistry(new Properties(), locatorFile);

        assertTrue(locatorRegistry.getLocator("my-button") instanceof ByCssSelector);
        assertTrue(locatorRegistry.getLocator("my-input") instanceof ByXPath);
        assertTrue(locatorRegistry.getLocator("my-frame") instanceof ById);

        assertEquals(new ByCssSelector("#mypage .mybutton"), locatorRegistry.getLocator("my-button"));
        assertEquals(new ByXPath("//input[@class='myinput']"), locatorRegistry.getLocator("my-input"));
        assertEquals(new ById("myframe"), locatorRegistry.getLocator("my-frame"));
    }

    @Test
    public void shouldRegisterDefaultProfileWhenLocatorPropertyIsNotSet() throws URISyntaxException {
        File locatorFile = new File(LocatorRegistryTest.class.getResource("/conf/hierarchical/locator.yml").toURI());
        LocatorRegistry locatorRegistry = new LocatorRegistry(new Properties(), locatorFile);

        assertTrue(locatorRegistry.getLocator("my-button") instanceof ByCssSelector);
        assertTrue(locatorRegistry.getLocator("my-input") instanceof ByXPath);
        assertTrue(locatorRegistry.getLocator("my-frame") instanceof ById);

        assertEquals(new ByCssSelector("#mypage .mybutton"), locatorRegistry.getLocator("my-button"));
        assertEquals(new ByXPath("//input[@class='myinput']"), locatorRegistry.getLocator("my-input"));
        assertEquals(new ById("myframe"), locatorRegistry.getLocator("my-frame"));
    }

    @Test
    public void shouldRegisterUserSpecifiedProfileWhenLocatorPropertyIsSet() throws URISyntaxException {
        Properties properties = new Properties();
        properties.setProperty("locator", "override");
        File locatorFile = new File(LocatorRegistryTest.class.getResource("/conf/hierarchical/locator.yml").toURI());
        LocatorRegistry locatorRegistry = new LocatorRegistry(properties, locatorFile);

        assertTrue(locatorRegistry.getLocator("my-button") instanceof ByCssSelector);
        assertTrue(locatorRegistry.getLocator("my-input") instanceof ByXPath);
        assertTrue(locatorRegistry.getLocator("my-frame") instanceof ById);

        assertEquals(new ByCssSelector("#overridepage .overridebutton"), locatorRegistry.getLocator("my-button"));
        assertEquals(new ByXPath("//input[@class='myinput']"), locatorRegistry.getLocator("my-input"));
        assertEquals(new ById("myframe"), locatorRegistry.getLocator("my-frame"));
    }

    @Test
    public void failsWhenFlattenedLocatorFileContainsInvalidType() throws URISyntaxException {
        exception.expect(LocatorException.class);
        File invalidContentFlattenedLocatorFile = new File(LocatorRegistryTest.class.getResource("/conf/flattened/invalid_type.yml").toURI());
        new LocatorRegistry(new Properties(), invalidContentFlattenedLocatorFile);
    }

    @Test
    public void failsWhenFlattenedLocatorFileContainsInvalidTabCharacter() throws URISyntaxException {
        exception.expect(LocatorException.class);
        exception.expectMessage(containsString("Do not use \\t(TAB) for indentation"));
        File locatorFile = new File(LocatorRegistryTest.class.getResource("/conf/flattened/invalid_tab_char.yml").toURI());
        new LocatorRegistry(new Properties(), locatorFile);
    }

    @Test
    public void failsWhenFlattenedLocatorFileHasMissingClosingQuoteCharacter() throws URISyntaxException {
        exception.expect(LocatorException.class);
        exception.expectMessage(containsString("expected <block end>"));
        File locatorFile = new File(LocatorRegistryTest.class.getResource("/conf/flattened/missing_closing_quote_char.yml").toURI());
        new LocatorRegistry(new Properties(), locatorFile);
    }
}
