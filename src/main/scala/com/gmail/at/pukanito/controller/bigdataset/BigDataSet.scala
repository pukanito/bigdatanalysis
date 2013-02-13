package com.gmail.at.pukanito.controller.bigdataset

import com.gmail.at.pukanito.model.dsl.AttributeModel
import com.gmail.at.pukanito.model.attributes.AttributeValue
import com.gmail.at.pukanito.model.store.GraphItemStore

/**
 * Big data set is a set of values in an attribute model that is stored in a store.
 *
 * A big data set makes it possible to manipulate stored data (create, read, update, delete, ...)
 * via a view.
 */
abstract class BigDataSet(
		// TODO: add view object
  val store: GraphItemStore[AttributeValue],
  val model: Option[AttributeModel]
) {

}