package com.gmail.at.pukanito.model.container

/**
 * Exception thrown when an item is added to a GraphItem and the item is the same object
 * as the GraphItem or one of its (in)direct parents.
 *
 * @constructor Creates a graph cycle exception.
 * @param value The item that was added and already exists as a (in)direct parent.
 */
class GraphCycleException(value: GraphItem[_])
  extends RuntimeException("Cycle in graph with: " + value) {}

/**
 * Exception thrown when an item is added to a GraphItem with an already existing key.
 *
 * @constructor Creates a duplicate item exception.
 * @param value The item that was added and has an already existing key.
 */
class DuplicateGraphItemException(value: GraphItem[_])
  extends RuntimeException("Duplicate graph item: " + value) {}

/**
 * Makes objects graph item compatible.
 *
 * GraphItem can have:
 *  - compound key
 *  - multiple children
 *  - multiple parents
 *
 * A graph item is an object that can have parent-child relations with other objects
 * of the same type. Child objects are identified by a (compound) key. A child object
 * can appear multiple times in the same graph as long as no cycles are introduced and
 * it can only appear once within a certain parent object.
 *
 * To make a class a GraphItem:
 *
 * {{{
 * class MyClass(<optional parameters>) extends GraphItem[MyClass] {
 *   ...
 * }
 * }}}
 *
 * or, when using initial children and/or parents (with default values):
 *
 * {{{
 * class MyClass(<optional parameters>,
 *   initialChildren: List[MyClass] = Nil,
 *   initialParents: List[MyClass] = Nil
 * ) extends GraphItem[MyClass](initialChildren, initialParents) {
 *   ...
 * }
 * }}}
 *
 * @constructor Creates a graph item with specified children and/or parents.
 * @param T the type that can be contained within this graph item.
 * @param initialChildren a list of the initial children of this graph item.
 * @param initialParents a list of the initial parents of this graph item.
 * @throws GraphCycleException when a cycle is detected when the item would be created.
 * @throws DuplicateGraphItemException when duplicate item would be detected.
 */
abstract class GraphItem[T <: GraphItem[T]](
  initialChildren: List[T] = Nil,
  initialParents: List[T] = Nil
) {
  this: T =>

  private var parentValues: Set[T] = Set[T]()
  private[this] var childrenMap: Map[GraphItemKey, T] = Map[GraphItemKey, T]()

  // Before adding initial children and parents, check for cycles and duplicates.
  testCycleExistsInParents(initialParents, this)
  initialChildren foreach (testCycleExistsInParents(List(this), _))
  initialChildren foreach (testCycleExistsInParents(initialParents, _))
  initialChildren foreach (
    (x) => initialChildren foreach (
      (y) => if (!(x eq y) && (x.key == y.key)) throw new DuplicateGraphItemException(x)))
  initialParents foreach ((x) => if (x.children contains this.key) throw new DuplicateGraphItemException(x))

  // No cycles or duplicates found, go ahead and add.
  initialParents foreach (_.addWithoutException(this))
  initialChildren foreach (addWithoutException(_))

  /**
   * Returns true if child item is equal (object equality) to one of the items or one
   * of items' parents. This also checks if one of the children of the child item is object
   * equal to one of the items or one of items' parents because if this is the case than
   * the child item itself will also be one of the parents.
   *
   * @param items the items to check (also check items' parents).
   * @param childItem the item that may not be object equal to one of the items or its parents.
   */
  private def testCycleExistsInParents(items: List[T], childItem: T): Boolean = {
    if (items.isEmpty)
      false
    else
      items.exists( x => (x eq childItem) || testCycleExistsInParents(x.parents.toList, childItem) )
  }

  /**
   * Returns the key of a graph item. Should be immutable!
   */
  def key: GraphItemKey

  /**
   * Returns a copy of this object.
   */
  def copy: T

  /**
   * Returns a copy of this object and all its children.
   */
  def copyGraph: T = {
    val item = this.copy
    childrenMap.values foreach { item += _.copyGraph }
    item
  }

  /**
   * Adds a child item without checking for cycles or duplicate keys.
   *
   * @param childItem The child to add.
   */
  private def addWithoutException(childItem: T) = {
    childrenMap += (childItem.key -> childItem)
    childItem.parentValues += this
  }

  /**
   * Returns the path(s) of the graph item.
   */
  def paths: Set[GraphPath] = {
    if (parents.size == 0)
      Set(GraphPath(key))
    else
      parents flatMap { _.paths } map { _ + GraphPath(key) }
  }

  /**
   * Returns a path of the graph item. If multiple paths exist one of them is returned.
   */
  def path: GraphPath = {
    if (parents.size == 0)
      GraphPath(key)
    else
      parents.first.path + GraphPath(key)
  }

  /**
   * Returns the set of parent graph items of this graph item, empty when this is a root item.
   */
  def parents: Set[T] = parentValues

  /**
   * Returns the map of child graph items of this graph item.
   */
  def children: Map[GraphItemKey, T] = childrenMap

  /**
   * Adds a new child to this graph item.
   *
   * @param childItem The child to add.
   * @throws GraphCycleException when a cycle is detected when the value would be added.
   * @throws DuplicateGraphItemException when an item with the same key already exists.
   */
  def +=(childItem: T) = {
    if (testCycleExistsInParents(List(this), childItem)) throw new GraphCycleException(childItem)
    if (childrenMap contains childItem.key) throw new DuplicateGraphItemException(childItem)
    addWithoutException(childItem)
  }

  /**
   * Returns the item at the end of the specified graph path.
   *
   * @param path path to the item.
   * @throws NoSuchElementException if an item in the path does not exist.
   */
  def apply(path: GraphPath): T = {
    if (path.size == 0)
      this
    else
      children(path.head)(path.tail)
  }

}
