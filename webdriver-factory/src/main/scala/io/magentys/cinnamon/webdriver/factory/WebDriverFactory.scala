package io.magentys.cinnamon.webdriver.factory

import java.net.URL
import java.nio.file.{Files, Paths}

import io.github.bonigarcia.wdm.{BrowserManager, WebDriverManager}
import io.magentys.cinnamon.webdriver.capabilities.DriverBinary
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.{Capabilities, WebDriver}

import scala.util.Try

// helper interface around statics used in WebDriverManager
private[factory] trait WebDriverManagerFactory {
  def driverManagerClass(driverClass: Class[_ <: WebDriver]): BrowserManager = WebDriverManager.getInstance(driverClass)
}

class WebDriverFactory(factory: WebDriverManagerFactory) {

  /**
    * Create a new instance of a web driver
    *
    * @param capabilities the browser capabilities
    * @param hubUrl       optional hub url
    * @param exePath      optional driver exe path
    * @param driverBinary optional driver binary configuration
    * @return
    */
  def getDriver(capabilities: DesiredCapabilities, hubUrl: Option[String], exePath: Option[String], driverBinary: Option[DriverBinary]): WebDriver = {

    if (hubUrl.isDefined && !hubUrl.get.isEmpty) {
      val remoteDriverClass = DriverRegistry.getRemoteDriverClass(capabilities) match {
        case Some(clazz) => clazz
        case None => throw new Exception("Cannot find the remote driver class in the driver registry.")
      }
      return remoteDriverClass.getDeclaredConstructor(classOf[URL], classOf[Capabilities]).newInstance(new URL(hubUrl.get), capabilities)
    }

    val driverClass = DriverRegistry.getDriverClass(capabilities) match {
      case Some(clazz) => clazz
      case None => throw new Exception("Cannot find the driver class in the driver registry.")
    }

    // If an exe path has been defined then check that it exists and use it, otherwise download the exe using the WebdriverManager
    if (exePath.isDefined) {
      require(exePath.get.nonEmpty && Files.exists(Paths.get(exePath.get)), s"Cannot find the exe path that has been set by a webdriver property.")
    } else {
      driverBinary match {
        case Some(binary) => Try(factory.driverManagerClass(driverClass).version(binary.version).architecture(binary.arch).setup())
        case None => Try(factory.driverManagerClass(driverClass).setup())
      }
    }
    driverClass.getDeclaredConstructor(classOf[Capabilities]).newInstance(capabilities)
  }

}

object WebDriverFactory {
  def apply() = new WebDriverFactory(new WebDriverManagerFactory {})
}
