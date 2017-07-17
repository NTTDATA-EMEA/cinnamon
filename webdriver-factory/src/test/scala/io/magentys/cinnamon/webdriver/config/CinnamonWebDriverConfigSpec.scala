package io.magentys.cinnamon.webdriver.config

import org.scalatest.{FlatSpec, Matchers}

class CinnamonWebDriverConfigSpec extends FlatSpec with Matchers {

  behavior of "CinnamonWebDriverConfig"

  it should "default the browserProfile to chrome" in {
    val driverConfig = new CinnamonWebDriverConfig().driverConfig
    driverConfig.desiredCapabilities.getCapability("browserName") shouldBe "chrome"
  }

  it should "set the browserName capability to firefox when the system property is set to firefox" in {
    try {
      System.setProperty("browserProfile", "firefox")
      val driverConfig = new CinnamonWebDriverConfig().driverConfig
      driverConfig.desiredCapabilities.getCapability("browserName") shouldBe "firefox"
    } finally {
      System.clearProperty("browserProfile")
    }
  }

  it should "take system properties as overrides" in {
    try {
      System.setProperty("reuse-browser-session", "true")
      val config = new CinnamonWebDriverConfig().config
      config.getBoolean("reuse-browser-session") shouldBe true
    } finally {
      System.clearProperty("reuse-browser-session")
    }
  }
}
