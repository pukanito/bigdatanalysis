package com.gmail.at.pukanito.controller.config

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

class ConfigurationTest extends FunSpec with ShouldMatchers {

  describe("Configuration") {

    it("should find the highest prioritized path") {
      new Configuration() whichPath("test") should equal (Some("test"))
      new Configuration() whichPath("test.foo") should equal (Some("test.foo"))
      new Configuration() whichPath("test.bar") should equal (Some("test.bar"))
      new Configuration() whichPath("test.foo.bar") should equal (Some("test.foo.bar"))
      new Configuration() whichPath("foo.bar") should equal (Some("foo.bar"))
      new Configuration() whichPath("spec.bar") should equal (Some("spec.bar"))

      new Configuration("test") whichPath("foo") should equal (Some("test.foo"))
      new Configuration("test") whichPath("foo.bar") should equal (Some("test.foo.bar"))
      new Configuration("test") whichPath("bar") should equal (Some("test.bar"))
      new Configuration("test") whichPath("test.foo.bar") should equal (Some("test.foo.bar"))

      new Configuration("") whichPath("foo", "test") should equal (Some("test.foo"))
      new Configuration("") whichPath("foo.bar", "test") should equal (Some("test.foo.bar"))
      new Configuration("") whichPath("bar", "test") should equal (Some("test.bar"))
      new Configuration("") whichPath("test.foo.bar", "test") should equal (Some("test.foo.bar"))

      new Configuration("test") whichPath("bar", "foo") should equal (Some("test.foo.bar"))
      new Configuration("test") whichPath("foo", "bar") should equal (Some("test.foo"))
      new Configuration("test") whichPath("bar", "spec") should equal (Some("spec.bar"))
      new Configuration("test") whichPath("bar", "spec2") should equal (Some("test.bar"))
      new Configuration("test") whichPath("test.foo.bar", "spec") should equal (Some("test.foo.bar"))
    }

    it("should be possible to retrieve default and specific values") {
      new Configuration("test") getString("val1") should equal ("VAL1")
      new Configuration("test") getString("val1", "spec1") should equal ("VAL1SPEC1")
      new Configuration("test") getString("val1", "spec2") should equal ("VAL1SPEC2")
      new Configuration("test") getString("val1", "spec3") should equal ("VAL1")
    }

    it("should distinguish test and runtime configuration") {
      new Configuration("configuration") getString("description") should equal ("Application configuration")
      new Configuration("configuration") getString("description", "test") should equal ("Testing configuration")
    }

  }

}