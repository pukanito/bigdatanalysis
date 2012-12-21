package com.gmail.at.pukanito.model.attributes

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

class AttributeIdentifierTest extends FunSpec with ShouldMatchers {

  describe("AttributeIdentifier") {

    it("should contain valid characters only") {
      intercept[IllegalArgumentException] { new AttributeIdentifier("!illegal") }
      intercept[IllegalArgumentException] { new AttributeIdentifier("ill!egal") }
      intercept[IllegalArgumentException] { new AttributeIdentifier("1illegal") }
    }

  }

}