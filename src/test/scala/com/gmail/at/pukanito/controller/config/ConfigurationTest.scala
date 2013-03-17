package com.gmail.at.pukanito.controller.config

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

class ConfigurationTest extends FunSpec with ShouldMatchers {

  describe("Configuration") {

    it("should find the highest prioritized path") {
      new Configuration() whichPath("foo") should equal (None)
      new Configuration() whichPath("test.foo") should equal (Some("test.foo"))
      new Configuration() whichPath("foo.bar") should equal (None)
      new Configuration() whichPath("test.foo.bar") should equal (Some("test.foo.bar"))
      new Configuration("test") whichPath("foo") should equal (Some("test.foo"))
      new Configuration("test") whichPath("test.foo") should equal (Some("test.foo"))
      new Configuration("test") whichPath("foo.bar") should equal (Some("test.foo.bar"))
      new Configuration("test") whichPath("test.foo.bar") should equal (Some("test.foo.bar"))
      new Configuration("", "bar") whichPath("foo") should equal (None)
      new Configuration("", "bar") whichPath("test.foo") should equal (Some("test.foo.bar"))
      new Configuration("", "bar") whichPath("foo.bar") should equal (None)
      new Configuration("", "bar") whichPath("test.foo.bar") should equal (Some("test.foo.bar"))
      new Configuration("test", "bar") whichPath("foo") should equal (Some("test.foo.bar"))
      new Configuration("test", "bar") whichPath("test.foo") should equal (Some("test.foo.bar"))
      new Configuration("test", "bar") whichPath("foo.bar") should equal (Some("test.foo.bar"))
      new Configuration("test", "bar") whichPath("test.foo.bar") should equal (Some("test.foo.bar"))
    }

    it("should be possible to retrieve default values") { (pending)
    }

    it("should be possible to retrieve specific values") (pending)

  }

}