package io.magentys.cinnamon.webdriver.capabilities

import org.openqa.selenium.Platform
import org.scalatest.{FlatSpec, Matchers}

class CapabilitiesModelSpec extends FlatSpec with Matchers {

  behavior of "SeleniumCapabilities"

  it should "not allow empty browserName" in {
    intercept[IllegalArgumentException] {
      SeleniumCapabilities("")
    }
  }

  it should "successfully initialise SeleniumCapabilities with a valid browserName and default values" in {
    val capabilities = SeleniumCapabilities("chrome")
    capabilities.browserName shouldBe "chrome"
    capabilities.platform shouldBe None
    capabilities.version shouldBe None
    capabilities.nativeEvents shouldBe None
    capabilities.javascriptEnabled shouldBe Some(true)
    capabilities.acceptSslCerts shouldBe Some(true)
  }

  it should "successfully initialise SeleniumCapabilities with a valid platform" in {
    val capabilities = SeleniumCapabilities("chrome", None, Some("WINDOWS"))
    val actual = Platform.valueOf(capabilities.platform.get)
    actual shouldBe Platform.WINDOWS
  }

  it should "return a Map without options with default values" in {
    val capsMap = SeleniumCapabilities("chrome")
    val clearedOptionsMap = capsMap.toMap(capsMap)
    val expected = Map("acceptSslCerts" -> true, "browserName" -> "chrome", "javascriptEnabled" -> true)
    clearedOptionsMap shouldBe expected
  }

  it should "exclude properties from Capabilities map" in {
    val properties = Map("myProp" -> "abc")
    val capsMap = SeleniumCapabilities("chrome", None, None, None, Some(true), Some(true), Some(properties)).toMap
    capsMap.get("properties") shouldBe None
  }

  it should "set properties as system properties if any" in {
    val properties = Map("myProp" -> "abc")
    val capsMap = SeleniumCapabilities("chrome", None, None, None, Some(true), Some(true), Some(properties)).toMap
    sys.props.get("myProp").get shouldBe "abc"
  }

  it should "set properties as system properties if any in setProperties" in {
    val properties = Map("myProp" -> "abc")
    val capsMap = SeleniumCapabilities("chrome", None, None, None, Some(true), Some(true), Some(properties)).setSystemProps()
    sys.props.get("myProp").get shouldBe "abc"
  }

  it should "do nothing with regards to properties if no properties passed" in {
    SeleniumCapabilities("chrome").setSystemProps shouldBe ((): Unit)
  }

  behavior of "CapsUtils"

  it should "return a Map of capabilities from class properties" in {
    val seleniumCapabilities = SeleniumCapabilities("chrome")
    val expected = Map("nativeEvents" -> None,
      "acceptSslCerts" -> Some(true),
      "browserName" -> "chrome",
      "version" -> None,
      "properties" -> None,
      "javascriptEnabled" -> Some(true),
      "platform" -> None)
    CapUtils.getCCs(seleniumCapabilities) shouldBe expected
  }
}
