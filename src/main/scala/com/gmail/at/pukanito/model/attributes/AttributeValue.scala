package com.gmail.at.pukanito.model.attributes

import scala.collection.mutable
import com.gmail.at.pukanito.model.container.GraphItem
import com.gmail.at.pukanito.model.container.GraphItemKey

/**
 * The base class for all attribute value types.
 *
 * An attribute value contains a value of a specific type and
 * can be parent for other attribute values.
 *
 * @constructor Create a base attribute value.
 * @param attributeDefinition The definition of this attribute value.
 */
abstract class AttributeValue(
  val attributeDefinition: AttributeDefinition
) extends GraphItem {

  override def key = GraphItemKey(attributeDefinition.attributeId.toString -> value)

  /**
   * Its value.
   */
  def value: Any

}
