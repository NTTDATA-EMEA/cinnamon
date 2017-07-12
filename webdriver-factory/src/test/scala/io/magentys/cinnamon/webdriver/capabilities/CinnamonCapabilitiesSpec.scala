package io.magentys.cinnamon.webdriver.capabilities

import java.util

import com.typesafe.config.ConfigFactory
import io.magentys.cinnamon.webdriver.Keys
import org.openqa.selenium.firefox.FirefoxDriver
import org.scalatest.{FlatSpec, Matchers}

class CinnamonCapabilitiesSpec extends FlatSpec with Matchers{

  behavior of "DriverConfig"

  val config = ConfigFactory.parseString(
    """
      |capabilities-profiles {
      | testChrome {
      |    browserName : "chrome"
      |    driverExtras : {
      |      "binary" : "//Applications//Google Chrome.app//Contents/MacOS//Google Chrome"
      |      "args" : [ "--allow-running-insecure-content", "--allow-file-access-from-files"]
      |      "bool" : true
      |    }
      | }
      |
      | testFirefox {
      |    browserName : "firefox"
      |    driverExtras : {
      |      "webdriver_assume_untrusted_issuer" : false
      |    }
      | }
      |}
    """.stripMargin)

  it should "return a Map of driver extras defined by user" in {
    val actual: Map[String, AnyRef] = DriverConfig.configToMap(config.getConfig("capabilities-profiles.testChrome."+Keys.DRIVER_EXTRAS_KEY))
    actual("binary") shouldBe "//Applications//Google Chrome.app//Contents/MacOS//Google Chrome"
    actual("bool") shouldBe true
    actual("args").asInstanceOf[util.ArrayList[String]].size shouldBe 2
    actual.size shouldBe 3
  }

  it should "bind to the correct driver extra capabilities [ChromeExtras] when browserName is [chrome]" in {
    val userMap: Map[String, AnyRef] = DriverConfig.configToMap(config.getConfig("capabilities-profiles.testChrome."+Keys.DRIVER_EXTRAS_KEY))
    val actual = DriverExtrasBinder.bindExtrasMap("chrome", userMap)
    actual shouldBe a [ChromeExtras]
  }

  it should "use user's settings primarily but fallback to the cinnamon default values " in {
    val profileconfig = Option(config.getConfig("capabilities-profiles.testFirefox."+Keys.DRIVER_EXTRAS_KEY))
//    val firefoxSettings = DriverConfig.loadDriverExtrasWithDefautls(profileconfig, "firefox")
//    firefoxSettings("webdriver_accept_untrusted_certs") shouldBe true
//    firefoxSettings("webdriver_assume_untrusted_issuer") shouldBe false //default is false but user has override
    pending
  }

  it should "initialise a DriverConfig given a user profile and a lookup of profiles" in {
//    val driverConfig = DriverConfig("testFirefox", config.getConfig("capabilities-profiles"), "")
//    val caps = driverConfig.desiredCapabilities
//    caps.getBrowserName shouldBe "firefox"
//    caps.getPlatform shouldBe null
//    caps.getVersion shouldBe ""
//    caps.getCapability(FirefoxDriver.PROFILE) should not be null
//    driverConfig.requiresMoveMouse shouldBe false
    pending
  }
}
