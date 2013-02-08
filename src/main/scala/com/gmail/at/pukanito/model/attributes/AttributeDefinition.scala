package com.gmail.at.pukanito.model.attributes

import com.gmail.at.pukanito.model.container.{GraphItem,GraphItemKey}

/**
 * The base class for all attribute definitions.
 *
 * @constructor Create a base attribute definition with a specific id.
 * @param attributeId The attribute identifier to assign to the AttributeDefinition
 * @param attributeValueKeyIds List of identifiers that are part of the key of an attribute value
 *          that is associated with this attribute definition.
 * @param initialChildren List of attribute definitions that will be added as children of this
 *          attribute definition. This attribute definition will be their parent.
 * @param initialParents List of attribute definitions that will be added as parents of this
 *          attribute definition. This attribute definition will be their children.
 */
class AttributeDefinition (
  val attributeId: AttributeIdentifier,
  val attributeValueKeyIds: List[AttributeIdentifier] = List(),
  initialChildren: Set[AttributeDefinition] = Set[AttributeDefinition](),
  initialParents: Set[AttributeDefinition] = Set[AttributeDefinition]()
) extends GraphItem[AttributeDefinition](initialChildren, initialParents) {

  /**
   * The default key of an attribute definition: its attribute id.
   */
  override def key = attributeId.toString

}
