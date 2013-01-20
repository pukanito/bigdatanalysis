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
      assert(v.attributeDefinition eq d)
    }

    it("can be a root attribute, i.e. has no parent attribute values") {
      val d = new AttributeDefinition("testid")
      val v: AttributeValue = new AttributeTestValue(d)
      assert(v.parents.isEmpty)
    }

    it("can be associated with one or more parent attribute values") {
      val v1: AttributeValue = new AttributeTestValue(new AttributeDefinition("test1id"))
      val v2: AttributeValue = new AttributeTestValue(new AttributeDefinition("test2id"))
      val p1: AttributeValue = new AttributeTestValue(new AttributeDefinition("p1id"))
      val p2: AttributeValue = new AttributeTestValue(new AttributeDefinition("p2id"))
      p1 += v1
      p2 += v1
      p2 += v2
      expect(2) { v1.parents.size }
      expect(1) { v2.parents.size }
      expect(0) { p1.parents.size }
      expect(0) { p2.parents.size }
      expect(0) { v1.children.size }
      expect(0) { v2.children.size }
      expect(1) { p1.children.size }
      expect(2) { p2.children.size }
//      assert(p1.children("test1id") eq v1)
//      assert(p2.children("test1id") eq v1)
//      assert(p2.children("test2id") eq v2)
      (pending)
      assert(v1.parents contains p1)
      assert(v1.parents contains p2)
      assert(v2.parents contains p2)
    }

  }
}
