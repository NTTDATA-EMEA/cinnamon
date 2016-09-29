package io.magentys.cinnamon.webdriver.elements;

import org.junit.Assert;
import org.junit.Test;

public class StringNormaliserTest {

    @Test
    public void shouldReplaceNewlineWithASingleSpace() {
        String text = "New\n\nlines\rare replaced";
        String normalisedText = StringNormaliser.normalise(text);
        Assert.assertEquals("New lines are replaced", normalisedText);
    }

    @Test
    public void shouldReplaceCarriageReturnWithASingleSpace() {
        String text = "Carriage\r\rreturns\rare replaced";
        String normalisedText = StringNormaliser.normalise(text);
        Assert.assertEquals("Carriage returns are replaced", normalisedText);
    }

    @Test
    public void shouldReplaceMultipleWhitespaceWithASingleSpace() {
        String text = " White\t   \t   spaces\t  are   replaced  ";
        String normalisedText = StringNormaliser.normalise(text);
        Assert.assertEquals("White spaces are replaced", normalisedText);
    }
}