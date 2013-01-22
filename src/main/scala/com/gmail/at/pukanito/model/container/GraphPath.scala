package com.gmail.at.pukanito.model.container

import GraphPath._

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
  def tail = new GraphPath(path.tail: _*)

  /**
   * Append another path to this path.
   *
   * @param p the other path to append.
   * @return a new path consisting of this path with p appended.
   */
  def +(p: GraphPath) = {
    new GraphPath((this.path ++ p.path): _*)
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

}

/**
 * Types and helper methods for GraphPath.
 */
object GraphPath {
  def simpleGraphItemId = "!id!"
  type GraphItemKey = Map[String, Any]

  /**
   * @return a GraphPath with the specified keys.
   */
  def apply(path: GraphItemKey*): GraphPath = {
    new GraphPath(path: _*)
  }

  /**
   * @return a GraphPath with keys made from the specified Strings (simple GraphPath).
   */
  def apply(p: String, path: String*): GraphPath = {
    new GraphPath((p +: path).map(x => Map(simpleGraphItemId -> x)): _*)
  }
}
