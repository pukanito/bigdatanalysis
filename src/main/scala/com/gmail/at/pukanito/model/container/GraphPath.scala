package com.gmail.at.pukanito.model.container

import scala.collection.SeqLike
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Builder

/**
 * Representation of a relative or absolute path through GraphItems.
 *
 * @param path the keys representing the path.
 */
class GraphPath private (
  path: GraphItemKey*
) extends Seq[GraphItemKey] with SeqLike[GraphItemKey, GraphPath] {

  private val graphPathElements = Seq[GraphItemKey](path:_*)

  def apply(idx: Int): GraphItemKey = graphPathElements(idx)

  def iterator: Iterator[GraphItemKey] = graphPathElements.iterator

  def length: Int = graphPathElements.length

  override protected[this] def newBuilder: Builder[GraphItemKey, GraphPath] =
    GraphPath.newBuilder

  /**
   * Appends another path to this path.
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
 *
 * Creating a graph path from simple keys:
 * {{{
 * val path = GraphPath("key1" -> val1 [ , "key2" -> val2, ... ] )
 * }}}
 *
 * instead of:
 *
 * {{{
 * val path = GraphPath(GraphItemKey("key1" -> val1) [ , GraphItemKey("key2" -> val2), ... ] )
 * }}}
 *
 * With compound keys it is necessary to use GraphItemKey(...).
 */
object GraphPath {

  import scala.language.implicitConversions

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
   * Helper for match ... case extraction.
   */
  def unapplySeq(x: Seq[GraphItemKey]): Option[Seq[GraphItemKey]] = Some(x)

  def fromSeq(buf: Seq[GraphItemKey]): GraphPath = {
    new GraphPath(buf:_*)
  }

  def newBuilder: Builder[GraphItemKey, GraphPath] =
    new ArrayBuffer mapResult fromSeq
}
