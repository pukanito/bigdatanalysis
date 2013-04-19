package com.gmail.at.pukanito.controller.bigdataset

import com.gmail.at.pukanito.model.attributes.{AttributeModel,AttributeValue}
import com.gmail.at.pukanito.model.repository.GraphRepository

/**
 * Big data set is a set of values in an attribute model that is stored in a store.
 *
 * A big data set makes it possible to manipulate stored data (create, read, update, delete, ...).
 */
abstract class BigDataSet(
  val repository: GraphRepository[AttributeValue],
  val model: Option[AttributeModel]
) {

}
