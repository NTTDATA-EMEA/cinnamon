package io.magentys.cinnamon.webdriver.remote

import com.typesafe.config.Config
import org.openqa.selenium.remote.DesiredCapabilities

class Perfecto extends CinnamonRemote {

  override val name: String = "perfecto"

  override def matchesHubUrl(url: String): Boolean = url.endsWith("perfectomobile/wd/hub")

  override def capabilities(browserProfile: String, config: Config): DesiredCapabilities = {
    val mainRemoteCaps: DesiredCapabilities = super.capabilities(browserProfile, config)
    val additionalRemoteCaps = new DesiredCapabilities
    additionalRemoteCaps.setCapability("browserVersion", config.getString("capabilities-profiles." + browserProfile + ".perfecto.browserVersion"))
    additionalRemoteCaps.setCapability("platformVersion", config.getString("capabilities-profiles." + browserProfile + ".perfecto.platformVersion"))
    additionalRemoteCaps.setCapability("location", config.getString("capabilities-profiles." + browserProfile + ".perfecto.location"))
    additionalRemoteCaps.setCapability("resolution", config.getString("capabilities-profiles." + browserProfile + ".perfecto.resolution"))
    additionalRemoteCaps.setCapability("url", config.getString("hubUrl"))
    additionalRemoteCaps.setCapability("platformName", config.getString("capabilities-profiles." + browserProfile + ".perfecto.platformName"))
    mainRemoteCaps.merge(additionalRemoteCaps)
  }
}