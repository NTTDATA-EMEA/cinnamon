package io.magentys.cinnamon.webdriver.capabilities

import io.magentys.cinnamon.webdriver.Keys

sealed trait CapabilitiesMapper {

  def toMap: Map[String, Any] = {
    toMap(this)
  }

  //Cleans the options from a Map[String, Any]
  def toMap(cc: AnyRef) = {
    val (option, rest) = CapUtils.getCCs(cc).partition { case (_, x) => x.isInstanceOf[Option[Any]] }
    rest ++ option.collect { case (k, Some(v)) => (k, v) }
  }
}

case class AppiumCapabilities(platformName: String,
                              appiumVersion: Option[String] = None,
                              automationName: Option[String] = Some("Appium"),
                              platformVersion: Option[String] = None,
                              deviceName: Option[String] = None,
                              app: Option[String] = None,
                              browserName: Option[String] = Some(""),
                              newCommandTimeout: Option[Int] = None,
                              language: Option[String] = None,
                              locale: Option[String] = None,
                              udid: Option[String] = None,
                              orientation: Option[String] = None,
                              autoWebview: Option[Boolean] = None,
                              noReset: Option[Boolean] = None,
                              fullReset: Option[Boolean] = None,
                              eventTimings: Option[Boolean] = None,
                              enablePerformanceLogging: Option[Boolean] = None,
                              appActivity: Option[String] = None,
                              appPackage: Option[String] = None,
                              appWaitActivity: Option[String] = None,
                              appWaitPackage: Option[String] = None,
                              appWaitDuration: Option[Long] = None,
                              deviceReadyTimeout: Option[Int] = None,
                              androidCoverage: Option[String] = None,
                              androidDeviceReadyTimeout: Option[Int] = None,
                              androidInstallTimeout: Option[Long] = None,
                              androidInstallPath: Option[String] = None,
                              adbPort: Option[Int] = None,
                              remoteAdbHost: Option[String] = None,
                              androidDeviceSocket: Option[String] = None,
                              avd: Option[String] = None,
                              avdLaunchTimeout: Option[Long] = None,
                              avdReadyTimeout: Option[Long] = None,
                              avdArgs: Option[String] = None, //TODO Check type
                              useKeystore: Option[Boolean] = None,
                              keystorePath: Option[String] = None,
                              keystorePassword: Option[String] = None,
                              keyAlias: Option[String] = None,
                              keyPassword: Option[String] = None,
                              chromedriverExecutable: Option[String] = None,
                              autoWebviewTimeout: Option[Long] = None,
                              intentAction: Option[String] = None,
                              intentCategory: Option[String] = None,
                              intentFlags: Option[String] = None, //TODO Check type
                              optionalIntentArguments: Option[String] = None, //TODO Check type
                              dontStopAppOnReset: Option[Boolean] = None,
                              unicodeKeyboard: Option[Boolean] = None,
                              resetKeyboard: Option[Boolean] = None,
                              noSign: Option[Boolean] = None,
                              ignoreUnimportantViews: Option[Boolean] = None,
                              disableAndroidWatchers: Option[Boolean] = None,
                              chromeOptions: Option[Map[String, AnyRef]] = None,
                              recreateChromeDriverSessions: Option[Boolean] = None,
                              nativeWebScreenshot: Option[Boolean] = None,
                              androidScreenshotPath: Option[String] = None,
                              autoGrantPermissions: Option[Boolean] = None,
                              calendarFormat: Option[String] = None,
                              bundleId: Option[String] = None,
                              launchTimeout: Option[Long] = None,
                              locationServicesEnabled: Option[Boolean] = None,
                              locationServicesAuthorized: Option[Boolean] = None,
                              autoAcceptAlerts: Option[Boolean] = None,
                              autoDismissAlerts: Option[Boolean] = None,
                              nativeInstrumentsLib: Option[Boolean] = None,
                              nativeWebTap: Option[Boolean] = None,
                              safariInitialUrl: Option[String] = None,
                              safariAllowPopups: Option[Boolean] = None,
                              safariIgnoreFraudWarning: Option[Boolean] = None,
                              safariOpenLinksInBackground: Option[Boolean] = None,
                              keepKeyChains: Option[Boolean] = None,
                              localizableStringsDir: Option[String] = None,
                              processArguments: Option[String] = None, //TODO Check type
                              interKeyDelay: Option[Long] = None,
                              showIOSLog: Option[Boolean] = None,
                              acceptSslCerts: Option[Boolean] = Some(true),
                              sendKeyStrategy: Option[String] = None,
                              screenshotWaitTimeout: Option[Int] = None,
                              waitForAppScript: Option[String] = None,
                              webviewConnectRetries: Option[Int] = None,
                              appName: Option[String] = None,
                              customSSLCert: Option[String] = None, //TODO Check type
                              webkitResponseTimeout: Option[Long] = None) extends CapabilitiesMapper {

  require(platformName.nonEmpty, s"platformName is a mandatory field in the configuration profile.")
}

case class SeleniumCapabilities(browserName: String,
                                version: Option[String] = None,
                                platform: Option[String] = None,
                                nativeEvents: Option[Boolean] = None,
                                javascriptEnabled: Option[Boolean] = Some(true),
                                acceptSslCerts: Option[Boolean] = Some(true),
                                properties: Option[Map[String, String]] = None) extends CapabilitiesMapper {

  require(browserName.nonEmpty, s"browserName is a mandatory field in the configuration profile.")

  override def toMap = {
    setSystemProps()
    super.toMap(this).filter(_._1 != Keys.PROPS)
  }

  private[capabilities] def setSystemProps() = {
    this.properties match {
      case Some(props) =>
        props.map { case (k, v) =>
          // System properties take precedence over config values.
          if (!sys.props.contains(k)) System.setProperty(k, v)
        }
      case None => // do nothing
    }
  }
}

private object CapUtils {
  def getCCs(cc: AnyRef) =
    (Map[String, Any]() /: cc.getClass.getDeclaredFields) {
      (a, f) => f.setAccessible(true); a + (f.getName -> f.get(cc))
    }
}