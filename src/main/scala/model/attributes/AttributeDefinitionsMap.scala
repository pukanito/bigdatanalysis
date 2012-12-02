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
class AttributeDefinitionsMap {

  private val attributesByIdMap = mutable.Map[String, AttributeDefinition]()
  
  def contains(key: String): Boolean = {
    attributesByIdMap contains key
  }
  
  def apply(key: String): AttributeDefinition = {
    attributesByIdMap(key)
  }

  def +=(value: AttributeDefinition) = {
	if (attributesByIdMap contains value.attributeId)
	  throw new DuplicateAttributeException(value.attributeId)
    attributesByIdMap(value.attributeId) = value
  }
}

