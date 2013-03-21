package com.gmail.at.pukanito.model.graph

/**
 * Exception thrown when a child node is added to another node and the child node is
 * the same object as the parent node or one of its (in)direct parents.
 *
 * @constructor Creates a graph cycle exception.
 * @param value The node that was added and already exists as a (in)direct parent.
 */
class GraphCycleException(value: Node[_])
  extends RuntimeException(s"Cycle in graph with: $value") {}

/**
 * Exception thrown when a child is added to another node with an already existing key.
 *
 * @constructor Creates a duplicate child node exception.
 * @param value The node that was added and has an already existing key.
 */
class DuplicateChildNodeException(value: Node[_])
  extends RuntimeException(s"Duplicate graph item: $value") {}

/**
 * Makes objects Graph compatible.
 *
 * A node can have:
 *  - compound key
 *  - multiple children
 *  - multiple parents
 *
 * A node is an object that can have parent-child relations with other objects
 * of the same type. Child objects are identified by a (compound) key. A child object
 * can appear multiple times in the same graph as long as no cycles are introduced and
 * it can only appear once within a certain parent object.
 *
 * To make a class a Graph compatible:
 *
 * {{{
 * class MyClass(<optional parameters>) extends Node[MyClass] {
 *   ...
 * }
 * }}}
 *
 * @param T the type that can be contained within this node.
 */
trait Node[T <: Node[T]] {
  this: T =>

  private var parentValues: Set[T] = Set[T]()
  private[this] var childrenMap: Map[NodeKey, T] = Map[NodeKey, T]()

  /**
   * Returns the key of a node. Should be immutable!
   */
  def key: NodeKey

  /**
   * Returns a copy of this object.
   */
  def copy: T

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
   * Returns a copy of this object and all its children.
   */
  def copyGraph: T = {
    val item = this.copy
    childrenMap.values foreach { item += _.copyGraph }
    item
  }

  /**
   * Returns the path(s) of the node.
   */
  def paths: Set[Path] = {
    if (parents.size == 0)
      Set(Path(key))
    else
      parents flatMap { _.paths } map { _ + Path(key) }
  }

  /**
   * Returns a path of the node. If multiple paths exist one of them is returned.
   */
  def path: Path = {
    if (parents.size == 0)
      Path(key)
    else
      parents.head.path + Path(key)
  }

  /**
   * Returns the set of parent nodes of this node, empty when this is a root item.
   */
  def parents: Set[T] = parentValues

  /**
   * Returns the map of child nodes of this node.
   */
  def children: Map[NodeKey, T] = childrenMap

  /**
   * Adds a new child node to this node.
   *
   * @param child The child to add.
   * @throws GraphCycleException when a cycle is detected when the value would be added.
   * @throws DuplicateGraphItemException when an item with the same key already exists.
   */
  def +=(child: T) = {
    if (testCycleExistsInParents(List(this), child)) throw new GraphCycleException(child)
    if (childrenMap contains child.key) throw new DuplicateChildNodeException(child)
    childrenMap += (child.key -> child)
    child.parentValues += this
  }

  /**
   * Returns the node at the end of the specified path.
   *
   * @param path path to the node.
   * @throws NoSuchElementException if a node in the path does not exist.
   */
  def apply(path: Path): T = {
    if (path.size == 0)
      this
    else
      children(path.head)(path.tail)
  }

}
