package cucumber.runtime.junit;

import cucumber.api.Result;
import cucumber.api.TestCase;
import cucumber.runner.EventBus;
import io.magentys.cinnamon.cucumber.events.AfterHooksFinishedEvent;
import io.magentys.cinnamon.cucumber.events.CucumberFinishedEvent;
import io.magentys.cinnamon.cucumber.events.ScenarioFinishedEvent;
import io.magentys.cinnamon.cucumber.events.StepFinishedEvent;
import io.magentys.cinnamon.eventbus.EventBusContainer;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Aspect
public class CucumberAspect {

    private static final ThreadLocal<String> featureName = new ThreadLocal<>();
    private static final ThreadLocal<String> scenarioName = new ThreadLocal<>(); //TODO Do we still need this?
    private static final ThreadLocal<List<Result>> results = new ThreadLocal<>();
    private static final ThreadLocal<EventBus> bus = new ThreadLocal<>();
    private static final ThreadLocal<TestCase> testCase = new ThreadLocal<>();

    /**
     * Pointcut for <code>org.junit.runners.ParentRunner.run</code> method.
     */
    @Pointcut("within(org.junit.runners.ParentRunner+) && execution(* run(..))")
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
     * Pointcut for <code>cucumber.runtime.junit.PickleRunners.PickleRunner.run</code> method.
     */
    @Pointcut("within(cucumber.runtime.junit.PickleRunners.PickleRunner+) && execution(* run(..))")
    public void runScenario() {
        // pointcut body must be empty
    }

    /**
     * Pointcut for <code>cucumber.runner.Runner.buildBackendWorlds</code> method.
     */
    @Pointcut("execution(private * cucumber.runner.Runner.buildBackendWorlds(..))")
    public void buildBackendWorlds() {
        // pointcut body must be empty
    }

    /**
     * Pointcut for <code>cucumber.runner.Runner.getBus</code> method.
     */
    @Pointcut("execution(public * cucumber.runner.Runner.getBus(..))")
    public void getBus() {
        // pointcut body must be empty
    }

    /**
     * Pointcut for <code>cucumber.runner.Runner.createTestCaseForPickle</code> method.
     */
    @Pointcut("execution(private * cucumber.runner.Runner.createTestCaseForPickle(..))")
    public void createTestCaseForPickle() {
        // pointcut body must be empty
    }

    /**
     * Pointcut for <code>cucumber.runner.Scenario.add</code> method.
     */
    @Pointcut("execution(* cucumber.runner.Scenario.add(..))")
    public void addResult() {
        // pointcut body must be empty
    }

    /**
     * Pointcut for <code>cucumber.runner.TestCase.run</code> method.
     */
    @Pointcut("execution(* cucumber.runner.TestCase.run(..))")
    public void runAfterHooks() {
        // pointcut body must be empty
    }

    /**
     * Pointcut for <code>cucumber.runner.Runner.disposeBackendWorlds</code> method.
     */
    @Pointcut("execution(private void cucumber.runner.Runner.disposeBackendWorlds(..))")
    public void disposeBackendWorlds() {
        // pointcut body must be empty
    }

    @Before("runFeature()")
    public void beforeRunFeature(JoinPoint joinPoint) {
        FeatureRunner featureRunner = (FeatureRunner) joinPoint.getTarget();
        CucumberAspect.featureName.set(featureRunner.getName());
    }

    @AfterReturning(pointcut = "getBus()", returning = "bus")
    public void afterReturningBus(EventBus bus) {
        this.bus.set(bus);
    }

    @AfterReturning(pointcut = "createTestCaseForPickle()", returning = "testCase")
    public void afterReturningTestCase(TestCase testCase) {
        this.testCase.set(testCase);
    }

    @Before("runScenario()")
    public void beforeRunScenario(JoinPoint joinPoint) {
        PickleRunners.PickleRunner pickleRunner = (PickleRunners.PickleRunner) joinPoint.getTarget();
        CucumberAspect.scenarioName.set(pickleRunner.getDescription().getDisplayName());
    }

    @After("buildBackendWorlds()")
    public void afterBuildBackendWorlds() {
        CucumberAspect.results.set(new ArrayList<>());
    }

    @After("addResult() && args(result,..)")
    public void afterAddResult(Result result) {
        CucumberAspect.results.get().add(result);
        EventBusContainer.getEventBus().post(new StepFinishedEvent(bus.get(), testCase.get(), result));
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
            bus.remove();
            testCase.remove();
            results.remove();
            scenarioName.remove();
            featureName.remove();
        }
    }
}
