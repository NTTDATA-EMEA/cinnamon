package io.magentys.cinnamon.reportium;

import io.magentys.cinnamon.eventbus.EventBusContainer;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class ReportiumAspect {


    @Pointcut("execution(* cucumber.api.junit.Cucumber.*(..))")
    public void run() {
        // pointcut body must be empty
    }

    @Before("run()")
    public void beforeRunCucumber() {
        System.out.println("___REPORTIUMASPECT THROWN");

        EventBusContainer.getEventBus().register(new ReportiumLogger());
    }
}
