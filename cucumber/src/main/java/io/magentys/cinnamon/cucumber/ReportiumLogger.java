package io.magentys.cinnamon.cucumber;

import com.google.common.eventbus.Subscribe;
import com.perfecto.reportium.client.ReportiumClient;
import com.perfecto.reportium.client.ReportiumClientFactory;
import com.perfecto.reportium.model.PerfectoExecutionContext;
import com.perfecto.reportium.model.Project;
import com.perfecto.reportium.test.TestContext;
import com.perfecto.reportium.test.result.TestResultFactory;
import io.magentys.cinnamon.cucumber.events.ScenarioFinishedEvent;
import io.magentys.cinnamon.cucumber.events.StepFinishedEvent;
import io.magentys.cinnamon.webdriver.events.AfterConstructorEvent;
import org.openqa.selenium.WebDriver;

public class ReportiumLogger {

    ReportiumClient reportiumClient;

    @Subscribe
    public void handleEvent(AfterConstructorEvent event) {
        System.out.println("___REPORTIUMLOGGER AFTER CONSTRUCTOR");

        reportiumClient = createRemoteReportiumClient(event.getThis());

        reportiumClient.testStart("The new sanity test",new TestContext("Sanity"));

    }

    @Subscribe
    public void handleEvent(final StepFinishedEvent event) {
        reportiumClient.testStep("stepName if "+event.getStatus());
    }

    @Subscribe
    public void handleEvent(final ScenarioFinishedEvent event) {
        if (event.isFailed()) {
            reportiumClient.testStop(TestResultFactory.createFailure("Scenario failed.", new Throwable()));
        }
        if (!event.isFailed()) {
            reportiumClient.testStop(TestResultFactory.createSuccess());
        }
    }


    private static ReportiumClient createRemoteReportiumClient(WebDriver driver) {
        PerfectoExecutionContext perfectoExecutionContext = new PerfectoExecutionContext.PerfectoExecutionContextBuilder()
                .withProject(new Project("Sample Reportium project", "1.0")).withWebDriver(driver).build();
        return new ReportiumClientFactory().createPerfectoReportiumClient(perfectoExecutionContext);
    }

}
