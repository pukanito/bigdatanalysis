package attributes

import scala.collection.mutable

/**
 * The base class for all attribute definitions.
 *
 * Each attribute definition is identified by a unique attribute id and gets
 * automatically assigned a unique attribute number.
 */
class AttributeDefinition(attributeId: String) {
  import AttributeDefinition.attributesByIdMap
  if (attributesByIdMap contains attributeId)
    throw new RuntimeException("Duplicate attribute id defined: " + attributeId)
  attributesByIdMap(attributeId) = this
}

object AttributeDefinition {
  private val attributesByIdMap = mutable.Map[String, AttributeDefinition]()

  def attributeDefinitionById(id: String): AttributeDefinition = {
    attributesByIdMap(id)
  }
}

