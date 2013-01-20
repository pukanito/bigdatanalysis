package com.gmail.at.pukanito.model.container

import GraphPath.GraphItemKey

/**
 * Representation of a relative or absolute path through GraphItems.
 *
 * @constructor Create a path
 * @param keys the keys representing the path (can contain "." and ".." keys)
 */
abstract class GraphPath (keys: GraphItemKey*){

  protected val normalizedPath = keys

  /**
   * @return the normalized path (without "." and ".." keys)
   */
  def path = normalizedPath

  /**
   * @return the depth of the path.
   */
  def size = normalizedPath.size

  /**
   * @return whether this is an absolute path (true) or not (false).
   */
  def isAbsolute: Boolean

  /**
   * @return whether this is a root path (true) or not (false).
   */
  def isRoot = isAbsolute && (normalizedPath.size == 0)
}

object GraphPath {
  type GraphItemKey = Map[String, Any]
}

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

class RelativePath(rawPath: GraphItemKey*) extends GraphPath(rawPath: _*) {

  /**
   * Append a relative path to this path.
   *
   * @param p the relative path to append.
   * @return a new relative path consisting of the relative paht with p appended.
   */
  def +(p: RelativePath): RelativePath = {
    new RelativePath((this.path ++ p.path): _*)
  }

  val isAbsolute = false
}
