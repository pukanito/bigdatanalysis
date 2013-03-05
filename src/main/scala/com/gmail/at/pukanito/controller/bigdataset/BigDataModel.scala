package com.gmail.at.pukanito.controller.bigdataset

import com.gmail.at.pukanito.model.attributes.AttributeDefinition
import com.gmail.at.pukanito.model.store.GraphItemStore
import com.gmail.at.pukanito.model.container.GraphItemKey


/**
 * Big data model is an attribute model that is stored in a store.
 *
 * A big data model makes it possible to manipulate stored attribute definitions (create,
 * read, update, delete, ...) via a view.
 */
abstract class BigDataModel(
  // TODO: add view object
  val graphItemStore: GraphItemStore[AttributeDefinition]
) {
  val rootAttributeDefinitions: Map[GraphItemKey, AttributeDefinition] = Map[GraphItemKey, AttributeDefinition]()

  def add(attributeDefinition: AttributeDefinition)

  def update(attributeDefinition: AttributeDefinition)

  def delete

  def read

  def load

  def store

  def include
}