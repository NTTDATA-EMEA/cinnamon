package io.magentys.cinnamon.webdriver.remote

import com.google.common.eventbus.Subscribe
import com.perfecto.reportium.client.{ReportiumClient, ReportiumClientFactory}
import com.perfecto.reportium.model.{PerfectoExecutionContext, Project}
import com.typesafe.config.Config
import io.magentys.cinnamon.cucumber.events.{BeforeRunFeature, RunScenarioEvent, StepFinishedEvent}
import org.openqa.selenium.remote.DesiredCapabilities

class Perfecto extends CinnamonRemote {

  override val name: String = "perfecto"

  override def matchesHubUrl(url: String): Boolean = url.endsWith("perfectomobile/wd/hub")

  override def capabilities(browserProfile: String, config: Config): DesiredCapabilities = {
    val mainRemoteCaps: DesiredCapabilities = super.capabilities(browserProfile, config)

    //TODO Here we can add names, build numbers etc - Dependency on the "handlers"
    val additionalRemoteCaps = new DesiredCapabilities
    //    additionalRemoteCaps.setCapability("build", "someBuildNo")
    //    additionalRemoteCaps.setCapability("project", "someProject")
    //    additionalRemoteCaps.setCapability("name", "someName")
    mainRemoteCaps.merge(additionalRemoteCaps)
  }
}

class PerfectoLogger(reportiumClient: ReportiumClient) {

  @Subscribe
  private def handleEvent(event: BeforeRunFeature) = {
    System.out.println("_____LOGFEATURE")
    reportiumClient.testStep("step1")
  }

  @Subscribe
  private def handleEvent(event: RunScenarioEvent ) = {
    System.out.println("_____RUNSCENARIOEVENT")
    reportiumClient.testStep(event.getScenarioName)
  }

  @Subscribe
  private def handleEvent(event: StepFinishedEvent) = {
    System.out.println("_____STEPFINISHEDEVENT")
    reportiumClient.testStep(event.getStatus)
  }
}