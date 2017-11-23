package io.magentys.cinnamon.reportium;

import io.magentys.cinnamon.eventbus.EventBusContainer;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class ReportiumAspect {


//    @Pointcut("execution(* cucumber.api.junit.Cucumber.*(..))")
//    @Pointcut("execution(public * cucumber.runtime.junit.FeatureRunner.run(..))")
//    @Pointcut("execution(public void cucumber.runtime.Runtime.runStep(..))")
    @Pointcut("execution(* io.magentys.cinnamon.conf.Env.initConfig(..))")
    public void runCucumber() {
        // pointcut body must be empty
    }

    @Before("runCucumber()")
    public void beforeRunCucumber() {
        System.out.println("___REPORTIUMASPECT THROWN");

        EventBusContainer.getEventBus().register(new ReportiumLogger());
    }
}
