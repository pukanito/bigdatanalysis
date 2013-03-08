package com.gmail.at.pukanito.controller.bigdataset

import com.gmail.at.pukanito.model.attributes.AttributeDefinitionNode
import com.gmail.at.pukanito.model.store.GraphItemStore
import com.gmail.at.pukanito.model.container.GraphItemKey


/**
 * Big data model is an attribute model stored in a graph item store.
 */
abstract class BigDataModel(
  val graphItemStore: GraphItemStore[AttributeDefinitionNode]
) {
  val rootAttributeDefinitions: Map[GraphItemKey, AttributeDefinitionNode] = Map[GraphItemKey, AttributeDefinitionNode]()

  def add(attributeDefinitionNode: AttributeDefinitionNode)

  def update(attributeDefinitionNode: AttributeDefinitionNode)

  def delete

  def read

  def load

  def store

  def include
}