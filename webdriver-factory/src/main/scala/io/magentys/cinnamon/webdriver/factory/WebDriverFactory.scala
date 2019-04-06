package io.magentys.cinnamon.webdriver.factory

import java.net.URL
import java.nio.file.{Files, Paths}

import io.github.bonigarcia.wdm.WebDriverManager
import io.magentys.cinnamon.webdriver.capabilities.DriverBinary
import org.openqa.selenium.remote.{DesiredCapabilities, RemoteWebDriver}
import org.openqa.selenium.{Capabilities, WebDriver}

import scala.util.Try

// helper interface around statics
private[factory] trait WebDriverManagerFactory {
  def driverManagerClass(driverClass: Class[_ <: WebDriver]): WebDriverManager = WebDriverManager.getInstance(driverClass)

  def getDriver(driverClass: Class[_ <: WebDriver], capabilities: DesiredCapabilities): WebDriver = driverClass.getDeclaredConstructor(classOf[Capabilities]).newInstance(capabilities)

  def getRemoteDriver(driverClass: Class[_ <: RemoteWebDriver], capabilities: DesiredCapabilities, hubUrl: String): WebDriver = driverClass.getDeclaredConstructor(classOf[URL], classOf[Capabilities]).newInstance(new URL(hubUrl), capabilities)
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
      return factory.getRemoteDriver(remoteDriverClass, capabilities, hubUrl.get)
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
    factory.getDriver(driverClass, capabilities)
  }
}

object WebDriverFactory {
  def apply() = new WebDriverFactory(new WebDriverManagerFactory {})
}
