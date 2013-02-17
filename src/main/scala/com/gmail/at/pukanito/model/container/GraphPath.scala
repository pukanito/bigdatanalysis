package com.gmail.at.pukanito.model.container

/**
 * Representation of a relative or absolute path through GraphItems.
 *
 * @param path the keys representing the path.
 */
class GraphPath (val path: GraphItemKey*){

  /**
   * @return the depth of the path.
   */
  def size = path.size

  /**
   * @return the first item of the path.
   */
  def head = path.head

  /**
   * @return the path except the first item.
   */
  def tail = new GraphPath(path.tail:_*)

  /**
   * @return the path as a list of GraphItemKeys which can
   *   be handy for pattern matching.
   */
  def toList = path.toList

  /**
   * Append another path to this path.
   *
   * @param p the other path to append.
   * @return a new path consisting of this path with p appended.
   */
  def +(p: GraphPath) = {
    new GraphPath((this.path ++ p.path):_*)
  }

  override def equals(other: Any): Boolean = {
    other match {
      case that: GraphPath =>
        (that canEqual this) &&
        that.path == this.path
      case _ => false
    }
  }

  override def hashCode: Int = path.hashCode

  def canEqual(other: Any): Boolean = other.isInstanceOf[GraphPath]

  override def toString: String = {
    path.mkString("GraphPath(", ",", ")")
  }

}

/**
 * Types and helper methods for GraphPath.
 */
object GraphPath {

  /**
   * Helper to convert a GraphItemKey to a GraphPath (for implicit conversion).
   *
   * @param key graph item key to convert.
   * @return a GraphPath
   */
  implicit def graphItemKey2GraphPath(key: GraphItemKey): GraphPath = {
    GraphPath(key)
  }

  /**
   * Helper to create a GraphPath from keys.
   *
   * @param keys maps containing the keys of each level in the path to be created.
   * @return a GraphPath with the specified keys.
   */
  def apply(keys: GraphItemKey*): GraphPath = {
    new GraphPath(keys:_*)
  }

  /**
   * Convenience helper to create a GraphPath from a list of keys. Helpful after extraction
   * of part of a path to create a new path without ':_*'.
   *
   * @param keys list of maps containing the keys of each level in the path to be created.
   * @return a GraphPath with the specified keys.
   */
  def apply(keys: List[GraphItemKey]): GraphPath = {
    new GraphPath(keys:_*)
  }


}
