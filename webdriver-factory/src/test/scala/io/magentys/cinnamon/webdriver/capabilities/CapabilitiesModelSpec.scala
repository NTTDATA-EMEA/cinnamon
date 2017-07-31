package io.magentys.cinnamon.webdriver.capabilities

import org.openqa.selenium.Platform
import org.scalatest.{FlatSpec, Matchers}

class CapabilitiesModelSpec extends FlatSpec with Matchers {

  behavior of "CinnamonCapabilities"

  it should "not allow empty browserName" in {
    intercept[IllegalArgumentException] { BasicCapabilities("") }
  }

  it should "successfully initialise with a valid browserName and default values" in {
    val basicCapabilities = BasicCapabilities("chrome")
    basicCapabilities.browserName shouldBe "chrome"
    basicCapabilities.platform shouldBe None
    basicCapabilities.version shouldBe None
    basicCapabilities.nativeEvents shouldBe None
    basicCapabilities.javascriptEnabled shouldBe Some(true)
    basicCapabilities.acceptSslCerts shouldBe Some(true)
  }

  it should "successfully initialise with a valid platform name" in {
    val basicCapabilities = BasicCapabilities("chrome", None, Some("WINDOWS"))
    val actual = Platform.valueOf(basicCapabilities.platform.get)
    actual shouldBe Platform.WINDOWS
  }

  it should "retrun a Map without options with default values" in {
    val capsMap = BasicCapabilities("chrome")
    val clearedOptionsMap = capsMap.toMap(capsMap)
    val expected = Map("acceptSslCerts" -> true, "browserName" -> "chrome", "javascriptEnabled" -> true)
    clearedOptionsMap shouldBe expected
  }

  it should "exclude properties from Capabilities map" in {
    val properties = Map("myProp" -> "abc")
    val capsMap = BasicCapabilities("chrome", None, None, None, Some(true), Some(true), Some(properties)).asMap
    capsMap.get("properties") shouldBe None
  }

  it should "set properties as system properties if any" in {
    val properties = Map("myProp" -> "abc")
    val capsMap = BasicCapabilities("chrome", None, None, None, Some(true), Some(true), Some(properties)).asMap
    sys.props.get("myProp").get shouldBe "abc"
  }

  it should "set properties as system properties if any in setProperties" in {
    val properties = Map("myProp" -> "abc")
    val capsMap = BasicCapabilities("chrome", None, None, None, Some(true), Some(true), Some(properties)).setSystemProps()
    sys.props.get("myProp").get shouldBe "abc"
  }

  it should "do nothing with regards to properties if no properties passed" in {
    BasicCapabilities("chrome").setSystemProps shouldBe((): Unit)
  }

  behavior of "CapsUtils"

  it should "return a Map of capabilities from class properties" in {
    val basicCapabilities = BasicCapabilities("chrome")
    val expected =  Map("nativeEvents" -> None,
      "acceptSslCerts" -> Some(true),
      "browserName" -> "chrome",
      "version" -> None,
      "properties" -> None,
      "javascriptEnabled" -> Some(true),
      "platform" -> None)

    CapUtils.getCCs(basicCapabilities) shouldBe expected
  }

}
