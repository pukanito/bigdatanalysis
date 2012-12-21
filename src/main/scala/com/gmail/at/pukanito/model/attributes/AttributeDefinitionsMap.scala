package com.gmail.at.pukanito.model.attributes

import scala.collection.mutable

/**
 * Exception thrown when an AttributeDescription is added to an AttributeDefinitionsMap
 * and the map already contains an AttributeDescription with the same identifier.
 *
 * @constructor Create a duplicate attribute exception.
 * @param identifier The identifier of the duplicate attribute.
 */
class DuplicateAttributeException(identifier: String)
  extends RuntimeException("Duplicate attribute id in AttributeDefinitionsMap: " + identifier) {}

/**
 * A map containing AttributeDefinitions.
 *
 * @constructor Create an empty attribute definitions map.
 */
class AttributeDefinitionsMap extends mutable.HashMap[String, AttributeDefinition] {

  /**
   * Add a new attribute definition to this map.
   *
   * @param value The attribute definition to add to this map. The attribute id will be used as the map key.
   */
  def +=(value: AttributeDefinition) = {
    if (this contains value.attributeId)
      throw new DuplicateAttributeException(value.attributeId)
    this(value.attributeId) = value
  }
}

