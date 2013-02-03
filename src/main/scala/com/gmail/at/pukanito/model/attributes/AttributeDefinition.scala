package com.gmail.at.pukanito.model.attributes

import com.gmail.at.pukanito.model.container.{GraphItem,GraphItemKey}

/**
 * The base class for all attribute definitions.
 *
 * @constructor Create a base attribute definition with a specific id.
 * @param attributeId The attribute identifier to assign to the AttributeDefinition
 * @param attributeValueKeyIds List of identifiers that are part of the key of an attribute value
 *          that is associated with this attribute definition.
 */
class AttributeDefinition (
  val attributeId: AttributeIdentifier,
  val attributeValueKeyIds: List[AttributeIdentifier] = List(),
  initialChildren: Set[GraphItem[AttributeDefinition]] = Set[GraphItem[AttributeDefinition]](),
  initialParents: Set[GraphItem[AttributeDefinition]] = Set[GraphItem[AttributeDefinition]]()
) extends GraphItem[AttributeDefinition](initialChildren, initialParents) {

  override def key = attributeId.toString

}
