package com.gmail.at.pukanito.controller.bigdataset

import com.gmail.at.pukanito.model.attributes.AttributeDefinitionNode
import com.gmail.at.pukanito.model.store.GraphItemStore
import com.gmail.at.pukanito.model.container.GraphItemKey


/**
 * Big data model is a number of attribute models stored in a store.
 *
 * A big data model makes it possible to manipulate stored attribute definitions (create,
 * read, update, delete, ...).
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