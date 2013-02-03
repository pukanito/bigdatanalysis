package com.gmail.at.pukanito.model.attributes

import com.gmail.at.pukanito.model.container.GraphItem

/**
 * The base class for all attribute definitions.
 *
 * @constructor Create a base attribute definition with a specific id.
 * @param attributeId The attribute identifier to assign to the AttributeDefinition
 */
class AttributeDefinition (
  val attributeId: AttributeIdentifier
) extends GraphItem[AttributeDefinition] {

  override def key = attributeId.toString

}
