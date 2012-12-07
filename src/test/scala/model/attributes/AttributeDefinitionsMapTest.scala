package model.attributes

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

class AttributeDefinitionsMapTest extends FunSpec with ShouldMatchers {

  describe("AttributeDefinitionsMap") {
    
    it("should be possible to retrieve an existing AttributeDefinition by its identifier") {
      val m = new AttributeDefinitionsMap()
      m += new AttributeDefinition("testid")
      m("testid").attributeId should equal ("testid")
    }
    
    it("should throw a NoSuchElementException when a non existing AttributeDefinition is retrieved by its identifier") {
      val m = new AttributeDefinitionsMap()
	  intercept[NoSuchElementException] {
		m("testid")
	  }
    }
    
    it("should throw a DuplicateAttributeException when an AttributeDefinition is added with an already existing id") {
    	val m = new AttributeDefinitionsMap()
    	m += new AttributeDefinition("testid")
    	intercept[DuplicateAttributeException] {
    		m += new AttributeDefinition("testid")
    	}
    }
    
    it("should be possible to create AttributeDefinitions with the same id in different AttributeDefinitionsMaps") {
      val m = new AttributeDefinitionsMap()
      val m2 = new AttributeDefinitionsMap()
      m += new AttributeDefinition("testid")
	  m2 += new AttributeDefinition("testid")
    }
    
  }

}