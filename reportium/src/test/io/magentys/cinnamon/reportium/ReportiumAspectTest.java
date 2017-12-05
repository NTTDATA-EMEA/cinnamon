package io.magentys.cinnamon.reportium;

import cucumber.runtime.junit.FeatureRunner;
import cucumber.runtime.model.CucumberFeature;
import gherkin.formatter.model.Comment;
import gherkin.formatter.model.Feature;
import io.magentys.cinnamon.eventbus.EventBusContainer;
import io.magentys.cinnamon.reportium.events.BeforeFeatureScenario;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class ReportiumAspectTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private ProceedingJoinPoint proceedingJoinPoint;

    private ReportiumAspect sampleAspect = new ReportiumAspect();

    //TODO create tests for the aspects

    @Test
    public void testBeforeRunFeature() throws Throwable {

    //TODO mock the featureRunner, return a mock feature, then verify the thrown event contains data
//        when(proceedingJoinPoint.getTarget()).thenReturn(new FeatureRunner(new CucumberFeature(new Feature())));

        sampleAspect.beforeRunFeature(proceedingJoinPoint);

        FeatureRunner featureRunner = (FeatureRunner) proceedingJoinPoint.getTarget();

        System.out.println("___ GETNAME : " + featureRunner.getName());

        proceedingJoinPoint.proceed();

        Mockito.verify(proceedingJoinPoint, times(1))
                .proceed(new Object[] {new  BeforeFeatureScenario(featureRunner.getName())});
    }

    @Test
    public void testBeforeRunFeature1() throws Throwable {

        String featureName = "featureName";

        BeforeFeatureScenario resultEvent = new BeforeFeatureScenario(featureName);

        ReportiumAspect aspect = new ReportiumAspect();

        aspect.beforeRunFeature(proceedingJoinPoint);

        System.out.println("___ EVENT TOSTRING : "+EventBusContainer.getEventBus().toString());

        System.out.println("___ EVENTBUS DOES IT CONTAIN : "+EventBusContainer.getEventBus().identifier().contains("BeforeFeatureScenario"));

//        Assert.assertTrue(EvenatBusContainer.getEventBus());

    }

    @Test
    public void testAfterHooks() {

        ReportiumAspect aspect = new ReportiumAspect();

        aspect.afterRunAfterHooks();

        System.out.println("___ EVENTBUS DOES IT CONTAIN : "+EventBusContainer.getEventBus().identifier().length());

        System.out.println("___ identifier : "+EventBusContainer.getEventBus().identifier().isEmpty());

        Assert.assertTrue(EventBusContainer.getEventBus().identifier().contains("AfterHooksFinishedEvent"));

    }



}