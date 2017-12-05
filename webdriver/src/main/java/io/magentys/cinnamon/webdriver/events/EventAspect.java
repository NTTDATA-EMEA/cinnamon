package io.magentys.cinnamon.webdriver.events;

import io.magentys.cinnamon.eventbus.EventBusContainer;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

@Aspect
public class EventAspect {

    /**
     * Pointcut for <code>org.openqa.selenium.WebElement.click</code> method.
     */
    @Pointcut("execution(* org.openqa.selenium.WebElement.click(..))")
    public void click() {
        // pointcut body must be empty
    }

    @Before("click()")
    public void beforeClickOn(JoinPoint joinPoint) {
        EventBusContainer.getEventBus().post(new BeforeClickEvent());
    }
}