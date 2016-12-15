package io.magentys.cinnamon.webdriver.config

import org.scalatest.{BeforeAndAfterEach, FunSpec, Matchers}

class CinnamonWebDriverConfigTest extends FunSpec with Matchers with BeforeAndAfterEach {

  describe("CinnamonWebDriverConfig") {
    it("Take system properties as overrides") {
      val config = CinnamonWebDriverConfig.config
      assert(config.getBoolean("reuse-browser-session"))
    }
  }

  override protected def beforeEach(): Unit = {
    System.setProperty("browserProfile", "chrome")
    System.setProperty("reuse-browser-session", "true")
  }

  override protected def afterEach(): Unit = {
    System.clearProperty("reuse-browser-session")
    System.clearProperty("browserProfile")
  }
}
