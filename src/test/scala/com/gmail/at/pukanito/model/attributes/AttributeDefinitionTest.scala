package com.gmail.at.pukanito.model.attributes

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

class AttributeDefinitionTest extends FunSpec with ShouldMatchers {

  describe("AttributeDefinition") {

    it("should have an identifier equal to the one specified at creation") {
      val d = new AttributeDefinition("testid")
      d.attributeId should equal (new AttributeIdentifier("testid"))
    }

    it("should not be possible to modify the identifier") {
      // implicit by making attributeId a 'val' -> compiler error
    }

  }

}
