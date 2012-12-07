package model.attributes

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

class AttributeDefinitionsMapTest extends FunSpec with ShouldMatchers {

  describe("AttributeDefinitionsMap") {
    
    it("should be possible to retrieve an existing attribute definition by its identifier") {
      val m = new AttributeDefinitionsMap()
      m += new AttributeDefinition("testid")
      m("testid").attributeId should equal ("testid")
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
      val m = new AttributeDefinitionsMap()
      val m2 = new AttributeDefinitionsMap()
      m += new AttributeDefinition("testid")
	  m2 += new AttributeDefinition("testid")
    }
    
    it("should be possible to add the same attribute definition to different maps") {
      val m = new AttributeDefinitionsMap()
      val m2 = new AttributeDefinitionsMap()
      val a = new AttributeDefinition("testid")
      m += a
	  m2 += a
    }
    
  }

}