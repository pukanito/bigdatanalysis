package model.attributes

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

class AttributeDefinitionsMapTest extends FunSpec with ShouldMatchers {

  describe("AttributeDefinitionsMap") {

    it("should be possible to retrieve an existing attribute definition by its identifier") {
         val d = new AttributeDefinition("testid")
      val m = new AttributeDefinitionsMap()
      m += d
      assert(m("testid") eq d)
    }

    it("should throw a NoSuchElementException when a non existing attribute definition is retrieved by its identifier") {
      val m = new AttributeDefinitionsMap()
      intercept[NoSuchElementException] {
        m("testid")
      }
    }

    it("should throw a DuplicateAttributeException when an attribute definition is added with an already existing id") {
      val m = new AttributeDefinitionsMap()
      m += new AttributeDefinition("testid")
      intercept[DuplicateAttributeException] {
          m += new AttributeDefinition("testid")
      }
    }

    it("should be possible to add different attribute definitions with the same id to different maps") {
      val m1 = new AttributeDefinitionsMap()
      val m2 = new AttributeDefinitionsMap()
      val d1 = new AttributeDefinition("testid")
      val d2 = new AttributeDefinition("testid")
      m1 += d1
      m2 += d2
      assert(m1("testid") eq d1)
      assert(m2("testid") eq d2)
    }

    it("should be possible to add the same attribute definition to different maps") {
      val m1 = new AttributeDefinitionsMap()
      val m2 = new AttributeDefinitionsMap()
      val d = new AttributeDefinition("testid")
      m1 += d
      m2 += d
      assert(m1("testid") eq d)
      assert(m2("testid") eq d)
    }

  }

}
