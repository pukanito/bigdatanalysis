package com.gmail.at.pukanito.model.store

import com.gmail.at.pukanito.model.container.{GraphItem,GraphItemKey}

/**
 * Simple in memory storage.
 */
class MemoryMapGraphItemStore[T] extends GraphItemStore[T] {

  var items: Map[GraphItemKey, GraphItem[T]] = Map.empty

  def put(values: GraphItem[T]*) = {
    items ++= { values map (x => (x.key, x)) }
  }

  def apply(keys: GraphItemKey*): Set[GraphItem[T]] = {
    (for (k <- keys ) yield items(k)) (collection.breakOut)
  }

  def get(keys: GraphItemKey*): Set[Option[GraphItem[T]]] = {
    (for (k <- keys ) yield items.get(k)) (collection.breakOut)
  }

  def contains(keys: GraphItemKey*): Map[GraphItemKey, Boolean] = {
    (keys map { v => (v, items contains v) }) (collection.breakOut)
  }

  def delete(keys: GraphItemKey*) = {
    items --= keys
  }

}