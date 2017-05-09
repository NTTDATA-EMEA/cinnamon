package io.magentys.cinnamon.webdriver.factory

import java.net.URL

import io.github.bonigarcia.wdm.{BrowserManager, WebDriverManager}
import io.magentys.cinnamon.webdriver.capabilities.DriverBinaryConfig
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.WebDriver

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
    * @param binaryConfig optional driver binary configuration
    * @return
    */
  def getDriver(capabilities: DesiredCapabilities, hubUrl: Option[String], binaryConfig: Option[DriverBinaryConfig]): WebDriver = {

    // if a hub url has been passed in then ignore WDM and return an instance of remote web driver
    if (hubUrl.isDefined && !hubUrl.get.isEmpty) {
      return new RemoteWebDriver(new URL(hubUrl.get), capabilities)
    }

    val driverClass = DriverRegistry.getDriverClass(capabilities)

    if (driverClass.isDefined) {
      binaryConfig match {
        case Some(binConfig) => Try(factory.driverManagerClass(driverClass.get).setup(binConfig.arch, binConfig.version))
        case None => Try(factory.driverManagerClass(driverClass.get).setup())
      }
    }

    factory.webDriver(capabilities)
  }

}

object WebDriverFactory {
  def apply() = new WebDriverFactory(new WebDriverManagerFactory {})
}