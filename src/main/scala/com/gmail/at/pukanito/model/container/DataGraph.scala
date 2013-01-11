package com.gmail.at.pukanito.model.container

/**
 * Container for data graph items.
 */
class DataGraph {
  private var parentValues: Set[DataGraphItem] = Set()
  private var childrenMap: Map[String, DataGraphItem] = Map()

  /**
   * Set of parent graph items of this graph item.
   */
  def parents: Set[DataGraphItem] = { parentValues }

  /**
   * Map of child graph items of this graph items.
   */
  def children: Map[String, DataGraphItem] = { childrenMap }

}