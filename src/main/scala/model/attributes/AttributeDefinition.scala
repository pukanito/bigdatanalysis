package model.attributes

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
 * @param attributesByIdMap an optional AttributeDefinitionsMap to add the AttributeDefinition to.
 */
class AttributeDefinition(
  val attributeId: String,
  attributesByIdMap: Option[AttributeDefinitionsMap] = None
) {
  val attributeNumber: Int = 0
  
  attributesByIdMap match {
    case Some(map) => map += this
    case None =>
  }
  
  def this(attributeId: String, attributesByIdMap: AttributeDefinitionsMap) {
    this(attributeId, Some(attributesByIdMap))
  }
}
