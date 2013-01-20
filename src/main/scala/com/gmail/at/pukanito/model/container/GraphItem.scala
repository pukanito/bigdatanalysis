package com.gmail.at.pukanito.model.container

import scala.collection.immutable.HashMap

/**
 * Exception thrown when an AttributeDescription is added to an AttributeDefinitionsMap
 * and the map already contains an AttributeDescription with the same identifier.
 *
 * @constructor Create a graph cycle exception.
 * @param value The duplicate graph item.
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
   * @return the key of a graph item. Should be immutable!
   */
  def key: GraphItemKey

  /**
   * @return set of parent graph items of this graph item, empty when this is a root item.
   */
  def parents: Set[GraphItem] = { parentValues }

  /**
   * @return map of child graph items of this graph item.
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

  /**
   * Get a specific item of the graph.
   *
   * @param p path to the child item.
   * @return a child item according to the specified path.
   */
  def get(p: GraphPath): GraphItem = {
    if (p.size == 0)
      this
    else
      children(p.head).get(p.tail)
  }

}