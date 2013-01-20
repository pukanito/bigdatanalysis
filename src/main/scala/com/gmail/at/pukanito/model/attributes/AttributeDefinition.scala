package com.gmail.at.pukanito.model.attributes

import com.gmail.at.pukanito.model.container.GraphItem

/**
 * The base class for all attribute definitions.
 *
 * The attribute id is specified at creation time and cannot be modified afterwards.
 *
 * @constructor Create a base attribute definition.
 * @param attributeId The attribute identifier to assign to the AttributeDefinition
 */
class AttributeDefinition (
  val attributeId: AttributeIdentifier
) extends GraphItem {

  override def key = Map("id" -> attributeId)

}
