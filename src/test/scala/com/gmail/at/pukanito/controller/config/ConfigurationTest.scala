package com.gmail.at.pukanito.controller.config

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

class ConfigurationTest extends FunSpec with ShouldMatchers {

  describe("Configuration") {

    it("should find the highest prioritized path") {
      new Configuration() whichPath("test") should equal (Some("test"))
      new Configuration() whichPath("test.test") should equal (Some("test.test"))
      new Configuration() whichPath("test.bar") should equal (Some("test.bar"))
      new Configuration() whichPath("test.test.bar") should equal (Some("test.test.bar"))

      new Configuration("test") whichPath("test") should equal (Some("test.test"))
      new Configuration("test") whichPath("test.test") should equal (Some("test.test"))
      new Configuration("test") whichPath("test.bar") should equal (Some("test.test.bar"))
      new Configuration("test") whichPath("test.test.bar") should equal (Some("test.test.bar"))

      new Configuration("") whichPath("test", "test") should equal (Some("test.test"))
      new Configuration("") whichPath("test.test", "test") should equal (Some("test.test"))
      new Configuration("") whichPath("test.bar", "test") should equal (Some("test.test.bar"))
      new Configuration("") whichPath("test.test.bar", "test") should equal (Some("test.test.bar"))

      new Configuration("test") whichPath("bar", "test") should equal (Some("test.test.bar"))
      new Configuration("test") whichPath("test.test", "bar") should equal (Some("test.test"))
      new Configuration("test") whichPath("test.bar", "bar") should equal (Some("test.test.bar"))
      new Configuration("test") whichPath("test.test.bar", "bar") should equal (Some("test.test.bar"))
    }

    it("should be possible to retrieve default and specific values") {
      new Configuration("test") getString("val1") should equal ("VAL1")
      new Configuration("test") getString("val1", "spec1") should equal ("VAL1SPEC1")
      new Configuration("test") getString("val1", "spec2") should equal ("VAL1SPEC2")
      new Configuration("test") getString("val1", "spec3") should equal ("VAL1")
    }

  }

}