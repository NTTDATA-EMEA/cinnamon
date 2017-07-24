package io.magentys.cinnamon.webdriver.factory

import io.github.bonigarcia.wdm.{Architecture, BrowserManager}
import io.magentys.cinnamon.webdriver.capabilities.DriverBinaryConfig
import org.mockito.Matchers._
import org.mockito.Mockito._
import org.openqa.selenium.WebDriver
import org.openqa.selenium.remote.DesiredCapabilities
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
      it("checks the exe path exists when it is supplied") {
        intercept[IllegalArgumentException] {
          webDriverFactory.getDriver(capabilities, None, Some("/nonexistent/path"), None)
        }
      }

      it("calls WebDriverManager.setup() when no binary config supplied") {
        webDriverFactory.getDriver(capabilities, None, None, None)
        verify(browserManagerMock).setup()
      }

      it("calls WebDriverManager.version().architecture().setup() when binary config supplied") {
        val binaryConfig = DriverBinaryConfig("2.51", Architecture.x32)
        webDriverFactory.getDriver(capabilities, None, None, Some(binaryConfig))
        verify(browserManagerMock).version("2.51")
        verify(browserManagerMock).architecture(Architecture.x32)
        verify(browserManagerMock).setup()
      }

      it("calls WebDriverFactory.webDriver()") {
        webDriverFactory.getDriver(capabilities, None, None, None)
        verify(factoryMock).webDriver(capabilities)
      }

      it("does not use WebDriverManager if driver class is unknown") {
        webDriverFactory.getDriver(mock[DesiredCapabilities], None, None, None)
        verify(factoryMock, never()).driverManagerClass(any())
      }

      it("does not use WebDriverManager if exePath exists") {
        webDriverFactory.getDriver(mock[DesiredCapabilities], None, Some(""), None)
        verify(factoryMock, never()).driverManagerClass(any())
      }
    }
  }

}
