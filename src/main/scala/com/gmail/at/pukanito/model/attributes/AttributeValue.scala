package com.gmail.at.pukanito.model.attributes

import com.gmail.at.pukanito.model.graph

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
) extends graph.Node[AttributeValue] {

  /**
   * The default key of an attribute definition: its attribute id.
   */
  // scalastyle:off public.methods.have.type
  override def key = attributeDefinition.attributeId.toString
  // scalastyle:on public.methods.have.type

  /**
   * Its value.
   */
  def value: Any

}
