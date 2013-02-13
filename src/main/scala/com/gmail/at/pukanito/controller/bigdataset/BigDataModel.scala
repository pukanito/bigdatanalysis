package com.gmail.at.pukanito.controller.bigdataset

import com.gmail.at.pukanito.model.attributes.AttributeDefinition
import com.gmail.at.pukanito.model.store.GraphItemStore

/**
 * Big data model is an attribute model that is stored in a store.
 *
 * A big data model makes it possible to manipulate stored attribute definitions (create,
 * read, update, delete, ...) via a view.
 */
abstract class BigDataModel(
  // TODO: add view object
  val store: GraphItemStore[AttributeDefinition]
) {

}