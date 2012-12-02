package model.attributes

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

class AttributeDefinitionsMapTest extends FunSpec with ShouldMatchers {

  describe("AttributeDefinitionsMap") {
    
    it("should be possible to retrieve an existing AttributeDefinition by its identifier") {
      val a = new AttributeDefinition("testid")
      val m = new AttributeDefinitionsMap()
      m += a
      val a2 = m("testid")
      a2.attributeId should equal ("testid")
    }
    
    it("should throw a RuntimeException when an AttributeDefinition is added with an already existing id") {
      val a = new AttributeDefinition("testid")
      val m = new AttributeDefinitionsMap()
      m += a
	  intercept[DuplicateAttributeException] {
		m += a
	  }
    }
    
    it("should throw an exception when a non existing AttributeDefinition is retrieved by its identifier") {
      val m = new AttributeDefinitionsMap()
	  intercept[NoSuchElementException] {
		m("testid")
	  }
    }
    
    it("should be possible to create AttributeDefinitions with the same id in different AttributeDefinitionsMaps") (pending)
    
  }

}