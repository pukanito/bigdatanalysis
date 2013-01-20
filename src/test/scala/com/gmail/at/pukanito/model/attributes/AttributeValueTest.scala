package com.gmail.at.pukanito.model.attributes

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

class AttributeValueTest extends FunSpec with ShouldMatchers {

  private class AttributeTestValue(
    attributeDefinition: AttributeDefinition
  ) extends AttributeValue(attributeDefinition) {

    def value: Any = { 0 }
  }

  describe("AttributeValue") {

    it("should be associated with an attribute definition") {
      val d = new AttributeDefinition("testid")
      val v: AttributeValue = new AttributeTestValue(d)
      v.attributeDefinition should be theSameInstanceAs (d)
    }

  }
}
