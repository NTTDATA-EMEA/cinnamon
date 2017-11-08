package io.magentys.cinnamon.webdriver.events;

import io.magentys.cinnamon.eventbus.EventBusContainer;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.openqa.selenium.WebDriver;

@Aspect
public class EventAspect {

    @Pointcut("execution(org.openqa.selenium.WebDriver+.new(..)")
    public void constructor() {
        // pointcut body must be empty
    }

    /**
     * Pointcut for <code>org.openqa.selenium.WebElement.click</code> method.
     */
    @Pointcut("execution(* org.openqa.selenium.WebElement.click(..))")
    public void click() {
        // pointcut body must be empty
    }

    @AfterReturning("constructor()")
    public void afterReturningFromConstructor(JoinPoint joinPoint) {
        EventBusContainer.getEventBus().post(new AfterConstructorEvent((WebDriver) joinPoint.getThis()));
    }

    @Before("click()")
    public void beforeClickOn(JoinPoint joinPoint) {
        EventBusContainer.getEventBus().post(new BeforeClickEvent());
    }
}