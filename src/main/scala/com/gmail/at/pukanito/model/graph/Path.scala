package com.gmail.at.pukanito.model.graph

import scala.collection.SeqLike
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Builder

/**
 * Representation of a relative or absolute path through GraphItems.
 *
 * A path is a sequence of NodeKeys.
 */
abstract class Path extends Seq[NodeKey] with SeqLike[NodeKey, Path] {

  override protected[this] def newBuilder: Builder[NodeKey, Path] = Path.newBuilder

  /**
   * Appends another path to this path.
   *
   * @param p the other path to append.
   * @return a new path consisting of this path with p appended.
   */
  // scalastyle:off method.name spaces.after.plus
  def +(p: Path): Path
  // scalastyle:on method.name spaces.after.plus
}

/**
 * Implementation for the empty path.
 */
case class EmptyPath private[graph] () extends Path {

  override def apply(idx: Int): NodeKey = throw new NoSuchElementException

  override def iterator: Iterator[NodeKey] = Iterator.empty

  override def length: Int = 0

  // scalastyle:off method.name spaces.after.plus public.methods.have.type
  override def +(p: Path) = p
  // scalastyle:on method.name spaces.after.plus public.methods.have.type
}

/**
 * Implementation for the non-empty path.
 *
 * @param path the keys representing the path.
 */
case class NonEmptyPath private[graph] (
  path: NodeKey*
) extends Path {

  val pathElements = Seq[NodeKey](path:_*)

  override def apply(idx: Int): NodeKey = pathElements(idx)

  override def iterator: Iterator[NodeKey] = pathElements.iterator

  override def length: Int = pathElements.length

  // scalastyle:off method.name spaces.after.plus public.methods.have.type
  override def +(p: Path) = p match {
  // scalastyle:on method.name spaces.after.plus public.methods.have.type
    case _: EmptyPath => this
    case that: NonEmptyPath => new NonEmptyPath((this.pathElements ++ that.pathElements):_*)
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
  implicit def nodeKey2Path(key: NodeKey): Path = Path(key)

  /**
   * Helper to create a Path from keys.
   *
   * @param keys maps containing the keys of each level in the path to be created.
   * @return a Path with the specified keys.
   */
  def apply(keys: NodeKey*): Path = keys.size match {
    case 0 => new EmptyPath
    case _ => new NonEmptyPath(keys:_*)
  }

  /**
   * Helper for match ... case extraction.
   */
  def unapplySeq(x: Seq[NodeKey]): Option[Seq[NodeKey]] = Some(x)

  def fromSeq(buf: Seq[NodeKey]): Path = apply(buf:_*)

  /**
   * Helper for creating new instances.
   */
  def newBuilder: Builder[NodeKey, Path] = new ArrayBuffer mapResult fromSeq
}
