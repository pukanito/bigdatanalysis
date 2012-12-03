package model

/**
 * Immutable entity.
 */
trait Entity {
    
    /**
     * Identification of this entity. 
     */
    def id: String
    
    /**
     * The values contained in this entity. 
     */
    def values: Map[String, AttributeValue]
    
    /**
     * Update a value, creates a new entity.
     * 
     * @param attrValue the new attribute value
     * @return a new entity with the updated attribute value
     */
    def update(attrValue: AttributeValue): Entity
}

/**
 * Default implementation of Entity.
 */
case class DefaultEntity(id: String, values: Map[String, AttributeValue] = Map()) {
    def update(attrValue: AttributeValue) = DefaultEntity(id, values.updated(attrValue.attribute.id, attrValue))
}