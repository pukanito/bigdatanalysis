package com.gmail.at.pukanito.model.attributes

import com.gmail.at.pukanito.model.container.GraphItem

/**
 * Items that can be put in an attribute model.
 */
class AttributeDefinitionNode (
  attributeId: AttributeIdentifier,
  attributeValueKeyIds: List[AttributeIdentifier] = Nil
) extends AttributeDefinition(attributeId, attributeValueKeyIds) with GraphItem[AttributeDefinitionNode] {

  /**
   * The key of an attribute definition: its attribute id.
   */
  override def key = attributeId.toString

  override def copy: AttributeDefinitionNode = new AttributeDefinitionNode(attributeId, attributeValueKeyIds)

}