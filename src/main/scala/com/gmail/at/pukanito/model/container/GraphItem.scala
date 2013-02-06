package com.gmail.at.pukanito.model.container

/**
 * Exception thrown when an item is added to a GraphItem and the item is the same object
 * as the GraphItem or one of its (in)direct parents.
 *
 * @constructor Create a graph cycle exception.
 * @param value The duplicate graph item.
 */
class GraphCycleException(value: GraphItem[_])
  extends RuntimeException("Cycle in graph with: " + value) {}

/**
 * Exception thrown when an item is added to a GraphItem with an already existing key.
 *
 * @constructor Create a duplicate item exception.
 * @param value The item that failed to added.
 */
class DuplicateGraphItemException(value: GraphItem[_])
  extends RuntimeException("Duplicate graph item: " + value) {}

/**
 * Trait for making items graph item compatible.
 *
 * GraphItem can have:
 *  - compound key
 *  - multiple children
 *  - multiple parents
 *
 *  @param T the type that can be contained with this graph item.
 *  @param initialChildren a list of the initial children of this graph item.
 *  @param initialParents a list of the initial parents of this graph item.
 */
abstract class GraphItem[T](
  initialChildren: Set[GraphItem[T]] = Set[GraphItem[T]](),
  initialParents: Set[GraphItem[T]] = Set[GraphItem[T]]()
) {
  this: GraphItem[T] =>

  private var parentValues: Set[GraphItem[T]] = Set[GraphItem[T]]()
  private[this] var childrenMap: Map[GraphItemKey, GraphItem[T]] = Map[GraphItemKey, GraphItem[T]]()

  // Before adding initial children and parents, check for cycles and duplicates.
  testCycleExistsInParents(initialParents, this)
  initialChildren foreach (testCycleExistsInParents(Set(this), _))
  initialChildren foreach (testCycleExistsInParents(initialParents, _))
  initialChildren foreach (
    (x) => initialChildren foreach (
      (y) => if (!(x eq y) && (x.key == y.key)) throw new DuplicateGraphItemException(x)))
  initialParents foreach ((x) => if (x.children contains this.key) throw new DuplicateGraphItemException(x))

  // No cycles or duplicates found, go ahead and add.
  initialParents foreach (_.addWithoutException(this))
  initialChildren foreach (addWithoutException(_))

  /**
   * Check if child item is equal to one of the items or one of items' parents. This also
   * checks if one of the children of the child item is equal to one of the items or
   * one of items' parents because if this is the case than the child item itself will
   * also be one of the parents.
   *
   * @param items the items to check (also check items' parents).
   * @param childItem the child item to check against.
   */
  private def testCycleExistsInParents(items: Set[GraphItem[T]], childItem: GraphItem[T]): Boolean = {
    if (items.isEmpty)
      false
    else
      items.exists( x => (x eq childItem) || testCycleExistsInParents(x.parents, childItem) )
  }

  /**
   * Add a child item without checking for cycles or duplicate keys.
   *
   * @param childItem The child to add.
   */
  private def addWithoutException(childItem: GraphItem[T]) = {
    childrenMap += (childItem.key -> childItem)
    childItem.parentValues += this
  }

  /**
   * @return the key of a graph item. Should be immutable!
   */
  def key: GraphItemKey

  /**
   * @return set of parent graph items of this graph item, empty when this is a root item.
   */
  def parents: Set[GraphItem[T]] = parentValues

  /**
   * @return map of child graph items of this graph item.
   */
  def children: Map[GraphItemKey, GraphItem[T]] = childrenMap

  /**
   * Add a new child to this graph item.
   *
   * @param childItem The child to add.
   * @throws GraphCycleException when a cycle is detected when the value would be added.
   * @throws DuplicateGraphItemException when an item with the same key already exists.
   */
  def +=(childItem: GraphItem[T]) = {
    if (testCycleExistsInParents(Set(this), childItem)) throw new GraphCycleException(childItem)
    if (childrenMap contains childItem.key) throw new DuplicateGraphItemException(childItem)
    addWithoutException(childItem)
  }

  /**
   * Get a specific item in the graph.
   *
   * @param path path to the child item.
   * @return a child item according to the specified path.
   * @throws NoSuchElementException if an item in the path does not exist.
   */
  def apply(path: GraphPath): GraphItem[T] = {
    if (path.size == 0)
      this
    else
      children(path.head)(path.tail)
  }

}
