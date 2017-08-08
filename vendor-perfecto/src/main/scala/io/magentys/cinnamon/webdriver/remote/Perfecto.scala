package io.magentys.cinnamon.webdriver.remote

import com.typesafe.config.Config
import org.openqa.selenium.remote.DesiredCapabilities

class Perfecto extends CinnamonRemote {

  override val name: String = "perfecto"

  override def matchesHubUrl(url: String): Boolean = url.endsWith("perfectomobile/wd/hub")

  override def capabilities(browserProfile: String, config: Config): DesiredCapabilities = {
    val mainRemoteCaps: DesiredCapabilities = super.capabilities(browserProfile, config)
    val additionalRemoteCaps = new DesiredCapabilities
    val perfectoType = config.getString("capabilities-profiles." + browserProfile + ".perfecto.platformName")
    if (perfectoType.equals("Windows")) {
    additionalRemoteCaps.setCapability("browserVersion", config.getString("capabilities-profiles." + browserProfile + ".perfecto.browserVersion"))
    additionalRemoteCaps.setCapability("platformVersion", config.getString("capabilities-profiles." + browserProfile + ".perfecto.platformVersion"))
    additionalRemoteCaps.setCapability("location", config.getString("capabilities-profiles." + browserProfile + ".perfecto.location"))
    additionalRemoteCaps.setCapability("resolution", config.getString("capabilities-profiles." + browserProfile + ".perfecto.resolution"))
    additionalRemoteCaps.setCapability("url", config.getString("hubUrl"))
    additionalRemoteCaps.setCapability("platformName", config.getString("capabilities-profiles." + browserProfile + ".perfecto.platformName"))
  }

    else {
      additionalRemoteCaps.setCapability("platformName", config.getString("capabilities-profiles." + browserProfile + ".perfecto.platformName"))
      additionalRemoteCaps.setCapability("platformVersion", config.getString("capabilities-profiles." + browserProfile + ".perfecto.platformVersion"))
      additionalRemoteCaps.setCapability("manufacturer", config.getString("capabilities-profiles." + browserProfile + ".perfecto.manufacturer"))
      additionalRemoteCaps.setCapability("model", config.getString("capabilities-profiles." + browserProfile + ".perfecto.model"))
      additionalRemoteCaps.setCapability("location", config.getString("capabilities-profiles." + browserProfile + ".perfecto.location"))
      additionalRemoteCaps.setCapability("resolution", config.getString("capabilities-profiles." + browserProfile + ".perfecto.resolution"))
      additionalRemoteCaps.setCapability("network", config.getString("capabilities-profiles." + browserProfile + ".perfecto.network"))
      //browserName should not be send when running on iOS, this will become the appName in perfecto
      additionalRemoteCaps.setCapability("browserName", "")
    }

    mainRemoteCaps.merge(additionalRemoteCaps)
  }
}