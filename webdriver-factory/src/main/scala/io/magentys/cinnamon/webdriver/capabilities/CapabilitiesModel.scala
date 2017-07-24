package io.magentys.cinnamon.webdriver.capabilities

sealed trait CapabilitiesMapper {

  //Cleans the options from a Map[String, Any]
  def toMap(cc: AnyRef) = {
    val (option, rest) = CapUtils.getCCs(cc).partition { case (_, x) => x.isInstanceOf[Option[Any]] }
    rest ++ option.collect { case (k, Some(v)) => (k, v) }
  }
}

case class BasicCapabilities(browserName: String,
                             version: Option[String] = None,
                             platform: Option[String] = None,
                             nativeEvents: Option[Boolean] = None,
                             javascriptEnabled: Option[Boolean] = Some(true),
                             acceptSslCerts: Option[Boolean] = Some(true),
                             properties: Option[Map[String, String]] = None) extends CapabilitiesMapper {

  require(browserName.nonEmpty, s"browserName is a mandatory field in the configuration profile.")

  def toMap = {
    setSystemProps()
    super.toMap(this).filter(_._1 != "properties")
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