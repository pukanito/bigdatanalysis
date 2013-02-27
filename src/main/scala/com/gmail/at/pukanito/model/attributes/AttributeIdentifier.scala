package com.gmail.at.pukanito.model.attributes

/**
 * An attribute identifier.
 *
 * Identifies an attribute definition. Also used in (compound) key of attribute values.
 *
 * @constructor Creates an attribute identifier.
 * @param attributeId The value to assign to the AttributeIdentifier.
 * @throws IllegalArgumentException when the attributeId is not a valid identifier.
 */
class AttributeIdentifier(
  private val attributeId: String
) {
  if (!attributeId.head.isUnicodeIdentifierStart ||
      !attributeId.tail.forall(_.isUnicodeIdentifierPart))
    throw new IllegalArgumentException("'" + attributeId + "' is not a valid attribute identifier")

  /**
   * For printing.
   */
  override def toString() = attributeId

  /**
   * For usage in map.
   */
  override def hashCode() = attributeId.hashCode

  /**
   * For usage in map.
   */
  override def equals(other: Any) = other match {
    case that: AttributeIdentifier => that.attributeId == attributeId
    case _ => false
  }

}

/**
 * Helpers for creating attribute identifiers.
 */
object AttributeIdentifier {
  /**
   * For convenience, use String where AttributeIdentifier is needed.
   */
  implicit def string2AttributeIdentifier(s: String) = {
    new AttributeIdentifier(s)
  }
}