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
                              autoWebview: Option[Boolean] = Some(false),
                              noReset: Option[Boolean] = None,
                              fullReset: Option[Boolean] = None,
                              eventTimings: Option[Boolean] = Some(false),
                              enablePerformanceLogging: Option[Boolean] = Some(false)) extends CapabilitiesMapper {

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