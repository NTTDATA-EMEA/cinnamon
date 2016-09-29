package io.magentys.cinnamon.webdriver.factory

import java.net.URL

import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.{Capabilities, WebDriver}

import scala.util.Try

object WebDriverFactory {

  def getDriver(capabilities: Capabilities, hubURL: String = ""): WebDriver = {
    if (hubURL != null && hubURL.trim.nonEmpty) createRemoteWebDriver(capabilities, hubURL)
    else createWebDriver(capabilities)
  }

  private def createWebDriver(capabilities: Capabilities): WebDriver = {
    val driverClass = DriverRegistry.getDriverClass(capabilities)
    if (driverClass.isDefined) Try(WebDriverManager.getInstance(driverClass.get).setup())
    DriverRegistry.locals.newInstance(capabilities)
  }

  private def createRemoteWebDriver(capabilities: Capabilities, hubURL: String): WebDriver =
    new RemoteWebDriver(new URL(hubURL), capabilities)
}