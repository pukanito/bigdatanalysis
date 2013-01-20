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
   * Append another path to this path.
   *
   * @param p the other path to append.
   * @return a new path consisting of this path with p appended.
   */
  def +(p: GraphPath): GraphPath = {
    new GraphPath((this.path ++ p.path): _*)
  }

}

/**
 * Types and helper methods for GraphPath.
 */
object GraphPath {
  type GraphItemKey = Map[String, Any]
}
