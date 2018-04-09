package io.magentys.cinnamon.webdriver.factory

import java.net.URL

import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.ios.IOSDriver
import org.openqa.selenium.remote.{DesiredCapabilities, RemoteWebDriver}

object RemoteWebDriverFactory {

  def getRemoteDriver(capabilities: DesiredCapabilities, hubUrl: Option[String]): RemoteWebDriver = {

    if (capabilities.getCapability("platformName").equals("Android")) {
      return new AndroidDriver(new URL(hubUrl.get), capabilities).asInstanceOf[RemoteWebDriver]
    }

    if (capabilities.getCapability("platformName").equals("iOS")) {
      return new IOSDriver(new URL(hubUrl.get), capabilities)
    }

    else {
      return new RemoteWebDriver(new URL(hubUrl.get), capabilities)
    }
  }
}