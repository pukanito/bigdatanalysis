package com.gmail.at.pukanito.model.attributes

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

class AttributeDefinitionTest extends FunSpec with ShouldMatchers {

  describe("AttributeDefinition") {

    it("should have a valid identifier") {
      intercept[IllegalArgumentException] { new AttributeDefinition("!illegal") }
      intercept[IllegalArgumentException] { new AttributeDefinition("ill!egal") }
      intercept[IllegalArgumentException] { new AttributeDefinition("1illegal") }
    }

    it("should have an identifier equal to the one specified at creation") {
      val d = new AttributeDefinition("testid")
      d.attributeId should equal ("testid")
    }

    it("should not be possible to modify the identifier") {
      // implicit by making attributeId a 'val'
    }

  }
}
