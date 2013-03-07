package com.gmail.at.pukanito.controller.bigdataset

import com.gmail.at.pukanito.model.dsl.AttributeModelItem
import com.gmail.at.pukanito.model.store.GraphItemStore
import com.gmail.at.pukanito.model.container.GraphItemKey


/**
 * Big data model is a number of attribute models stored in a store.
 *
 * A big data model makes it possible to manipulate stored attribute definitions (create,
 * read, update, delete, ...).
 */
abstract class BigDataModel(
  val graphItemStore: GraphItemStore[AttributeModelItem]
) {
  val rootAttributeDefinitions: Map[GraphItemKey, AttributeModelItem] = Map[GraphItemKey, AttributeModelItem]()

  def add(attributeDefinition: AttributeModelItem)

  def update(attributeDefinition: AttributeModelItem)

  def delete

  def read

  def load

  def store

  def include
}