package com.gmail.at.pukanito.model.container

import collection.SeqLike
import collection.mutable.Builder
import collection.mutable.ArrayBuffer

/**
 * Representation of a relative or absolute path through GraphItems.
 *
 * @param path the keys representing the path.
 */
class GraphPath (
  path: GraphItemKey*
) extends Seq[GraphItemKey] with SeqLike[GraphItemKey, GraphPath] {

  private val graphPathElements = Seq[GraphItemKey](path:_*)

  def apply(idx: Int): GraphItemKey = graphPathElements(idx)

  def iterator: Iterator[GraphItemKey] = graphPathElements.iterator

  def length: Int = graphPathElements.length

  override protected[this] def newBuilder: Builder[GraphItemKey, GraphPath] =
    GraphPath.newBuilder

  /**
   * Append another path to this path.
   *
   * @param p the other path to append.
   * @return a new path consisting of this path with p appended.
   */
  def +(p: GraphPath) = {
    new GraphPath((this.graphPathElements ++ p.graphPathElements):_*)
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

  def fromSeq(buf: Seq[GraphItemKey]): GraphPath = {
    new GraphPath(buf:_*)
  }

  def newBuilder: Builder[GraphItemKey, GraphPath] =
    new ArrayBuffer mapResult fromSeq
}
