package io.magentys.cinnamon.webdriver.factory

import java.util

import io.appium.java_client.remote.{MobileCapabilityType, MobilePlatform}
import org.openqa.selenium.WebDriver
import org.openqa.selenium.remote.{BrowserType, CapabilityType, DesiredCapabilities}

import scala.collection.mutable
import scala.util.Try

object DriverRegistry {

  private val driverClassToCapabilities: mutable.Map[String, java.util.Map[String, Any]] = mutable.Map(
    "org.openqa.selenium.chrome.ChromeDriver" -> new util.HashMap[String, Any] {
      put(CapabilityType.BROWSER_NAME, BrowserType.CHROME)
    },
    "org.openqa.selenium.firefox.FirefoxDriver" -> new util.HashMap[String, Any] {
      put(CapabilityType.BROWSER_NAME, BrowserType.FIREFOX)
    },
    "org.openqa.selenium.ie.InternetExplorerDriver" -> new util.HashMap[String, Any] {
      put(CapabilityType.BROWSER_NAME, BrowserType.IE)
    },
    "org.openqa.selenium.edge.EdgeDriver" -> new util.HashMap[String, Any] {
      put(CapabilityType.BROWSER_NAME, BrowserType.EDGE)
    },
    "org.openqa.selenium.opera.OperaDriver" -> new util.HashMap[String, Any] {
      put(CapabilityType.BROWSER_NAME, BrowserType.OPERA_BLINK)
    },
    "org.openqa.selenium.safari.SafariDriver" -> new util.HashMap[String, Any] {
      put(CapabilityType.BROWSER_NAME, BrowserType.SAFARI)
    },
    "org.openqa.selenium.phantomjs.PhantomJSDriver" -> new util.HashMap[String, Any] {
      put(CapabilityType.BROWSER_NAME, BrowserType.PHANTOMJS)
    },
    "org.openqa.selenium.htmlunit.HtmlUnitDriver" -> new util.HashMap[String, Any] {
      put(CapabilityType.BROWSER_NAME, BrowserType.HTMLUNIT)
    },
    "io.appium.java_client.android.AndroidDriver" -> new util.HashMap[String, Any] {
      put(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID)
    },
    "io.appium.java_client.ios.IOSDriver" -> new util.HashMap[String, Any] {
      put(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS)
    }
  )

  def getDriverClass(capabilities: DesiredCapabilities): Option[Class[_ <: WebDriver]] = {
    val driverClassNames = driverClassToCapabilities.filter(p => capabilities.asMap().entrySet().containsAll(p._2.entrySet())).keys
    Try(Class.forName(driverClassNames.head).asSubclass(classOf[WebDriver])).toOption
  }

  def registerDriverClass(driverClass: String, capabilitiesMatcher: java.util.Map[String, Any]) {
    require(!driverClassToCapabilities.contains(driverClass), s"The driver class has already been registered.")
    driverClassToCapabilities += (driverClass -> capabilitiesMatcher)
  }
}