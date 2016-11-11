package io.magentys.cinnamon.webdriver.capabilities

import com.typesafe.config.{Config, ConfigFactory}
import io.github.bonigarcia.wdm.Architecture
import org.scalatest.mock.MockitoSugar
import org.scalatest.{BeforeAndAfterEach, FunSpec, Matchers}

class DriverConfigSpec extends FunSpec with MockitoSugar with Matchers with BeforeAndAfterEach {

  val config = ConfigFactory.load("defaults-test.conf")
  val defaultConfig = mock[Config]

  describe("DriverConfig") {
    describe("When driver binary config is present") {
      val driverConfig = DriverConfig("ie", config, "", config)
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
      val driverConfig = DriverConfig("firefox", config, "", config)
      it("does not have any driver binary configuration") {
        assert(driverConfig.binaryConfig.isEmpty)
      }
    }
  }
}
