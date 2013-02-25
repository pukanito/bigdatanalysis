package com.gmail.at.pukanito.model.container

import collection._

/**
 * Represents the (compound) key of a [[com.gmail.at.pukanito.model.container.GraphItem]].
 *
 * The key is implemented as a wrapper around Map[String, Any]
 *
 * @param elems the keys representing a level of a path.
 */
class GraphItemKey private (
  elems: (String, Any)*
) extends Map[String, Any] with MapLike[String, Any, GraphItemKey] {

  private val graphItemKeyElements = Map[String, Any](elems:_*)

  override def get(key: String): Option[Any] =
    if (key.isEmpty) None
    else graphItemKeyElements get key

  override def +[B1 >: Any](kv: (String, B1)): GraphItemKey = {
    new GraphItemKey((graphItemKeyElements + kv).toSeq:_*)
  }

  override def -(key: String): GraphItemKey = {
    new GraphItemKey((graphItemKeyElements - key).toSeq:_*)
  }

  override def iterator: Iterator[(String, Any)] = {
    graphItemKeyElements.iterator
  }

  override def stringPrefix: String = "GraphItemKey"

  override def empty = new GraphItemKey
}

/**
 * Helpers for creating GraphItemKeys.
 *
 * Creating graph item keys with a simple string value key
 * will create a key that internally looks like: "!id!" -> "stringvalue":
 * {{{
 * val key = GraphItemKey("stringvalue")
 * }}}
 *
 * Creating graph item keys with compound keys:
 * {{{
 * val key = GraphItemKey("id1" -> val1 [ , "id2" -> val2, ... ] )
 * }}}
 */
object GraphItemKey extends {

  // When using non-compound keys a standard String can be used in the map.
  private def simpleGraphItemId = "!id!"

  /**
   * Helps to convert String to a GraphItemKey (for implicit conversion).
   *
   * @param key string to use as simple key.
   * @return a GraphItemKey
   */
  implicit def string2GraphItemKey(key: String): GraphItemKey = {
    GraphItemKey(simpleGraphItemId -> key)
  }

  /**
   * Helper to convert Tuple to a GraphItemKey (for implicit conversion).
   *
   * @param key tuple to use as a key.
   * @return a GraphItemKey
   */
  implicit def tuple2GraphItemKey(key: (String, Any)): GraphItemKey = {
    GraphItemKey(key._1 -> key._2)
  }

  /**
   * Helper to create a new GraphItemKey.
   *
   * @param elems the keys representing a level of a path.
   */
  def apply(elems: (String, Any)*): GraphItemKey = {
    new GraphItemKey(elems:_*)
  }

}