package model.attributes

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

class AttributeValueTest extends FunSpec with ShouldMatchers {

  describe("AttributeValue") {
    
    it("should be associated with an AttributeDefinition") {
      val d = new AttributeDefinition("testid")
      val v: AttributeValue = new AttributeTestValue(d)
      v.attributeDefinition should equal (d)
    }
    
    it("can be a root attribute, i.e. has no parent AttributeValue") (pending)
    
    it("can be associated with a parent AttributeValue") (pending)
    
  }
}

class AttributeTestValue(
  attributeDefinition: AttributeDefinition,
  parentAttributeValue: Option[AttributeValue] = None
) extends AttributeValue(attributeDefinition, parentAttributeValue) {
    
  def value: Any = { 0 }
}