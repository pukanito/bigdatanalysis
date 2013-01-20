package com.gmail.at.pukanito.model.container

import scala.collection.immutable.HashMap

/**
 * Exception thrown when an item is added to a GraphItem and the item is the same object
 * as the GraphItem or one of its (in)direct parents.
 *
 * @constructor Create a graph cycle exception.
 * @param value The duplicate graph item.
 */
class GraphCycleException(value: GraphItem)
  extends RuntimeException("Cycle in graph with: " + value) {}

/**
 * Exception thrown when an item is added to a GraphItem with an already existing key.
 *
 * @constructor Create a duplicate item exception.
 * @param value The item that failed to added.
 */
class DuplicateGraphItemException(value: GraphItem)
  extends RuntimeException("Duplicate graph item: " + value) {}

/**
 * Trait for making items graph compatible.
 *
 * GraphItem can have:
 *  - compound key
 *  - multiple children
 *  - multiple parents
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
  def parents: Set[GraphItem] = parentValues

  /**
   * @return map of child graph items of this graph item.
   */
  def children: Map[GraphItemKey, GraphItem] = childrenMap

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
    if (childrenMap contains value.key) throw new DuplicateGraphItemException(value)
    childrenMap += (value.key -> value)
    value.parentValues += this
  }

  /**
   * Get a specific item of the graph.
   *
   * @param p path to the child item.
   * @return a child item according to the specified path (NoSuchElementException if
   *         an item in the path does not exist.
   */
  def apply(p: GraphPath): GraphItem = {
    if (p.size == 0)
      this
    else
      children(p.head)(p.tail)
  }

}

/**
 * Trait for making items graph compatible.
 *
 * GraphItem can have:
 *  - single key
 *  - multiple children
 *  - multiple parents
 */
trait SimpleGraphItem extends GraphItem {

  def simpleGraphItemId = "!id!"

  override def key = Map(simpleGraphItemId -> simpleKey)

  def simpleKey: String

  /**
   * @return child graph item with this specific key.
   */
  def apply(s: String): GraphItem = {
    children(Map(simpleGraphItemId -> s))
  }

}