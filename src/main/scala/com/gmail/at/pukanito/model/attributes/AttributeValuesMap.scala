package com.gmail.at.pukanito.model.attributes

import scala.collection.mutable

/**
 * A map containing AttributeValues.
 *
 * @constructor Create an empty attribute values map.
 */
class AttributeValuesMap extends mutable.HashMap[String, AttributeValue] {

  /**
   * Add a new attribute value to this map.
   *
   * @param value The attribute value to add to this map. The attribute definition id will be used as the map key.
   */
  def +=(value: AttributeValue) = {
    this(value.attributeDefinition.attributeId) = value
  }
}
