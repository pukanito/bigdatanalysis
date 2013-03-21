package com.gmail.at.pukanito.model.attributes

import com.gmail.at.pukanito.model.graph.Node

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
) extends Node[AttributeValue] {

  /**
   * The default key of an attribute definition: its attribute id.
   */
  override def key = attributeDefinition.attributeId.toString

  /**
   * Its value.
   */
  def value: Any

}
