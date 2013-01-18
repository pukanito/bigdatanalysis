package com.gmail.at.pukanito.model.container

/**
 * Representation of a relative or absolute path through GraphItems.
 */
class GraphPath {
  import GraphPath.GraphItemKey
  private[this] var pathList: List[GraphItemKey] = List()

  def depth = pathList.size
}

object GraphPath {
  type GraphItemKey = Map[String, Any]
}