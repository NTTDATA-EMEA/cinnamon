package io.magentys.cinnamon.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.TypeLiteral;
import com.google.inject.matcher.Matchers;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;
import io.magentys.cinnamon.webdriver.support.FindByKey;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.lang.reflect.Field;

import static io.magentys.cinnamon.webdriver.WebDriverContainer.webDriver;

public final class WebDriverModule extends AbstractModule {

    @Override
    public void configure() {
        final FindByKeyInjectionListener findByKeyInjectionListener = new FindByKeyInjectionListener();
        bind(FindByKeyInjectionListener.class).toInstance(findByKeyInjectionListener);

        bindListener(Matchers.any(), new TypeListener() {
            @Override
            public <I> void hear(TypeLiteral<I> type, TypeEncounter<I> encounter) {
                if (hasFindByKeyAnnotation(type.getRawType())) {
                    encounter.register(findByKeyInjectionListener);
                }
            }

            private boolean hasFindByKeyAnnotation(Class<?> clazz) {
                while (clazz != null) {
                    for (Field field : clazz.getDeclaredFields()) {
                        if (field.isAnnotationPresent(FindByKey.class) || field.isAnnotationPresent(FindBy.class) || field
                                .isAnnotationPresent(FindAll.class)) {
                            return true;
                        }
                    }
                    // If this Class represents either the Object class, an interface, a primitive type, or void, then
                    // null is returned.
                    clazz = clazz.getSuperclass();
                }
                return false;
            }
        });
    }

    @Provides
    WebDriver provideWebDriver() {
        return webDriver();
    }

}
