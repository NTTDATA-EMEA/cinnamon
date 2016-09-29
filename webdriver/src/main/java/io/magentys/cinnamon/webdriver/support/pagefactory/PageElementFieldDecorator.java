package io.magentys.cinnamon.webdriver.support.pagefactory;

import io.magentys.cinnamon.webdriver.elements.PageElement;
import io.magentys.cinnamon.webdriver.collections.PageElementCollection;
import io.magentys.cinnamon.webdriver.elements.TableElement;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.FieldDecorator;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class PageElementFieldDecorator implements FieldDecorator {

    private final WebDriver webDriver;
    private final SearchContext context;

    public PageElementFieldDecorator(final WebDriver webDriver) {
        this(webDriver, webDriver);
    }

    public PageElementFieldDecorator(final WebDriver webDriver, final SearchContext context) {
        this.webDriver = webDriver;
        this.context = context;
    }

    @Override
    public Object decorate(final ClassLoader ignored, final Field field) {
        if (!isProxyable(field)) {
            return null;
        }

        final ElementLocator locator = new PageElementLocator(webDriver, context, field);

        if (PageElement.class.isAssignableFrom(field.getType())) {
            return proxyForLocator(field, locator);
        } else if (PageElementCollection.class.isAssignableFrom(field.getType())) {
            return proxyForCollectionLocator(locator);
        } else {
            return null;
        }
    }

    private boolean isProxyable(final Field field) {
        final List<Class<?>> proxyable = Arrays.asList(PageElement.class, PageElementCollection.class, TableElement.class);
        return proxyable.contains(field.getType());
    }

    private Object proxyForLocator(final Field field, final ElementLocator locator) {
        final PageElementInterceptor methodInterceptor = new PageElementInterceptor(locator);
        final Class<?>[] argTypes = new Class<?>[] {};
        final Object[] args = new Object[] {};
        return getEnhancedProxy(field.getType(), argTypes, args, methodInterceptor);

    }

    private Object proxyForCollectionLocator(final ElementLocator locator) {
        final PageElementCollectionInterceptor methodInterceptor = new PageElementCollectionInterceptor(locator);
        final Class<?>[] argTypes = new Class<?>[] { ElementLocator.class, List.class };
        final Object[] args = new Object[] { null, null };
        return getEnhancedProxy(PageElementCollection.class, argTypes, args, methodInterceptor);
    }

    @SuppressWarnings("unchecked")
    public <T> T getEnhancedProxy(final Class<T> requiredClazz, final Class<?>[] argTypes, final Object[] args, final MethodInterceptor interceptor) {
        final Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(requiredClazz);
        enhancer.setCallback(interceptor);
        return (T) enhancer.create(argTypes, args);
    }
}
