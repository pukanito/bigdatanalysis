package com.gmail.at.pukanito.model.attributes

/**
 * The base class for all attribute definitions.
 *
 * The attribute id is specified at creation time and cannot be modified afterwards.
 *
 * @constructor Create a base attribute definition.
 * @param attributeId The attribute identifier to assign to the AttributeDefinition
 */
class AttributeDefinition(
  val attributeId: String
) {
  if (!attributeId.head.isUnicodeIdentifierStart ||
      !attributeId.tail.forall(_.isUnicodeIdentifierPart))
    throw new IllegalArgumentException("'" + attributeId + "' is not a valid attribute identifier")
}
