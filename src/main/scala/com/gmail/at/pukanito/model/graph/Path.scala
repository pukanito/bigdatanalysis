package com.gmail.at.pukanito.model.graph

import scala.collection.SeqLike
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Builder

/**
 * Representation of a relative or absolute path through GraphItems.
 *
 * @param path the keys representing the path.
 */
class Path private (
  path: NodeKey*
) extends Seq[NodeKey] with SeqLike[NodeKey, Path] {

  private val pathElements = Seq[NodeKey](path:_*)

  def apply(idx: Int): NodeKey = pathElements(idx)

  def iterator: Iterator[NodeKey] = pathElements.iterator

  def length: Int = pathElements.length

  override protected[this] def newBuilder: Builder[NodeKey, Path] =
    Path.newBuilder

  /**
   * Appends another path to this path.
   *
   * @param p the other path to append.
   * @return a new path consisting of this path with p appended.
   */
  def +(p: Path) = {
    new Path((this.pathElements ++ p.pathElements):_*)
  }
}

/**
 * Types and helper methods for Path.
 *
 * Creating a graph path from simple keys:
 * {{{
 * val path = Path("key1" -> val1 [ , "key2" -> val2, ... ] )
 * }}}
 *
 * instead of:
 *
 * {{{
 * val path = Path(NodeKey("key1" -> val1) [ , NodeKey("key2" -> val2), ... ] )
 * }}}
 *
 * With compound keys it is necessary to use NodeKey(...).
 */
object Path {

  import scala.language.implicitConversions

  /**
   * Helper to convert a NodeKey to a Path (for implicit conversion).
   *
   * @param key graph item key to convert.
   * @return a Path
   */
  implicit def nodeKey2Path(key: NodeKey): Path = {
    Path(key)
  }

  /**
   * Helper to create a Path from keys.
   *
   * @param keys maps containing the keys of each level in the path to be created.
   * @return a Path with the specified keys.
   */
  def apply(keys: NodeKey*): Path = {
    new Path(keys:_*)
  }

  /**
   * Helper for match ... case extraction.
   */
  def unapplySeq(x: Seq[NodeKey]): Option[Seq[NodeKey]] = Some(x)

  def fromSeq(buf: Seq[NodeKey]): Path = {
    new Path(buf:_*)
  }

  def newBuilder: Builder[NodeKey, Path] =
    new ArrayBuffer mapResult fromSeq
}
