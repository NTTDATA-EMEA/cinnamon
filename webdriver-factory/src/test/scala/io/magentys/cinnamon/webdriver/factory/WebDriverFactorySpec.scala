package io.magentys.cinnamon.webdriver.factory

import io.github.bonigarcia.wdm.{Architecture, BrowserManager}
import io.magentys.cinnamon.webdriver.capabilities.DriverBinary
import org.mockito.Matchers._
import org.mockito.Mockito._
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.{Platform, WebDriver}
import org.scalatest.mock.MockitoSugar
import org.scalatest.{BeforeAndAfterEach, FunSpec, Matchers}

class WebDriverFactorySpec extends FunSpec with MockitoSugar with Matchers with BeforeAndAfterEach {

  var factoryMock: WebDriverManagerFactory = _
  var browserManagerMock: BrowserManager = _
  var webDriverFactory: WebDriverFactory = _
  val capabilities = DesiredCapabilities.htmlUnit

  override protected def beforeEach(): Unit = {
    factoryMock = mock[WebDriverManagerFactory]
    browserManagerMock = mock[BrowserManager]

    when(factoryMock.driverManagerClass(any[Class[_ <: WebDriver]]))
      .thenReturn(browserManagerMock)
    when(browserManagerMock.architecture(any[Architecture])).thenReturn(browserManagerMock)
    when(browserManagerMock.version(any[String])).thenReturn(browserManagerMock)

    webDriverFactory = new WebDriverFactory(factoryMock)
  }

  describe("WebDriverFactory") {
    describe("getDriver()") {
      it("checks whether the driver class is registered") {
        intercept[Exception] {
          webDriverFactory.getDriver(new DesiredCapabilities("unregistered", "", Platform.ANY), None, None, None)
        }
      }

      it("checks the exe path exists when it is supplied") {
        intercept[IllegalArgumentException] {
          webDriverFactory.getDriver(capabilities, None, Some("/nonexistent/path"), None)
        }
      }

      it("checks the exe path is empty when it is supplied") {
        intercept[IllegalArgumentException] {
          webDriverFactory.getDriver(capabilities, None, Some(""), None)
        }
      }

      it("calls WebDriverManager.setup() when no binary config supplied") {
        webDriverFactory.getDriver(capabilities, None, None, None)
        verify(browserManagerMock).setup()
      }

      it("calls WebDriverManager.version().architecture().setup() when driver binary config supplied") {
        val driverBinary = DriverBinary("2.51", Architecture.x32)
        webDriverFactory.getDriver(capabilities, None, None, Some(driverBinary))
        verify(browserManagerMock).version("2.51")
        verify(browserManagerMock).architecture(Architecture.x32)
        verify(browserManagerMock).setup()
      }

      it("does not use WebDriverManager if exePath exists") {
        webDriverFactory.getDriver(capabilities, None, Some("."), None)
        verify(factoryMock, never()).driverManagerClass(any())
      }
    }
  }
}

