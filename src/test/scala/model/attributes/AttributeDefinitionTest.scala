package model.attributes

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

class AttributeDefinitionTest extends FunSpec with ShouldMatchers {

  describe("AttributeDefinition") {
    
    it("should have an identifier equal to the one specified at creation") {
      val a = new AttributeDefinition("testid")
      a.attributeId should equal ("testid")
    }
    
    it("should throw a DuplicateAttributeException when an AttributeDefinition is created in an AttributeDefinitionsMap that already contains the id") {
   	  val m = new AttributeDefinitionsMap()
      new AttributeDefinition("testid", m)
	  intercept[DuplicateAttributeException] {
		new AttributeDefinition("testid", m)
	  }
    }
    
    it("should not be possible to modify the identifier") {
      // implicit by making attributeId a 'val'
    }

    it("should have an attribute number") (pending)
    
    it("should not be possible to modify the attribute number") {
      // implicit by  making attributeNumber a 'val'
    }
    
  }
}