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
      val driverBinary = driverConfig.driverBinary.get

      it("has driver binary config if present in conf file") {
        driverConfig.driverBinary.isDefined shouldBe true
      }

      it("has the correct values as defined in the config file") {
        driverBinary.arch shouldBe Architecture.X32
        driverBinary.version shouldBe "2.51"
      }
    }

    describe("When driver binary config is not present") {
      val driverConfig = DriverConfig("firefox", config, "")
      it("does not have any driver binary configuration") {
        driverConfig.driverBinary.isEmpty shouldBe true
      }
    }

    describe("When both a webdriver.*.driver property and binary config are present in the config file") {
      val driverConfig = DriverConfig("edge", config, "")
      it("does not have any driver binary configuration") {
        driverConfig.driverBinary.isEmpty shouldBe true
      }

      it("sets the webdriver.*.property to the value in the config file") {
        driverConfig.exePath.get shouldBe "/edge/path"
      }
    }

    describe("When a webdriver.*.driver property is set in both the config file and as a system property") {
      try {
        System.setProperty("webdriver.edge.driver", "/another/path")
        val driverConfig = DriverConfig("edge", config, "")
        it("does not have any driver binary configuration") {
          driverConfig.driverBinary.isEmpty shouldBe true
        }

        it("sets the webdriver.*.property to the value of the system property") {
          driverConfig.exePath.get shouldBe "/another/path"
        }
      } finally {
        System.clearProperty("webdriver.edge.driver")
      }
    }

    describe("When a webdriver.*.driver property is only set as a system property") {
      try {
        System.setProperty("webdriver.gecko.driver", "/another/path")
        val driverConfig = DriverConfig("firefox", config, "")
        it("does not have any driver binary configuration") {
          driverConfig.driverBinary.isEmpty shouldBe true
        }

        it("sets the webdriver.*.property to the value of the system property") {
          driverConfig.exePath.get shouldBe "/another/path"
        }
      } finally {
        System.clearProperty("webdriver.gecko.driver")
      }
    }
  }
}
