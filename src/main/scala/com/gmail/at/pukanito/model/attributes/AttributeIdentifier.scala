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
  // scalastyle:off if.brace
  if (!attributeId.head.isUnicodeIdentifierStart ||
      !attributeId.tail.forall(_.isUnicodeIdentifierPart))
    throw new IllegalArgumentException("'" + attributeId + "' is not a valid attribute identifier")
  // scalastyle:on if.brace

  /**
   * For printing.
   */
  // scalastyle:off public.methods.have.type
  override def toString() = attributeId
  // scalastyle:on public.methods.have.type

  /**
   * For usage in map.
   */
  // scalastyle:off public.methods.have.type
  override def hashCode() = attributeId.hashCode
  // scalastyle:on public.methods.have.type

  /**
   * For usage in map.
   */
  // scalastyle:off public.methods.have.type
  override def equals(other: Any) = other match {
  // scalastyle:on public.methods.have.type
    case that: AttributeIdentifier => that.attributeId == attributeId
    case _ => false
  }

}

/**
 * Helpers for creating attribute identifiers.
 */
object AttributeIdentifier {

  import scala.language.implicitConversions

  /**
   * For convenience, use String where AttributeIdentifier is needed.
   */
  // scalastyle:off public.methods.have.type
  implicit def string2AttributeIdentifier(s: String) = {
  // scalastyle:on public.methods.have.type
    new AttributeIdentifier(s)
  }
}
