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

  private var parentNodes: Set[T] = Set[T]()
  private[this] var childNodes: Map[NodeKey, T] = Map[NodeKey, T]()
  private var container: Option[Graph] = None

  /**
   * Returns the key of a node. Should be immutable!
   */
  def key: NodeKey

  /**
   * Returns a copy of this object.
   */
  def copy: T

  /**
   * Returns true if child node is equal (object equality) to one of the nodes or one
   * of nodes' parents. This also checks if one of the children of the child node is object
   * equal to one of the nodes or one of nodes' parents because if this is the case than
   * the child node itself will also be one of the parents.
   *
   * @param nodes the nodes to check (also check nodes' parents).
   * @param childNode the node that may not be object equal to one of the nodes or its parents.
   */
  private def testCycleExistsInParents(nodes: List[T], childNode: T): Boolean = {
    if (nodes.isEmpty)
      false
    else
      nodes.exists( x => (x eq childNode) || testCycleExistsInParents(x.parents.toList, childNode) )
  }

  /**
   * Returns a copy of this object and all its children.
   */
  def copyGraph: T = {
    val item = this.copy
    childNodes.values foreach { item += _.copyGraph }
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
  def parents: Set[T] = parentNodes

  /**
   * Returns the map of child nodes of this node.
   */
  def children: Map[NodeKey, T] = childNodes

  /**
   * Adds a new child node to this node.
   *
   * @param child The child to add.
   * @throws GraphCycleException when a cycle is detected when the value would be added.
   * @throws DuplicateGraphItemException when an item with the same key already exists.
   */
  def +=(childNode: T) = {
    if (testCycleExistsInParents(List(this), childNode)) throw new GraphCycleException(childNode)
    if (childNodes contains childNode.key) throw new DuplicateChildNodeException(childNode)
    childNodes += (childNode.key -> childNode)
    childNode.parentNodes += this
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
