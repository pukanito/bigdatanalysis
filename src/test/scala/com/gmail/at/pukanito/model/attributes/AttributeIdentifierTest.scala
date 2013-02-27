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

    it("should compare different instances with the same id to be equal") {
      val legalId = "legalId"
      new AttributeIdentifier(legalId) should equal (new AttributeIdentifier(legalId))
    }

    it("should compare different instances with the different id to not be equal") {
      val legalId = "legalId"
      val otherLegalId = "otherLegalId"
      new AttributeIdentifier(legalId) should not equal (new AttributeIdentifier(otherLegalId))
    }

    it("should convert from String implicitely") {
      def testConvert(id: AttributeIdentifier) {
    	  new AttributeIdentifier("XXX") should equal (id)
      }
      testConvert("XXX")
    }

  }

}