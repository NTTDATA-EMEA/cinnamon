package cucumber.runtime.junit;

import cucumber.api.event.EventListener;
import cucumber.api.Result;
import io.magentys.cinnamon.cucumber.events.AfterHooksFinishedEvent;
import io.magentys.cinnamon.cucumber.events.CucumberFinishedEvent;
import io.magentys.cinnamon.cucumber.events.ScenarioFinishedEvent;
import io.magentys.cinnamon.cucumber.events.StepFinishedEvent;
import io.magentys.cinnamon.eventbus.EventBusContainer;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.util.ArrayList;
import java.util.List;

@Aspect
public class CucumberAspect {

    private static final ThreadLocal<String> featureName = new ThreadLocal<>();
    private static final ThreadLocal<String> scenarioName = new ThreadLocal<>();
    private static final ThreadLocal<EventListener> reporter = new ThreadLocal<>();
    private static final ThreadLocal<List<Result>> results = new ThreadLocal<>();

    /**
     * Pointcut for <code>cucumber.api.junit.Cucumber.run</code> method.
     */
    @Pointcut("execution(public * cucumber.api.junit.Cucumber.run(..))")
    public void runCucumber() {
        // pointcut body must be empty
    }

    /**
     * Pointcut for <code>cucumber.runtime.junit.FeatureRunner.FeatureRunner.run</code> method.
     */
    @Pointcut("execution(public * cucumber.runtime.junit.FeatureRunner.run(..))")
    public void runFeature() {
        // pointcut body must be empty
    }

    /**
     * Pointcut for <code>cucumber.runtime.junit.ExecutionUnitRunner.run</code> method.
     */
    @Pointcut("within(cucumber.runtime.junit.PickleRunners.PickleRunner+) && execution(* run(..))")
    public void runScenario() {
        // pointcut body must be empty
    }

    /**
     * Pointcut for <code>cucumber.runtime.Runtime.buildBackendWorlds</code> method.
     */
    @Pointcut("execution(public void cucumber.runtime.Runtime.buildBackendWorlds(..))")
    public void buildBackendWorlds() {
        // pointcut body must be empty
    }

    /**
     * Pointcut for <code>cucumber.runtime.Runtime.runBeforeHooks</code> method.
     */
    @Pointcut("execution(public void cucumber.runtime.Runtime.runBeforeHooks(..))")
    public void runBeforeHooks() {
        // pointcut body must be empty
    }

    /**
     * Pointcut for <code>cucumber.runtime.Runtime.runStep</code> method.
     */
    @Pointcut("execution(public void cucumber.runtime.Runtime.runStep(..))")
    public void runStep() {
        // pointcut body must be empty
    }

    /**
     * Pointcut for <code>cucumber.runtime.Runtime.addStepToCounterAndResult</code> method.
     */
    @Pointcut("execution(* cucumber.runtime.Runtime.addStepToCounterAndResult(..))")
    public void addStepToCounterAndResult() {
        // pointcut body must be empty
    }

    /**
     * Pointcut for <code>cucumber.runtime.Runtime.runAfterHooks</code> method.
     */
    @Pointcut("execution(public void cucumber.runtime.Runtime.runAfterHooks(..))")
    public void runAfterHooks() {
        // pointcut body must be empty
    }

    /**
     * Pointcut for <code>cucumber.runtime.Runtime.disposeBackendWorlds</code> method.
     */
    @Pointcut("execution(public void cucumber.runtime.Runtime.disposeBackendWorlds(..))")
    public void disposeBackendWorlds() {
        // pointcut body must be empty
    }

    @Before("runFeature()")
    public void beforeRunFeature(JoinPoint joinPoint) {
        FeatureRunner featureRunner = (FeatureRunner) joinPoint.getTarget();
        CucumberAspect.featureName.set(featureRunner.getName());
    }

    @Before("runScenario()")
    public void beforeRunScenario(JoinPoint joinPoint) {

        System.out.println("against all probability i am in my beforeRunScenario hook... tidy.");

        PickleRunners.PickleRunner pickleRunner = (PickleRunners.PickleRunner) joinPoint.getTarget();
        CucumberAspect.scenarioName.set(pickleRunner.getDescription().getDisplayName());
    }

    @Before("buildBackendWorlds() && args(eventListener,..)")
    public void beforeBuildBackendWorlds(EventListener eventListener) {
        CucumberAspect.reporter.set(eventListener);
    }

    @After("buildBackendWorlds()")
    public void afterBuildBackendWorlds() {
        CucumberAspect.results.set(new ArrayList<>());
    }

    @After("addStepToCounterAndResult() && args(result,..)")
    public void afterAddStepToCounterAndResult(Result result) {
        CucumberAspect.results.get().add(result);
        EventBusContainer.getEventBus().post(new StepFinishedEvent(result, reporter.get()));
    }

    @After("runAfterHooks()")
    public void afterRunAfterHooks() {
        EventBusContainer.getEventBus().post(new AfterHooksFinishedEvent());
    }

    @After("disposeBackendWorlds()")
    public void afterDisposeBackendWorlds() {
        EventBusContainer.getEventBus().post(new ScenarioFinishedEvent(CucumberAspect.results.get()));
    }

    @After("runCucumber()")
    public void afterRunCucumber() {
        try {
            EventBusContainer.getEventBus().post(new CucumberFinishedEvent());
        } finally {
            reporter.remove();
            results.remove();
            scenarioName.remove();
            featureName.remove();
        }
    }
}
