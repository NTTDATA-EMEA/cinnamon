package io.magentys.cinnamon.webdriver.support.pagefactory;

import io.magentys.cinnamon.webdriver.Timeout;
import io.magentys.cinnamon.webdriver.conditions.Condition;
import io.magentys.cinnamon.webdriver.support.FindByKey;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.pagefactory.AbstractAnnotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import static io.magentys.cinnamon.webdriver.Timeouts.defaultTimeout;
import static io.magentys.cinnamon.webdriver.conditions.ElementConditions.present;

public class Annotations extends AbstractAnnotations {
    private Field field;

    /**
     * @param field expected to be an element in a Page Object
     */
    public Annotations(Field field) {
        this.field = field;
    }

    /**
     * Defines whether or not given element
     * should be returned from cache on further calls.
     *
     * @return true if @CacheLookup annotation exists on a field
     */
    public boolean isLookupCached() {
        return (field.getAnnotation(CacheLookup.class) != null);
    }

    /**
     * Defines how to transform given object (field, class, etc)
     * into {@link org.openqa.selenium.By} class used by webdriver to locate elements.
     * <p>
     * Looks for one of {@link org.openqa.selenium.support.FindBy},
     * {@link org.openqa.selenium.support.FindBys}
     * {@link org.openqa.selenium.support.FindAll} or
     * {@link io.magentys.cinnamon.webdriver.support.FindByKey} field annotations. In case
     * no annotations provided for field, uses field name as 'id' or 'name'.
     *
     * @return By object
     * @throws IllegalArgumentException when more than one annotation on a field provided
     */
    public By buildBy() {
        assertValidAnnotations();

        By ans = null;

        for (Annotation annotation : field.getDeclaredAnnotations()) {
            AbstractFindByBuilder builder = null;
            if (annotation.annotationType().isAnnotationPresent(PageFactoryFinder.class)) {
                try {
                    builder = annotation.annotationType().getAnnotation(PageFactoryFinder.class).value().newInstance();
                } catch (ReflectiveOperationException e) {
                    // Fall through.
                }
            }
            if (builder != null) {
                ans = builder.buildIt(annotation, field);
                break;
            }
        }

        if (ans == null) {
            ans = buildByFromDefault();
        }

        if (ans == null) {
            throw new IllegalArgumentException("Cannot determine how to locate element " + field);
        }

        return ans;
    }

    protected Field getField() {
        return field;
    }

    protected Condition<WebElement> buildCondition() {
        return present;
    }

    protected Timeout buildTimeout() {
        return defaultTimeout();
    }

    protected By buildByFromDefault() {
        return new ByIdOrName(field.getName());
    }

    protected void assertValidAnnotations() {
        FindBys findBys = field.getAnnotation(FindBys.class);
        FindAll findAll = field.getAnnotation(FindAll.class);
        FindBy findBy = field.getAnnotation(FindBy.class);
        FindByKey findByKey = field.getAnnotation(FindByKey.class);
        if (findBys != null && findBy != null) {
            throw new IllegalArgumentException("If you use a '@FindBys' annotation, " + "you must not also use a '@FindBy' annotation");
        }
        if (findAll != null && findBy != null) {
            throw new IllegalArgumentException("If you use a '@FindAll' annotation, " + "you must not also use a '@FindBy' annotation");
        }
        if (findAll != null && findBys != null) {
            throw new IllegalArgumentException("If you use a '@FindAll' annotation, " + "you must not also use a '@FindBys' annotation");
        }
        if (findByKey != null && findBy != null) {
            throw new IllegalArgumentException("If you use a '@FindByKey' annotation, " + "you must not also use a '@FindBy' annotation");
        }
        if (findBys != null && findByKey != null) {
            throw new IllegalArgumentException("If you use a '@FindBys' annotation, " + "you must not also use a '@FindByKey' annotation");
        }
        if (findAll != null && findByKey != null) {
            throw new IllegalArgumentException("If you use a '@FindAll' annotation, " + "you must not also use a '@FindByKey' annotation");
        }
    }
}
