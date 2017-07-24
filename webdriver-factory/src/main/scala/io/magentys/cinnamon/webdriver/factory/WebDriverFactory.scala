package io.magentys.cinnamon.webdriver.factory

import java.net.URL
import java.nio.file.{Files, Paths}

import io.github.bonigarcia.wdm.{BrowserManager, WebDriverManager}
import io.magentys.cinnamon.webdriver.capabilities.DriverBinaryConfig
import org.openqa.selenium.WebDriver
import org.openqa.selenium.remote.{DesiredCapabilities, RemoteWebDriver}

import scala.util.Try

// helper interface around statics used in WebDriverManager
private[factory] trait WebDriverManagerFactory {
  def driverManagerClass(driverClass: Class[_ <: WebDriver]): BrowserManager = WebDriverManager.getInstance(driverClass)

  def webDriver(capabilities: DesiredCapabilities): WebDriver = DriverRegistry.locals.newInstance(capabilities)
}

class WebDriverFactory(factory: WebDriverManagerFactory) {

  /**
    * Create a new instance of a web driver
    *
    * @param capabilities the browser capabilities
    * @param hubUrl       optional hub url
    * @param exePath      optional driver exe path
    * @param binaryConfig optional driver binary configuration
    * @return
    */
  def getDriver(capabilities: DesiredCapabilities, hubUrl: Option[String], exePath: Option[String], binaryConfig: Option[DriverBinaryConfig]): WebDriver = {

    // if a hub url has been passed in then ignore WDM and return an instance of remote web driver
    if (hubUrl.isDefined && !hubUrl.get.isEmpty) {
      return new RemoteWebDriver(new URL(hubUrl.get), capabilities)
    }

    val driverClass = DriverRegistry.getDriverClass(capabilities)

    // if an exe path has been defined then check that it exists and use it, otherwise download the exe using the WebdriverManager
    if (exePath.isDefined && !exePath.get.isEmpty) {
      require(Files.exists(Paths.get(exePath.get)), s"Cannot find the exe path that has been set by a webdriver property.")
    } else if (driverClass.isDefined) {
      binaryConfig match {
        case Some(binConfig) => Try(factory.driverManagerClass(driverClass.get).version(binConfig.version).architecture(binConfig.arch).setup())
        case None => Try(factory.driverManagerClass(driverClass.get).setup())
      }
    }
    factory.webDriver(capabilities)
  }
}

object WebDriverFactory {
  def apply() = new WebDriverFactory(new WebDriverManagerFactory {})
}