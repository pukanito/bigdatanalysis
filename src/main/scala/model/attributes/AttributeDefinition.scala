package model.attributes

import scala.collection.mutable

/**
 * The base class for all attribute definitions.
 *
 * Each attribute definition is identified by a unique attribute id and gets
 * automatically assigned a unique attribute number.
 */
class AttributeDefinition(val attributeId: String) {
  val attributeNumber: Int = 0
}
