package io.magentys.cinnamon.webdriver.support;

import io.magentys.cinnamon.webdriver.ByKey;
import org.openqa.selenium.By;
import org.openqa.selenium.support.AbstractFindByBuilder;
import org.openqa.selenium.support.PageFactoryFinder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.TYPE })
@PageFactoryFinder(FindByKey.FindByBuilder.class)
public @interface FindByKey {
    /**
     * @return The locator key of the PageElement
     */
    String value();

    class FindByBuilder extends AbstractFindByBuilder {
        public By buildIt(Object annotation, Field field) {
            FindByKey findByKey = (FindByKey) annotation;
            return new ByKey(findByKey.value());
        }
    }
}