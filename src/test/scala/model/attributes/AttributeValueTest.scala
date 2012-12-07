package model.attributes

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

class AttributeValueTest extends FunSpec with ShouldMatchers {

  private class AttributeTestValue(
    attributeDefinition: AttributeDefinition,
    parentAttributeValue: Option[AttributeValue] = None
  ) extends AttributeValue(attributeDefinition, parentAttributeValue) {
    
    private val parentsMap: AttributeDefinitionsMap = new AttributeDefinitionsMap()
    private val childrenMap: AttributeDefinitionsMap = new AttributeDefinitionsMap()

    def value: Any = { 0 }
    
    def parents: AttributeDefinitionsMap = { parentsMap }
    
    def children: AttributeDefinitionsMap = { childrenMap }
  }

  describe("AttributeValue") {

    it("should be associated with an attribute definition") {
      val d = new AttributeDefinition("testid")
      val v: AttributeValue = new AttributeTestValue(d)
      v.attributeDefinition should equal (d)
    }

    it("can be a root attribute, i.e. has no parent attribute values") (pending)

    it("can be associated with a single parent attribute values") (pending)

    it("can be associated with multiple parent attribute values") (pending)
    
    it("can have zero or more child attribute values") (pending)

  }
}
