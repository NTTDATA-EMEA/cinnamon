package io.magentys.cinnamon.webdriver.support.pagefactory;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static io.magentys.cinnamon.webdriver.elements.PageElement.makeElement;

class PageElementInterceptor implements MethodInterceptor {
    private final ElementLocator locator;

    PageElementInterceptor(final ElementLocator locator) {
        this.locator = locator;
    }

    @Override
    public Object intercept(final Object obj, final Method method, final Object[] args, final MethodProxy proxy) throws Throwable {
        if (Object.class.getDeclaredMethod("finalize").equals(method)) {
            return proxy.invokeSuper(obj, args);
        }

        final Object element = makeElement(locator, locator.findElement());

        try {
            return method.invoke(element, args);
        } catch (final InvocationTargetException e) {
            // unwrap the underlying exception to make the proxying transparent
            throw e.getCause();
        }
    }
}