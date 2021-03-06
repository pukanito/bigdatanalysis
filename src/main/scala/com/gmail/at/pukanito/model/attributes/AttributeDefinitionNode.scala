package com.gmail.at.pukanito.model.attributes

import com.gmail.at.pukanito.model.graph

/**
 * Attribute definitions that can be put in an attribute model.
 *
 * @constructor Create an attribute definition node with a specific attribute id and define the key.
 * @param attributeId The attribute identifier to assign to the AttributeDefinitionNode.
 * @param attributeValueKeyIds The key attributes of the node to identify attribute values.
 */
class AttributeDefinitionNode (
  attributeId: AttributeIdentifier,
  val attributeValueKeyIds: List[AttributeIdentifier] = Nil
) extends AttributeDefinition(attributeId) with graph.Node[AttributeDefinitionNode] {

  /**
   * The key of an attribute definition: its attribute id.
   */
  // scalastyle:off public.methods.have.type
  override def key = attributeId.toString
  // scalastyle:on public.methods.have.type

  override def copy: AttributeDefinitionNode = new AttributeDefinitionNode(attributeId, attributeValueKeyIds)

}
