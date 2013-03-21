package com.gmail.at.pukanito.model.graph

import scala.collection.Iterator
import scala.collection.Map
import scala.collection.MapLike

/**
 * Represents the (compound) key of a [[com.gmail.at.pukanito.model.graph.GraphItem]].
 *
 * The key is implemented as a wrapper around Map[String, Any]
 *
 * @param elems the keys representing a level of a path.
 */
class NodeKey private (
  elems: (String, Any)*
) extends Map[String, Any] with MapLike[String, Any, NodeKey] {

  private val graphItemKeyElements = Map[String, Any](elems:_*)

  override def get(key: String): Option[Any] =
    if (key.isEmpty) None
    else graphItemKeyElements get key

  override def +[B1 >: Any](kv: (String, B1)): NodeKey = {
    new NodeKey((graphItemKeyElements + kv).toSeq:_*)
  }

  override def -(key: String): NodeKey = {
    new NodeKey((graphItemKeyElements - key).toSeq:_*)
  }

  override def iterator: Iterator[(String, Any)] = {
    graphItemKeyElements.iterator
  }

  override def stringPrefix: String = "NodeKey"

  override def empty = new NodeKey
}

/**
 * Helpers for creating GraphItemKeys.
 *
 * Creating graph item keys with a simple string value key
 * will create a key that internally looks like: "!id!" -> "stringvalue":
 * {{{
 * val key = NodeKey("stringvalue")
 * }}}
 *
 * Creating graph item keys with compound keys:
 * {{{
 * val key = NodeKey("id1" -> val1 [ , "id2" -> val2, ... ] )
 * }}}
 */
object NodeKey extends {

  import scala.language.implicitConversions

  // When using non-compound keys a standard String can be used in the map.
  private def simpleGraphItemId = "!id!"

  /**
   * Helps to convert String to a NodeKey (for implicit conversion).
   *
   * @param key string to use as simple key.
   * @return a NodeKey
   */
  implicit def string2GraphItemKey(key: String): NodeKey = {
    NodeKey(simpleGraphItemId -> key)
  }

  /**
   * Helper to convert Tuple to a NodeKey (for implicit conversion).
   *
   * @param key tuple to use as a key.
   * @return a NodeKey
   */
  implicit def tuple2GraphItemKey(key: (String, Any)): NodeKey = {
    NodeKey(key._1 -> key._2)
  }

  /**
   * Helper to create a new NodeKey.
   *
   * @param elems the keys representing a level of a path.
   */
  def apply(elems: (String, Any)*): NodeKey = {
    new NodeKey(elems:_*)
  }

}