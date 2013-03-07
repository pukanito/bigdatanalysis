package com.gmail.at.pukanito.model.attributes

import com.gmail.at.pukanito.model.container.{GraphItem,GraphItemKey}

/**
 * The base class for all attribute definitions.
 *
 * An attribute definition describes a data structure that can be used to store a value.
 *
 * @constructor Create a base attribute definition with a specific attribute id.
 * @param attributeId The attribute identifier to assign to the AttributeDefinition. Every
 *          attribute definition has a unique attribute identifier.
 * @param attributeValueKeyIds List of identifiers that are part of the key of an attribute value
 *          that is associated with this attribute definition.
 */
class AttributeDefinition (
  val attributeId: AttributeIdentifier,
  val attributeValueKeyIds: List[AttributeIdentifier] = Nil
) {

}
