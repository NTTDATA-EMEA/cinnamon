package io.magentys.cinnamon.webdriver.capabilities

import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.{FirefoxDriver, FirefoxProfile}
import org.openqa.selenium.safari.SafariOptions

import scala.collection.JavaConverters._

object DriverExtrasBinder {

  /**
    * Cinnamon provides some default profiles for the known browsers.
    * In case of a different browser no need to load any specific default
    * hence we just apply an empty driver extras object.
    * Note: the browserName needs to match exactly the default ones if inheritance is required.
    *
    * @param browserName
    * @param driverExtrasMap
    * @return DriverExtras
    */
  def bindExtrasMap(browserName: String, driverExtrasMap: Map[String, AnyRef]) = {
    browserName match {
      case "internet explorer" => InternetExplorerExtras(driverExtrasMap)
      case "chrome" => ChromeExtras(driverExtrasMap)
      case "firefox" => FirefoxExtras(driverExtrasMap)
      case "safari" => SafariExtras(driverExtrasMap)
      case _ => DefaultExtras(driverExtrasMap)
    }
  }
}

sealed trait DriverExtras {
  def getCapabilityMap: Map[String, Any]

  def requiresMoveMouse: Boolean
}

/** Internet Explorer */
case class InternetExplorerExtras(driverExtras: Map[String, Any]) extends DriverExtras {

  override def getCapabilityMap: Map[String, Any] = this.driverExtras

  override def requiresMoveMouse: Boolean = {
    driverExtras("enablePersistentHover").asInstanceOf[Boolean] ||
      !driverExtras("requireWindowFocus").asInstanceOf[Boolean]
  }
}

/** Firefox */
case class FirefoxExtras(driverExtras: Map[String, Any]) extends DriverExtras {

  override def getCapabilityMap: Map[String, Any] = {
    val firefoxProfile = new FirefoxProfile()

    this.driverExtras.foreach { case (k, v) =>
      v match {
        case value: Boolean => firefoxProfile.setPreference(k, value.asInstanceOf[Boolean])
        case value: String => firefoxProfile.setPreference(k, value.asInstanceOf[String])
        case value: Integer => firefoxProfile.setPreference(k, value.asInstanceOf[Integer])
      }
    }
    Map(FirefoxDriver.PROFILE -> firefoxProfile)
  }

  override def requiresMoveMouse: Boolean = false
}

/** Chrome */
case class ChromeExtras(driverExtras: Map[String, Any]) extends DriverExtras {

  override def getCapabilityMap: Map[String, Any] = {
    Map(ChromeOptions.CAPABILITY -> driverExtras.asJava)
  }

  override def requiresMoveMouse: Boolean = true
}

/** Safari */
case class SafariExtras(driverExtras: Map[String, Any]) extends DriverExtras {

  override def getCapabilityMap: Map[String, Any] = {
    Map(SafariOptions.CAPABILITY -> driverExtras.asJava)
  }

  override def requiresMoveMouse: Boolean = false
}

/** Default */
case class DefaultExtras(driverExtras: Map[String, Any]) extends DriverExtras {

  override def getCapabilityMap: Map[String, Any] = Map.empty

  override def requiresMoveMouse: Boolean = false
}