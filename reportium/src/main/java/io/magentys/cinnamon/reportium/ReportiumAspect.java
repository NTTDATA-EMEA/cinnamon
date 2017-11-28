package io.magentys.cinnamon.reportium;

import io.magentys.cinnamon.eventbus.EventBusContainer;
import io.magentys.cinnamon.reportium.events.*;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cucumber.runtime.junit.ExecutionUnitRunner;
import cucumber.runtime.junit.FeatureRunner;

import gherkin.formatter.Reporter;
import gherkin.formatter.model.Result;
import gherkin.formatter.model.Step;
import gherkin.formatter.model.Tag;

@Aspect
public class ReportiumAspect {

    private static final ThreadLocal<String> featureName = new ThreadLocal<>();
    private static final ThreadLocal<String> scenarioName = new ThreadLocal<>();
    private static final ThreadLocal<Reporter> reporter = new ThreadLocal<>();
    private static final ThreadLocal<List<Result>> results = new ThreadLocal<>();
    private static final ThreadLocal<Step> stepName = new ThreadLocal<>();

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
    @Pointcut("execution(public * cucumber.runtime.junit.ExecutionUnitRunner.run(..))")
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

    @Pointcut("execution(* cucumber.runtime.Runtime.addHookToCounterAndResult(..))")
    public void addHookToCounterAndResult() {
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

    @Before("runBeforeHooks()")
    public void beforeRunBeforeHooks(JoinPoint joinPoint) {
        Set<Tag> tags = (Set<Tag>)joinPoint.getArgs()[1];
        List<String> list = new ArrayList<String>();
        tags.stream().forEach(a->list.add(a.getName()));
        EventBusContainer.getEventBus().post(new TagsEvent(list));
    }

    @Before("runFeature()")
    public void beforeRunFeature(JoinPoint joinPoint) {
        if (System.getProperties().stringPropertyNames().contains("hubUrl") && System.getProperty("hubUrl").contains("perfectomobile")) {
        EventBusContainer.getEventBus().register(new ReportiumLogger());
        }
        FeatureRunner featureRunner = (FeatureRunner) joinPoint.getTarget();
        ReportiumAspect.featureName.set(featureRunner.getName());
        EventBusContainer.getEventBus().post(new BeforeFeatureScenario(featureRunner.getName()));
    }

    @Before("runScenario()")
    public void beforeRunScenario(JoinPoint joinPoint) {
        ExecutionUnitRunner executionUnitRunner = (ExecutionUnitRunner) joinPoint.getTarget();
        ReportiumAspect.scenarioName.set(executionUnitRunner.getName());
        EventBusContainer.getEventBus().post(new BeforeScenarioEvent(executionUnitRunner.getName(), executionUnitRunner.getDescription().toString()));
    }

    @Before("buildBackendWorlds() && args(reporter,..)")
    public void beforeBuildBackendWorlds(Reporter reporter) {
        ReportiumAspect.reporter.set(reporter);
    }

    @After("buildBackendWorlds()")
    public void afterBuildBackendWorlds() {
        ReportiumAspect.results.set(new ArrayList<>());
    }

    @After("addStepToCounterAndResult() && args(result,..)")
    public void afterAddStepToCounterAndResult(Result result) {
        ReportiumAspect.results.get().add(result);
        EventBusContainer.getEventBus().post(new StepFinishedEvent(result, reporter.get(), result.getErrorMessage(), result.getError()));
    }

    @After("addHookToCounterAndResult() && args(result,..)")
    public void afterAddHookToCounterAndResult(Result result) {
        EventBusContainer.getEventBus().post(new BeforeHooksFinishedEvent(result, result.getErrorMessage(), result.getError()));
    }

    @After("runAfterHooks()")
    public void afterRunAfterHooks() {
        EventBusContainer.getEventBus().post(new AfterHooksFinishedEvent());
    }

    @After("disposeBackendWorlds()")
    public void afterDisposeBackendWorlds() {
        EventBusContainer.getEventBus().post(new ScenarioFinishedEvent(ReportiumAspect.results.get()));
    }

    @Before("runStep()")
    public void beforeRunStep(JoinPoint joinPoint) {
        EventBusContainer.getEventBus().post(new AfterStepEvent(((Step)joinPoint.getArgs()[1]).getName()));
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
