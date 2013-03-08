package com.gmail.at.pukanito.model.attributes

import com.gmail.at.pukanito.model.container.GraphItem

/**
 * Attribute definitions that can be put in an attribute model.
 */
class AttributeDefinitionNode (
  attributeId: AttributeIdentifier,
  val attributeValueKeyIds: List[AttributeIdentifier] = Nil
) extends AttributeDefinition(attributeId) with GraphItem[AttributeDefinitionNode] {

  /**
   * The key of an attribute definition: its attribute id.
   */
  override def key = attributeId.toString

  override def copy: AttributeDefinitionNode = new AttributeDefinitionNode(attributeId, attributeValueKeyIds)

}