package com.gmail.at.pukanito.model.attributes

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers
import com.gmail.at.pukanito.model.container.GraphPath

class AttributeDefinitionTest extends FunSpec with ShouldMatchers {

  describe("AttributeDefinition") {

    it("should have an identifier equal to the one specified at creation") {
      val d = new AttributeDefinition("testid")
      d.attributeId should equal (new AttributeIdentifier("testid"))
    }

    it("should not be possible to modify the identifier") {
      // implicit by making attributeId a 'val'
    }

    it("should be possible to get attribute definitions by simple id") {
      val d1 = new AttributeDefinition("testid1")
      val d2 = new AttributeDefinition("testid2")
      d1 += d2
      d1("testid2") should be theSameInstanceAs (d2)
    }

  }
}
