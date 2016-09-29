package io.magentys.cinnamon.webdriver.support;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.TYPE })
public @interface FindByKey {
    /**
     * @return The locator key of the PageElement
     */
    String value();
}