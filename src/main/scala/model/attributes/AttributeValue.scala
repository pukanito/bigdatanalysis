package model.attributes

import scala.collection.mutable

/**
 * The base class for all attribute value types.
 *
 * An attribute value contains a value of a specific type and
 * can be parent for other attribute values.
 *
 * An attribute value without a parent attribute is a root attribute.
 */
abstract class AttributeValue(
  val attributeDefinition: AttributeDefinition
) {
  private val parentValues: mutable.Set[AttributeValue] = mutable.Set()
  private val childrenMap: AttributeValuesMap = new AttributeValuesMap()

  /**
   * Its value.
   */
  def value: Any

  /**
   * Parent attribute values of this attribute value.
   */
  def parents: Set[AttributeValue] = { Set.empty ++ parentValues }

  /**
   * Map with child attribute values of this attribute value.
   */
  def children: AttributeValuesMap = { childrenMap }

  /**
   * Add a child value to this attribute value.
   */
  def +=(value: AttributeValue) = {
    childrenMap += value
    value.parentValues += this
  }
}
