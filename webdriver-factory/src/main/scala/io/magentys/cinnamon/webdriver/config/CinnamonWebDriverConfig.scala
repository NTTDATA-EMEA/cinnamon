package io.magentys.cinnamon.webdriver.config

import java.io.{FileNotFoundException, File}

import com.typesafe.config.{ConfigFactory, Config}
import io.magentys.cinnamon.webdriver.Keys
import io.magentys.cinnamon.webdriver.capabilities.DriverConfig

import scala.util.Try
/**
  * Three types of variables are expected to be passed by the user with regards to config:
  *
  * 1. browserProfile - refers to the capabilities profile the user wants to execute
  *                     and should be wrapped in capabilities-profiles.
  *
  * 2. hubUrl - this is only required for remote driver connections.
  *             In case it is empty, we default to "" which implies a local driver.
  *
  * 3. webdriverConfig - this allows the user to pass a config file from outside the project.
  *                      In case it is empty, we search in the project directories.
  *
  * The config file should be named or end with "webdriver.conf"
  */
object CinnamonWebDriverConfig {

  val defaultCinnamonConfig = ConfigFactory.load(Keys.DEFAULTS)

  val config: Config = {

    val configFile: Option[File] = {
      val externalFile: String = Try(defaultCinnamonConfig.getString(Keys.WEBDRIVER_CONFIG)).toOption.getOrElse("")

      Try(externalFile match {
        case "" => findTheConfigFileInClasspath
        case x => new File(externalFile)
      }).toOption
    }

    if (configFile.isDefined) {
      val userConfig = ConfigFactory.parseFile(configFile.get).resolve()
      userConfig.withFallback(defaultCinnamonConfig)
    } else {
      defaultCinnamonConfig
    }

  }

  val hubUrl = Try(config.getString(Keys.HUB_URL)).toOption.getOrElse("")

  val driverConfig = {
    val browserProfile: String = config.getString(Keys.BROWSER_PROFILE)
    DriverConfig(browserProfile, config, hubUrl, defaultCinnamonConfig) //All user input here makes the config
  }

  private def listFiles(base: File, recursive: Boolean = true): Seq[File] = {
    val files = base.listFiles
    val result = files.filter(_.isFile)
    val allFiles = result ++ files
      .filter(f => f.isDirectory && !f.getAbsolutePath.contains("target"))
      .filter(_ => recursive)
      .flatMap(listFiles(_, recursive))

    allFiles.filter(_.getName.endsWith(Keys.CONFIG_FILE_EXTENSION))
  }

  private def findTheConfigFileInClasspath: File = {
    val projectDir = System.getProperty("project.dir", ".")
    val files = listFiles(new File(projectDir))
    files.size match {
      case 0 => throw new FileNotFoundException("No config file found. Your file should be named or end with webdriver.conf")
      case file if file > 1  => throw new Exception("More than one file found ending with webdriver.conf")
      case file if file == 1  => files.head
    }
  }

}
