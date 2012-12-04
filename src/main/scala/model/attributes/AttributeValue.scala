package model.attributes

/**
 * The base class for all attribute value types.
 * 
 * An attribute value contains a value of a specific type and
 * can be parent for other attribute values.
 * 
 * An attribute value without a parent attribute is a root attribute.
 */
abstract class AttributeValue(
  val attributeDefinition: AttributeDefinition,
  val parentAttributeValue: Option[AttributeValue] = None
) {
  def value: Any
}