package com.gmail.at.pukanito.model.container

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
   * @param childItem The child to add.
   * @throws GraphCycleException when a cycle is detected when the value would be added.
   * @throws DuplicateGraphItemException when an item with the same key already exists.
   */
  def +=(childItem: GraphItem) = {
    def testCycleExists(items: Set[GraphItem]): Boolean = {
      if (items.isEmpty) false else items.exists( x => (x eq childItem) || testCycleExists(x.parents) )
    }
    if (testCycleExists(Set(this))) throw new GraphCycleException(childItem)
    if (childrenMap contains childItem.key) throw new DuplicateGraphItemException(childItem)
    childrenMap += (childItem.key -> childItem)
    childItem.parentValues += this
  }

  /**
   * Get a specific item in the graph.
   *
   * @param path path to the child item.
   * @return a child item according to the specified path.
   * @throws NoSuchElementException if an item in the path does not exist.
   */
  def apply(path: GraphPath): GraphItem = {
    if (path.size == 0)
      this
    else
      children(path.head)(path.tail)
  }

}
