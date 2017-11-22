package io.magentys.cinnamon.reportium;

import com.google.common.eventbus.Subscribe;
import com.perfecto.reportium.client.ReportiumClient;
import com.perfecto.reportium.client.ReportiumClientFactory;
import com.perfecto.reportium.model.PerfectoExecutionContext;
import com.perfecto.reportium.model.Project;
import com.perfecto.reportium.test.TestContext;
import com.perfecto.reportium.test.result.TestResultFactory;
import io.magentys.cinnamon.events.*;
import io.magentys.cinnamon.webdriver.WebDriverContainer;
import io.magentys.cinnamon.webdriver.events.AfterConstructorEvent;
import org.openqa.selenium.WebDriver;

import java.util.Arrays;
import java.util.List;
import java.util.Set;


public class ReportiumLogger {

    ReportiumClient reportiumClient;
    boolean stepResult;
    String scenarioName;
    String featureName;
    boolean failed = false;
    List<String> tags;
    String stepName;

    @Subscribe
    public void handleEvent(AfterConstructorEvent event) {
        if (reportiumClient == null) {
        reportiumClient = createRemoteReportiumClient(WebDriverContainer.getWebDriverContainer().getWebDriver());
        String[] tagsArray = tags.toArray(new String[tags.size()]);
        reportiumClient.testStart(featureName + "-" +scenarioName, new TestContext(tagsArray));
        }
    }

    @Subscribe
    public void handleEvent(final ScenarioEvent event) {
        scenarioName = event.getScenarioName().replaceFirst("Scenario: ", "");
    }

    @Subscribe
    public void handleEvent(final FeatureEvent event) {
        featureName = event.getFeatureName().replaceFirst("Feature: ", "");
    }

    @Subscribe
    public void handleEvent(final StepEvent event) {
        stepName = event.getName();
    }

    @Subscribe
    public void handleEvent(final TagEvent event) {
        tags = event.getTags();
    }

    @Subscribe
    public void handleEvent(final BeforeHookEvent event) {
         if (!failed) {
            if (event.isFailed()) {
                reportiumClient.testStep("before hook name status: " + event.getStatus());
                reportiumClient.testStop(TestResultFactory.createFailure(event.getErrorMessage(), event.getError()));
                failed = true;
            } else {
                reportiumClient.testStep("before hook name status: " + event.getStatus());
            }
        }
    }

    @Subscribe
    public void handleEvent(final TestStepFinishedEvent event) {
        if (!failed) {
            if (event.isFailed()) {
                reportiumClient.testStep(stepName + " -> " + event.getStatus());
                reportiumClient.testStop(TestResultFactory.createFailure(event.getErrorMessage(), event.getError()));
                failed = true;
            } else {
                reportiumClient.testStep(stepName + " -> " + event.getStatus());
            }
        }
    }

    @Subscribe
    public void handleEvent(final TestCaseFinishedEvent event) {
        if (!event.isFailed() && !failed) {
            reportiumClient.testStop(TestResultFactory.createSuccess());
        }
    }


    private static ReportiumClient createRemoteReportiumClient(WebDriver driver) {
        PerfectoExecutionContext perfectoExecutionContext = new PerfectoExecutionContext.PerfectoExecutionContextBuilder()
                .withProject(new Project("Sample Reportium project", "1.0")).withWebDriver(driver).build();
        return new ReportiumClientFactory().createPerfectoReportiumClient(perfectoExecutionContext);
    }

}
