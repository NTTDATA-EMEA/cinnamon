package io.magentys.cinnamon.webdriver.factory

import io.appium.java_client.remote.{MobileCapabilityType, MobilePlatform}
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.{Platform, WebDriver}
import org.scalatest.mock.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

class DriverRegistrySpec extends FlatSpec with Matchers with MockitoSugar {

  behavior of "DriverRegistry"

  it should "handle new drivers added by user" in {
    val testCapabilities = new DesiredCapabilities("TEST", "", Platform.ANY)
    DriverRegistry.addDriverProvider(testCapabilities, classOf[ATestDriver].getName)
    val actual: WebDriver = WebDriverFactory().getDriver(testCapabilities, None, None, None)
    actual shouldBe a[ATestDriver]
  }

  it should "return an instance of local selenium FirefoxDriver given firefox desired capabilities" in {
    val firefoxDesiredCapabilities = DesiredCapabilities.firefox
    val actual = DriverRegistry.locals.hasMappingFor(firefoxDesiredCapabilities)
    actual shouldBe true
  }

  it should "return an instance of local appium AndroidDriver given platformName matches android" in {
    val androidCapabilities = new DesiredCapabilities(new java.util.HashMap[String, Any] {
      put(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID)
    })
    val actual = DriverRegistry.locals.hasMappingFor(androidCapabilities)
    actual shouldBe true
  }

  it should "return an instance of local appium IOSDriver given platformName matches iOS" in {
    val iOSCapabilities = new DesiredCapabilities(new java.util.HashMap[String, Any] {
      put(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS)
    })
    val actual = DriverRegistry.locals.hasMappingFor(iOSCapabilities)
    actual shouldBe true
  }
}
