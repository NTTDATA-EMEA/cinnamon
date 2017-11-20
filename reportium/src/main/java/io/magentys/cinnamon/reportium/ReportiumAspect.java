//package io.magentys.cinnamon.reportium;
//
//import io.magentys.cinnamon.eventbus.EventBusContainer;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//
//@Aspect
//public class ReportiumAspect {
//
//    /**
//     * Pointcut for <code>cucumber.api.junit.Cucumber.run</code> method.
//     */
//    @Pointcut("execution(public * cucumber.api.junit.Cucumber.run(..))")
//    public void runCucumber() {
//        // pointcut body must be empty
//    }
//
//    @Before("runCucumber()")
//    public void beforeRunCucumber() {
//        System.out.println("___REPORTIUMASPECT THROWN");
//
//        EventBusContainer.getEventBus().register(new ReportiumLogger());
//    }
//
//
//
//
//
//
//}
