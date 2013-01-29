package com.gmail.at.pukanito.model.container

import collection._

class GraphItemKey private (
  elems: (String, Any)*
) extends Map[String, Any] with MapLike[String, Any, GraphItemKey] {

  val graphItemKeys = Map[String, Any](elems:_*)

  def get(key: String): Option[Any] =
    if (key.isEmpty) None
    else Some(graphItemKeys(key))

  def +[B1 >: Any](kv: (String, B1)): Map[String, B1] = {
    new GraphItemKey((graphItemKeys + kv).toSeq:_*)
  }

  def -(key: String): GraphItemKey = {
    new GraphItemKey((graphItemKeys - key).toSeq:_*)
  }

  def iterator: Iterator[(String, Any)] = {
    graphItemKeys.iterator
  }

  override def empty = new GraphItemKey
}

object GraphItemKey extends {

  private def simpleGraphItemId = "!id!"

  def empty = new GraphItemKey

  /**
   * Helper to convert String to a GraphItemKey.
   *
   * @param key string to use as simple key.
   * @return a GraphItemKey
   */
  implicit def string2GraphItemKey(key: String): GraphItemKey = {
    GraphItemKey(simpleGraphItemId -> key)
  }

  def apply(elems: (String, Any)*): GraphItemKey = {
    new GraphItemKey(elems:_*)
  }

}