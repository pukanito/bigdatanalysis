package model.attributes

import scala.collection.mutable

/**
 * A map containing AttributeValues.
 */
class AttributeValuesMap extends mutable.HashMap[String, AttributeValue] {

  def +=(value: AttributeValue) = {
    this(value.attributeDefinition.attributeId) = value
  }
}
