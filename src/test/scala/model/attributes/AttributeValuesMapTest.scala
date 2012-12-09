package model.attributes

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

class AttributeValuesMapTest extends FunSpec with ShouldMatchers {

  private class AttributeTestValue(
    attributeDefinition: AttributeDefinition
  ) extends AttributeValue(attributeDefinition) {

    def value: Any = { 0 }
  }

  describe("AttributeValuesMap") {

    it("should be possible to retrieve an existing attribute value by its identifier") {
      val v = new AttributeTestValue(new AttributeDefinition("testid"))
      val m = new AttributeValuesMap()
      m += v
      assert(m("testid") eq v)
    }

    it("should return a default value when a non existing attribute value is retrieved by its identifier") {
      val m = new AttributeValuesMap()
//      m("testid") should equal (None)
    }

    it("should be possible to overwrite an already existing attribute value") {
      val m = new AttributeValuesMap()
      m += new AttributeTestValue(new AttributeDefinition("testid1"))
      val v = new AttributeTestValue(new AttributeDefinition("testid2"))
      m += v
      assert(m("testid2") eq v)
    }

    it("should be possible to add different attribute values with the same id to different maps") {
      val m1 = new AttributeValuesMap()
      val m2 = new AttributeValuesMap()
      val v1 = new AttributeTestValue(new AttributeDefinition("testid"))
      val v2 = new AttributeTestValue(new AttributeDefinition("testid"))
      m1 += v1
      m2 += v2
      assert(m1("testid") eq v1)
      assert(m2("testid") eq v2)
    }

    it("should be possible to add the same attribute value to different maps") {
      val m1 = new AttributeValuesMap()
      val m2 = new AttributeValuesMap()
      val v = new AttributeTestValue(new AttributeDefinition("testid"))
      m1 += v
      m2 += v
      assert(m1("testid") eq v)
      assert(m2("testid") eq v)
    }

  }

}
