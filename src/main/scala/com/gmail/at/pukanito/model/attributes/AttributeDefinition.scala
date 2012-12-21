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
  id: AttributeIdentifier
) {
  private val attributeDefinitionId = id

  def attributeId: String = { attributeDefinitionId.toString }
}
