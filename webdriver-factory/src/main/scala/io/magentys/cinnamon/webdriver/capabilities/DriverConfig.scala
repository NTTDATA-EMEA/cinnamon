package io.magentys.cinnamon.webdriver.capabilities

import com.typesafe.config.Config
import io.github.bonigarcia.wdm.Architecture
import io.magentys.cinnamon.webdriver.Keys
import io.magentys.cinnamon.webdriver.remote.{CinnamonRemote, RemoterDetector}
import net.ceedubs.ficus.Ficus._
import net.ceedubs.ficus.readers.ArbitraryTypeReader._
import org.openqa.selenium.remote.{BrowserType, DesiredCapabilities}

import scala.collection.JavaConverters._
import scala.util.Try

case class DriverBinary(version: String, arch: Architecture)

case class DriverConfig(desiredCapabilities: DesiredCapabilities,
                        requiresMoveMouse: Boolean,
                        exePath: Option[String] = None,
                        driverBinary: Option[DriverBinary] = None) {
}

object DriverConfig {
  /**
    * This class is responsible to define the webdriver config based on user options.
    * It combines Capabilities whether it's remote or not. Will automatically identify if remote is required,
    * based on whether the user has passed a hubUrl.
    *
    * @param browserProfile user's selected browserProfile from capabilities-profiles
    * @param combinedConfig combined config should be passed in order to bind the correct object based on the selected browserProfile
    * @param hubUrl         if this is defined remote capabilities will also be added
    * @return DriverConfig object
    */
  def apply(browserProfile: String, combinedConfig: Config, hubUrl: String): DriverConfig = {

    //1. Get the Capabilities profile config from the combined config.
    val capabilitiesProfiles = combinedConfig.getConfig(Keys.CAPABILITIES_PROFILES)

    //2. Load the basic capabilities.
    val basicCapabilities = Try(capabilitiesProfiles.as[AppiumCapabilities](browserProfile)).getOrElse(capabilitiesProfiles.as[SeleniumCapabilities](browserProfile))
    val basicCaps = new DesiredCapabilities(basicCapabilities.toMap.asJava)

    //3. Bind the driver extras.
    val extraCapabilities = {
      val driverExtras = getDriverExtras(browserProfile, combinedConfig)
      DriverExtrasBinder.bindExtrasMap(basicCaps.getBrowserName, driverExtras)
    }
    val extraCaps = new DesiredCapabilities(extraCapabilities.getCapabilityMap.asJava)

    //4. Merge them all, adding remotes if required.
    val capabilities: DesiredCapabilities = {
      if (remoteCapabilitiesRequired(hubUrl)) basicCaps.merge(extraCaps).merge(remoteCapabilities(browserProfile, combinedConfig, hubUrl))
      else basicCaps.merge(extraCaps)
    }

    //5. Set the binaryConfig. Skip if a webdriver.*.property has been set in the config or via the command line.
    val browserConfig = capabilitiesProfiles.getConfig(browserProfile)
    val exePath = getExePath(capabilities)
    if (exePath.isEmpty && browserConfig.hasPath(Keys.DRIVER_BINARY)) {
      val binaryConfig = browserConfig.getConfig(Keys.DRIVER_BINARY)
      val version = binaryConfig.hasPath("version") match {
        case true => binaryConfig.getString("version")
        case false => "LATEST"
      }

      // Determine the architecture version.
      val archVersions = Map("32" -> Architecture.X32, "64" -> Architecture.X64)
      val arch = binaryConfig.hasPath("arch") match {
        case true => archVersions.getOrElse(binaryConfig.getString("arch"), Architecture.DEFAULT)
        case false => Architecture.DEFAULT
      }
      DriverConfig(capabilities, extraCapabilities.requiresMoveMouse, None, Some(DriverBinary(version, arch)))
    } else if (exePath.isDefined) {
      DriverConfig(capabilities, extraCapabilities.requiresMoveMouse, exePath)
    } else {
      DriverConfig(capabilities, extraCapabilities.requiresMoveMouse)
    }
  }

  private[capabilities] def getExePath(desiredCapabilities: DesiredCapabilities): Option[String] = {
    desiredCapabilities.getBrowserName match {
      case BrowserType.CHROME => Option(sys.props("webdriver.chrome.driver"))
      case BrowserType.FIREFOX => Option(sys.props("webdriver.gecko.driver"))
      case BrowserType.EDGE => Option(sys.props("webdriver.edge.driver"))
      case BrowserType.PHANTOMJS => Option(sys.props("phantomjs.binary.path"))
      case BrowserType.IE => Option(sys.props("webdriver.ie.driver"))
      case _ => None
    }
  }

  private[capabilities] def getDriverExtras(browserProfile: String, config: Config): Map[String, AnyRef] = {
    val driverExtras = Try(config.getConfig(Keys.CAPABILITIES_PROFILES + "." + browserProfile + "." + Keys.DRIVER_EXTRAS_KEY)).toOption
    if (driverExtras.isDefined) {
      configToMap(driverExtras.get)
    } else {
      Map.empty
    }
  }

  private[capabilities] def configToMap(c: Config): Map[String, AnyRef] = {
    c.entrySet.asScala.map(f => (f.getKey, f.getValue.unwrapped())).toMap
  }

  private[capabilities] def remoteCapabilitiesRequired(hubUrl: String): Boolean = hubUrl != null && hubUrl.nonEmpty

  private[capabilities] def remoteCapabilities(userProfile: String, config: Config, url: String): DesiredCapabilities = {
    val remotersMatched: List[CinnamonRemote] = RemoterDetector.getRemoterMatchesURL(url)
    remotersMatched.size match {
      case 1 => remotersMatched.head.capabilities(userProfile, config)
      case x if x > 1 => throw new Exception("More than one remoter found with url: [" + url + "]")
      case _ => new DesiredCapabilities() // Defaults to selenium grid and provides empty capabilities to merge
    }
  }
}