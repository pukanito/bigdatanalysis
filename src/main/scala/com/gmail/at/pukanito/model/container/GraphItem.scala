package com.gmail.at.pukanito.model.container

import scala.collection.immutable.HashMap

/**
 * Exception thrown when an AttributeDescription is added to an AttributeDefinitionsMap
 * and the map already contains an AttributeDescription with the same identifier.
 *
 * @constructor Create a duplicate attribute exception.
 * @param identifier The identifier of the duplicate attribute.
 */
class GraphCycleException(value: GraphItem)
  extends RuntimeException("Cycle in graph with: " + value) {}

/**
 * Trait for making items graph compatible.
 */
trait GraphItem {
  import GraphPath.GraphItemKey
  private var parentValues: Set[GraphItem] = Set()
  private[this] var childrenMap: Map[GraphItemKey, GraphItem] = Map()

  /**
   * The key of a graph item. Should be immutable!
   */
  def key: GraphItemKey

  /**
   * Set of parent graph items of this graph item. Empty when
   * this is a root item.
   */
  def parents: Set[GraphItem] = { parentValues }

  /**
   * Map of child graph items of this graph item.
   */
  def children: Map[GraphItemKey, GraphItem] = { childrenMap }

  /**
   * Add a new child to this graph item.
   *
   * Throws GraphCycleException when a cycle is detected.
   *
   * @param value The child to add.
   */
  def +=(value: GraphItem) = {
    def testCycleExists(items: Set[GraphItem]): Boolean = {
      if (items.isEmpty) false else items.exists( (x) => (x eq value) || testCycleExists(x.parents) )
    }
    if (testCycleExists(Set(this))) throw new GraphCycleException(value)
    childrenMap += (value.key -> value)
    value.parentValues += this
  }

}