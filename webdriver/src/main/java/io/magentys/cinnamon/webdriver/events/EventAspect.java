package io.magentys.cinnamon.webdriver.events;

import io.magentys.cinnamon.eventbus.EventBusContainer;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.openqa.selenium.WebDriver;

@Aspect
public class EventAspect {

//    @Pointcut("call(* *.getDriver(..))")
//    @Pointcut("execution(org.openqa.selenium..*.get(..))")
//    @Pointcut("execution(org.openqa.selenium.WebDriver+.new(..)")
    @Pointcut("execution(* org.seleniumhq.selenium..*.*(..))")
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

        System.out.println("___EVENTASPECT AFTER CONSTRUCTOR");

        System.out.println("___GETCLASS :"+joinPoint.getThis().getClass());

        EventBusContainer.getEventBus().post(new AfterConstructorEvent((WebDriver) joinPoint.getThis()));
    }

    @Before("click()")
    public void beforeClickOn(JoinPoint joinPoint) {
        EventBusContainer.getEventBus().post(new BeforeClickEvent());
    }
}