package com.gmail.at.pukanito.controller.bigdataset

import com.gmail.at.pukanito.model.attributes.{AttributeDefinitionNode,AttributeModel}
import com.gmail.at.pukanito.model.repository.GraphRepository


/**
 * Big data model is an attribute definition graph stored in a
 * graph repository. Listeners can be attached to specific events:
 *
 * - load
 * - store
 */
class BigDataModel (
  val graphRepository: GraphRepository[AttributeDefinitionNode]
) extends AttributeModel {
  // scalastyle:off public.methods.have.type
  def load = graphRepository.get()
  // scalastyle:on public.methods.have.type

  // scalastyle:off public.methods.have.type
  def store = graphRepository.put(rootAttributes.toSeq:_*)
  // scalastyle:on public.methods.have.type
}
