package model.attributes

import scala.collection.mutable

/**
 * Exception thrown when an AttributeDescription is added to an AttributeDefinitionsMap
 * and the map already contains an AttributeDescription with the same identifier.
 */
class DuplicateAttributeException(identifier: String) 
  extends RuntimeException("Duplicate attribute id in AttributeDefinitionsMap: " + identifier) {}

/**
 * A map containing AttributeDefinitions.
 */
class AttributeDefinitionsMap extends mutable.HashMap[String, AttributeDefinition] {

  def +=(value: AttributeDefinition) = {
	if (this contains value.attributeId)
	  throw new DuplicateAttributeException(value.attributeId)
    this(value.attributeId) = value
  }
}

