package io.magentys.cinnamon.webdriver.capabilities

import com.typesafe.config.ConfigFactory
import io.github.bonigarcia.wdm.Architecture
import org.scalatest.{BeforeAndAfterEach, FunSpec, Matchers}

class DriverConfigSpec extends FunSpec with Matchers with BeforeAndAfterEach {

  val config = ConfigFactory.load("defaults-test.conf")

  describe("DriverConfig") {
    describe("When driverExtras config is present") {
      val driverConfig = DriverConfig("ie", config, "")

      it("has driverExtras settings applied to the capabilities") {
        driverConfig.desiredCapabilities.getCapability("unexpectedAlertBehaviour") shouldBe "dismiss"
        driverConfig.desiredCapabilities.getCapability("ie.ensureCleanSession") shouldBe true
        driverConfig.desiredCapabilities.getCapability("ignoreZoomSetting") shouldBe true
        driverConfig.desiredCapabilities.getCapability("requireWindowFocus") shouldBe true
        driverConfig.desiredCapabilities.getCapability("enablePersistentHover") shouldBe false
      }
    }

    describe("When driver binary config is present") {
      val driverConfig = DriverConfig("ie", config, "")
      val binaryConfig = driverConfig.binaryConfig.get

      it("has binary config if present in conf file") {
        assert(driverConfig.binaryConfig.isDefined)
      }

      it("has the correct values as defined in the config file") {
        assert(binaryConfig.arch == Architecture.x32)
        assert(binaryConfig.version == "2.51")
      }
    }

    describe("When driver binary config is not present") {
      val driverConfig = DriverConfig("firefox", config, "")
      it("does not have any driver binary configuration") {
        assert(driverConfig.binaryConfig.isEmpty)
      }
    }
  }
}
