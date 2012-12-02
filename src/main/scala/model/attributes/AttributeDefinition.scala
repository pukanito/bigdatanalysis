package model.attributes

import scala.collection.mutable

/**
 * The base class for all attribute definitions.
 *
 * Each attribute definition is identified by a unique attribute id and gets
 * automatically assigned a unique attribute number.
 * 
 * The attribute id is specified at creation time and cannot be modified afterwards.
 * 
 * The attribute number is generated at creation time and cannot be modified afterwards.
 * 
 * @param attributeId the attribute identifier to assign to the AttributeDefinition
 */
class AttributeDefinition(val attributeId: String) {
  val attributeNumber: Int = 0
}
