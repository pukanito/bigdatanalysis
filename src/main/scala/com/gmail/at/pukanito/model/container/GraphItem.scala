package com.gmail.at.pukanito.model.container

/**
 * Trait for making items graph compatible.
 */
trait GraphItem {
  private var parentValues: Set[GraphItem] = Set()
  private var childrenMap: Map[String, GraphItem] = Map()

  /**
   * Set of parent graph items of this graph item.
   */
  def parents: Set[GraphItem] = { parentValues }

  /**
   * Map of child graph items of this graph items.
   */
  def children: Map[String, GraphItem] = { childrenMap }

}