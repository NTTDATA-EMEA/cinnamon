package io.magentys.cinnamon.webdriver.remote

import com.google.common.eventbus.Subscribe
import com.perfecto.reportium.client.ReportiumClient
import com.typesafe.config.Config
import io.magentys.cinnamon.events.TestStepFinishedEvent
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