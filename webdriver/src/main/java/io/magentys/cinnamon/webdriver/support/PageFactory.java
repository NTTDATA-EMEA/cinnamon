package io.magentys.cinnamon.webdriver.support;

import io.magentys.cinnamon.webdriver.support.pagefactory.PageElementFieldDecorator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import org.openqa.selenium.WebDriver;

public class PageFactory {
    private final WebDriver webDriver;

    @Inject
    public PageFactory(final WebDriver webDriver) {
        this.webDriver = webDriver;
    }
    
    public static void initElements(final WebDriver webDriver, final Object page) {
        new PageFactory(webDriver).initElements(page);
    }

    public void initElements(final Object page) {
        final PageElementFieldDecorator decorator = new PageElementFieldDecorator(webDriver);
        recursivelyProxyFields(decorator, page, page.getClass());
    }

    private void recursivelyProxyFields(final PageElementFieldDecorator decorator, final Object page,
            final Class<?> classInHierarchyToProxy) {
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);
        List<Field> fields = listFields(classInHierarchyToProxy);
        List<Callable<Void>> callables = new ArrayList<>(fields.size());
        for (Field field : fields) {
            Callable<Void> callable = new FieldDecoratorCallable(decorator, page, field);
            callables.add(callable);
        }

        try {
            executor.invokeAll(callables);
            executor.shutdown();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private List<Field> listFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        while (clazz != null) {
            Collections.addAll(fields, clazz.getDeclaredFields());
            // If this Class represents either the Object class, an interface, a primitive type, or void, then
            // null is returned.
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

    class FieldDecoratorCallable implements Callable<Void> {

        final PageElementFieldDecorator decorator;
        final Object page;
        final Field field;

        public FieldDecoratorCallable(final PageElementFieldDecorator decorator, final Object page, final Field field) {
            this.decorator = decorator;
            this.field = field;
            this.page = page;
        }

        @Override
        public Void call() {
            final Object value = decorator.decorate(page.getClass().getClassLoader(), field);
            if (value != null) {
                try {
                    field.setAccessible(true);
                    field.set(page, value);
                } catch (final IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
            return null;
        }

    }
}
