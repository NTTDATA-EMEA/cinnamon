package io.magentys.cinnamon.webdriver.factory

import io.appium.java_client.remote.{MobileCapabilityType, MobilePlatform}
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.remote.{BrowserType, CapabilityType, DesiredCapabilities}
import org.openqa.selenium.{Platform, WebDriver}
import org.scalatest.{FlatSpec, Matchers}

class DriverRegistrySpec extends FlatSpec with Matchers {

  behavior of "DriverRegistry"

  it should "check that a new driver added by a user is not already registered" in {
    intercept[IllegalArgumentException] {
      DriverRegistry.registerDriverClass(classOf[ChromeDriver].getName, new java.util.HashMap[String, Any] {
        put(CapabilityType.BROWSER_NAME, "chrome")
      })
    }
  }

  it should "handle new drivers added by a user" in {
    val testCapabilities = new DesiredCapabilities("TEST", "", Platform.ANY)
    DriverRegistry.registerDriverClass(classOf[ATestDriver].getName, new java.util.HashMap[String, Any] {
      put(CapabilityType.BROWSER_NAME, "TEST")
    })
    val actual: WebDriver = WebDriverFactory().getDriver(testCapabilities, None, None, None)
    actual shouldBe a[ATestDriver]
  }

  it should "return a driver class for Selenium FirefoxDriver given firefox desired capabilities" in {
    val capabilities = DesiredCapabilities.firefox
    val driverClass = DriverRegistry.getDriverClass(capabilities)
    driverClass shouldBe Some(classOf[org.openqa.selenium.firefox.FirefoxDriver])
  }

  it should "return an driver class for Appium AndroidDriver given platformName matches Android" in {
    val capabilities = new DesiredCapabilities(new java.util.HashMap[String, Any] {
      put(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID)
    })
    val driverClass = DriverRegistry.getDriverClass(capabilities)
    driverClass shouldBe Some(classOf[io.appium.java_client.android.AndroidDriver[_]])
  }

  it should "return a driver class for Appium IOSDriver given platformName matches iOS" in {
    val capabilities = new DesiredCapabilities(new java.util.HashMap[String, Any] {
      put(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS)
    })
    val driverClass = DriverRegistry.getDriverClass(capabilities)
    driverClass shouldBe Some(classOf[io.appium.java_client.ios.IOSDriver[_]])
  }

  it should "return a remote driver class for Selenium RemoteWebDriver given platformName is not specified" in {
    val capabilities = new DesiredCapabilities(new java.util.HashMap[String, Any] {
      put(CapabilityType.BROWSER_NAME, BrowserType.CHROME)
    })
    val remoteDriverClass = DriverRegistry.getRemoteDriverClass(capabilities)
    remoteDriverClass shouldBe Some(classOf[org.openqa.selenium.remote.RemoteWebDriver])
  }

  it should "return a remote driver class for Appium AndroidDriver given platformName matches Android" in {
    val capabilities = new DesiredCapabilities(new java.util.HashMap[String, Any] {
      put(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID)
    })
    val remoteDriverClass = DriverRegistry.getDriverClass(capabilities)
    remoteDriverClass shouldBe Some(classOf[io.appium.java_client.android.AndroidDriver[_]])
  }

  it should "return a remote driver class for Appium IOSDriver given platformName matches iOS" in {
    val capabilities = new DesiredCapabilities(new java.util.HashMap[String, Any] {
      put(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS)
    })
    val remoteDriverClass = DriverRegistry.getDriverClass(capabilities)
    remoteDriverClass shouldBe Some(classOf[io.appium.java_client.ios.IOSDriver[_]])
  }
}