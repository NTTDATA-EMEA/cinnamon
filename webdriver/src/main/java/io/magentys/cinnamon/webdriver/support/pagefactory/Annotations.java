package io.magentys.cinnamon.webdriver.support.pagefactory;

import io.magentys.cinnamon.webdriver.ByKey;
import io.magentys.cinnamon.webdriver.Timeout;
import io.magentys.cinnamon.webdriver.conditions.Condition;
import io.magentys.cinnamon.webdriver.support.FindByKey;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ByIdOrName;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.pagefactory.AbstractAnnotations;

import java.lang.reflect.Field;

import static io.magentys.cinnamon.webdriver.Timeouts.defaultTimeout;
import static io.magentys.cinnamon.webdriver.conditions.ElementConditions.present;

public class Annotations extends AbstractAnnotations {
    
    private final Field field;

    public Annotations(final Field field) {
        this.field = field;
    }

    public boolean isLookupCached() {
        return (field.getAnnotation(CacheLookup.class) != null);
    }

    public By buildBy() {
        By ans = null;

        FindAll findAll = field.getAnnotation(FindAll.class);
        if (findAll != null) {
            ans = buildBysFromFindByOneOf(findAll);
        }

        FindBy findBy = field.getAnnotation(FindBy.class);
        if (ans == null && findBy != null) {
            ans = buildByFromFindBy(findBy);
        }

        FindByKey findByKey = field.getAnnotation(FindByKey.class);
        if (ans == null && findByKey != null) {
            ans = buildByFromFindByKey(findByKey);
        }

        if (ans == null) {
            ans = buildByFromDefault();
        }

        if (ans == null) {
            throw new IllegalArgumentException("Cannot determine how to locate element " + field);
        }

        return ans;
    }

    // TODO Read condition annotations applied to field.
    protected Condition<WebElement> buildCondition() {
        return present;
    }

    // TODO Add timeout value to find annotation.
    protected Timeout buildTimeout() {
        return defaultTimeout();
    }

    protected By buildByFromDefault() {
        return new ByIdOrName(field.getName());
    }

    protected By buildByFromFindByKey(FindByKey findByKey) {
        return ByKey.locatorKey(findByKey.value());
    }

    protected Field getField() {
        return field;
    }
}
