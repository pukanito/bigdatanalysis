package com.gmail.at.pukanito.model.container

import GraphPath._

/**
 * Representation of a relative or absolute path through GraphItems.
 *
 * @param path the keys representing the path.
 */
abstract class GraphPath (val path: GraphItemKey*){

  /**
   * @return the depth of the path.
   */
  def size = path.size

  /**
   * @return whether this is an absolute path (true) or not (false).
   */
  def isAbsolute: Boolean

  /**
   * @return whether this is a root path (true) or not (false).
   */
  def isRoot = isAbsolute && (size == 0)
}

/**
 * Types and helper methods for GraphPath.
 */
object GraphPath {
  type GraphItemKey = Map[String, Any]
}

/**
 * Representation of an absolute path.
 *
 * @constructor Create an absolute path.
 * @param rawPath the keys representing the path.
 */
class AbsolutePath(rawPath: GraphItemKey*) extends GraphPath(rawPath: _*) {

  /**
   * Append a relative path to this path.
   *
   * @param p the relative path to append.
   * @return a new absolute path consisting of the absolute path with p appended.
   */
  def +(p: RelativePath): AbsolutePath = {
    new AbsolutePath((this.path ++ p.path): _*)
  }

  val isAbsolute = true
}

/**
 * Representation of a relative path.
 *
 * @constructor Create a relative path.
 * @param p the keys representing the path.
 */
class RelativePath(p: GraphItemKey*) extends GraphPath(p: _*) {

  /**
   * Append a relative path to this path.
   *
   * @param p the relative path to append.
   * @return a new relative path consisting of the relative path with p appended.
   */
  def +(p: RelativePath): RelativePath = {
    new RelativePath((this.path ++ p.path): _*)
  }

  val isAbsolute = false
}
