package com.gmail.at.pukanito.model.dsl

import com.gmail.at.pukanito.model.attributes.{AttributeDefinition,AttributeIdentifier}
import com.gmail.at.pukanito.model.container.GraphItem

/**
 * Items that can be put in an attribute model.
 */
class AttributeModelItem (
  attributeId: AttributeIdentifier,
  attributeValueKeyIds: List[AttributeIdentifier] = Nil
) extends AttributeDefinition(attributeId, attributeValueKeyIds) with GraphItem[AttributeModelItem] {

  /**
   * The key of an attribute definition: its attribute id.
   */
  override def key = attributeId.toString

  override def copy: AttributeModelItem = new AttributeModelItem(attributeId, attributeValueKeyIds)

}