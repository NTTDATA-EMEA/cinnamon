package io.magentys.cinnamon.webdriver.collections;

import io.magentys.cinnamon.webdriver.conditions.Condition;
import io.magentys.cinnamon.webdriver.elements.WebElementConverter;
import org.apache.commons.collections.CollectionUtils;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static io.magentys.cinnamon.webdriver.elements.WebElementConverter.elementConverter;

public class TextsContainCondition extends Condition<List<WebElement>> {

    private final WebElementConverter webElementConverter;
    private final boolean ignoreCase;
    private final String[] texts;

    public TextsContainCondition(final String... texts) {
        this(elementConverter(), false, texts);
    }

    public TextsContainCondition(final boolean ignoreCase, final String... texts) {
        this(elementConverter(), ignoreCase, texts);
    }

    TextsContainCondition(final WebElementConverter webElementConverter, final boolean ignoreCase, final String... texts) {
        this.webElementConverter = webElementConverter;
        this.ignoreCase = ignoreCase;
        this.texts = texts;
    }

    @Override
    public boolean apply(List<WebElement> elements) {
        List<String> elementsTexts = webElementConverter.getTextsFrom(elements);
        if (texts.length > elementsTexts.size())
            return false;

        List<String> actualTexts = ignoreCase ? elementsTexts.stream().map(String::toLowerCase).collect(Collectors.toList()) : elementsTexts;
        List<String> expectedTexts = ignoreCase ?
                Arrays.stream(texts).map(String::toLowerCase).collect(Collectors.toList()) :
                Arrays.stream(texts).collect(Collectors.toList());
        return CollectionUtils.intersection(expectedTexts, actualTexts).size() == expectedTexts.size();
    }

    @Override
    public String toString() {
        return "texts contain: " + Arrays.asList(texts);
    }
}