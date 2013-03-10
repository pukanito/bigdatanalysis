package com.gmail.at.pukanito.controller.bigdataset

import com.gmail.at.pukanito.model.attributes.AttributeDefinitionNode
import com.gmail.at.pukanito.model.store.GraphItemStore
import com.gmail.at.pukanito.model.container.GraphItemKey
import com.gmail.at.pukanito.model.attributes.AttributeModel
import com.gmail.at.pukanito.model.container.GraphPath


/**
 * Big data model is an attribute definition nodes hierarchy stored in a 
 * graph item store. Listeners can be attached to specific events:
 * 
 * - create
 * - read
 * - update
 * - delete
 * - load
 * - store
 * - include
 */
class BigDataModel (
  val graphItemStore: GraphItemStore[AttributeDefinitionNode]
) extends AttributeModel {
  def create(attributeDefinitionNode: AttributeDefinitionNode): BigDataModel

  def read(graphPath: GraphPath): AttributeDefinitionNode

  def update(attributeDefinitionNode: AttributeDefinitionNode): BigDataModel

  def delete(graphPath: GraphPath): BigDataModel

  def load: BigDataModel

  def store: BigDataModel

  def include: BigDataModel
}