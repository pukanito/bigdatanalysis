package com.gmail.at.pukanito.model.attributes

/**
 * An attribute identifier.
 *
 * It uniquely identifies an attribute definition or attribute value.
 *
 * @constructor Create a base attribute definition.
 * @param attributeId The value to assign to the AttributeIdentifier
 */
class AttributeIdentifier(
  val attributeId: String
) {
  if (!attributeId.head.isUnicodeIdentifierStart ||
      !attributeId.tail.forall(_.isUnicodeIdentifierPart))
    throw new IllegalArgumentException("'" + attributeId + "' is not a valid attribute identifier")

  // For usage in a map
  override def hashCode() = attributeId.hashCode

  // For usage in a map
  override def equals(other: Any) = other match {
    case that: AttributeIdentifier => that.attributeId == attributeId
    case _ => false
  }

  // For convenience
  override def toString = attributeId
}

object AttributeIdentifier {
  implicit def string2AttributeIdentifier(s: String) = {
    new AttributeIdentifier(s)
  }
}