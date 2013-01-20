package com.gmail.at.pukanito.model.attributes

/**
 * An attribute identifier.
 *
 * It identifies an attribute definition or attribute value.
 *
 * @constructor Create an attribute identifier.
 * @param attributeId The value to assign to the AttributeIdentifier
 */
class AttributeIdentifier(
  val attributeId: String
) {
  if (!attributeId.head.isUnicodeIdentifierStart ||
      !attributeId.tail.forall(_.isUnicodeIdentifierPart))
    throw new IllegalArgumentException("'" + attributeId + "' is not a valid attribute identifier")

  override def toString() = attributeId

  // For usage in a map
  override def hashCode() = attributeId.hashCode

  // For usage in a map
  override def equals(other: Any) = other match {
    case that: AttributeIdentifier => that.attributeId == attributeId
    case _ => false
  }

}

object AttributeIdentifier {
  implicit def string2AttributeIdentifier(s: String) = {
    new AttributeIdentifier(s)
  }
}