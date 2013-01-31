package com.gmail.at.pukanito.model.container

import collection._

/**
 * Representation of the keys in a GraphPath.
 *
 * @param elems the keys representing a level of a path.
 */
class GraphItemKey private (
  elems: (String, Any)*
) extends Map[String, Any] with MapLike[String, Any, GraphItemKey] {

  val graphItemKeys = Map[String, Any](elems:_*)

  override def get(key: String): Option[Any] =
    if (key.isEmpty) None
    else graphItemKeys get key

  override def +[B1 >: Any](kv: (String, B1)): Map[String, B1] = {
    new GraphItemKey((graphItemKeys + kv).toSeq:_*)
  }

  override def -(key: String): GraphItemKey = {
    new GraphItemKey((graphItemKeys - key).toSeq:_*)
  }

  override def iterator: Iterator[(String, Any)] = {
    graphItemKeys.iterator
  }

  override def empty = new GraphItemKey
}

/**
 * Helpers for GraphItemKey
 */
object GraphItemKey extends {

  private def simpleGraphItemId = "!id!"

  /**
   * Helper to convert String to a GraphItemKey (for implicit conversion).
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
  implicit def string2GraphItemKey(key: (String, Any)): GraphItemKey = {
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