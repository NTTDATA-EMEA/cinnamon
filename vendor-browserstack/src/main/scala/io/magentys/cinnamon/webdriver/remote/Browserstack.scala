package io.magentys.cinnamon.webdriver.remote

import com.typesafe.config.Config
import org.openqa.selenium.remote.DesiredCapabilities

class Browserstack extends CinnamonRemote {

  override val name: String = "browserstack"

  override def matchesHubUrl(url: String): Boolean = url.endsWith("browserstack.com/wd/hub")

  override def capabilities(browserProfile: String, config: Config): DesiredCapabilities = {
    val mainRemoteCaps: DesiredCapabilities = super.capabilities(browserProfile, config)

    //TODO Here we can add names, build numbers etc - Handlers dependency
    val additionalRemoteCaps = new DesiredCapabilities
    //    additionalRemoteCaps.setCapability("build", "someBuildNo")
    //    additionalRemoteCaps.setCapability("project", "someProject")
    //    additionalRemoteCaps.setCapability("name", "someName")
    mainRemoteCaps.merge(additionalRemoteCaps)
  }

}
