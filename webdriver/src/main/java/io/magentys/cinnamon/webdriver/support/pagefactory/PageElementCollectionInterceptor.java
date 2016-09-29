package io.magentys.cinnamon.webdriver.support.pagefactory;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static io.magentys.cinnamon.webdriver.collections.PageElementCollection.makeCollection;

public class PageElementCollectionInterceptor implements MethodInterceptor {
    private final ElementLocator locator;

    PageElementCollectionInterceptor(final ElementLocator locator) {
        this.locator = locator;
    }

    @Override
    public Object intercept(final Object obj, final Method method, final Object[] args, final MethodProxy proxy) throws Throwable {
        if (Object.class.getDeclaredMethod("finalize").equals(method)) {
            return proxy.invokeSuper(obj, args);
        }

        final Object collection = makeCollection(locator, locator.findElements());

        try {
            return method.invoke(collection, args);
        } catch (final InvocationTargetException e) {
            // unwrap the underlying cause
            throw e.getCause();
        }
    }
}