package com.gmail.at.pukanito.controller.config

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

class ConfigurationTest extends FunSpec with ShouldMatchers {

  describe("Configuration") {

    it("should be possible to retrieve default values") {
      Configuration.hasPath("foo.bar") should be (false)
      Configuration.hasPath("foo.bar", "test") should be (true)
    }

    it("should be possible to retrieve specific values") (pending)

  }

}