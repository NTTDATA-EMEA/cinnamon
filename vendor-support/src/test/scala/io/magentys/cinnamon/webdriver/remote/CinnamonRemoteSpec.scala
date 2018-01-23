package io.magentys.cinnamon.webdriver.remote

import com.typesafe.config.ConfigFactory
import org.openqa.selenium.remote.DesiredCapabilities
import org.scalatest.{FlatSpec, Matchers}

class CinnamonRemoteSpec extends FlatSpec with Matchers {

  behavior of "CinnamonRemote"

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
      |    testVendor : {
      |       platformVersion : 9.1.0
      |       deviceName: "Samsung Galaxy"
      |    }
      | }
      |}
    """.stripMargin)

  val remoter = new CinnamonRemote {
    override def matchesHubUrl(url: String): Boolean = ???

    override val name: String = "testVendor"
  }

  it should "include vendor capabilities" in {
    val actual: DesiredCapabilities = remoter.capabilities("testChrome", config)
    actual.getCapability("platformVersion") shouldBe "9.1.0"
    actual.getCapability("deviceName") shouldBe "Samsung Galaxy"
  }

  it should "not include system properties in the vendor capabilities" in {
    try {
      System.setProperty("some.property", "xyz")
      val actual: DesiredCapabilities = remoter.capabilities("testChrome", config)
      actual.getCapability("some.property") shouldBe null
    } finally {
      System.clearProperty("some.property")
    }
  }

  it should "optionally override settings with system properties in the default vendor capabilities" in {
    try {
      System.setProperty("testVendor.user", "myUser")
      val actual: DesiredCapabilities = remoter.capabilities("testChrome", config)
      actual.getCapability("user") shouldBe "myUser"
      actual.getCapability("password") shouldBe null
    } finally {
      System.clearProperty("testVendor.user")
    }
  }

}