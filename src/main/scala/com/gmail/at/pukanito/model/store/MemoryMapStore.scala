package com.gmail.at.pukanito.model.store

import com.gmail.at.pukanito.model.container.{GraphItem,GraphItemKey}

/**
 * Simple in memory storage.
 */
class MemoryMapGraphItemStore[T <: GraphItem[T]] extends GraphItemStore[T] {

  private var items: Map[GraphItemKey, T] = Map.empty

  def put(values: T*) = {
    items ++= { values map (x => (x.key, x)) }
  }

  def apply(keys: GraphItemKey*): Set[T] = {
    (for (k <- keys ) yield items(k)) (collection.breakOut)
  }

  def get(keys: GraphItemKey*): Set[Option[T]] = {
    (for (k <- keys ) yield items.get(k)) (collection.breakOut)
  }

  def contains(keys: GraphItemKey*): Map[GraphItemKey, Boolean] = {
    (keys map { k => (k, items contains k) }) (collection.breakOut)
  }

  def delete(keys: GraphItemKey*) = {
    items --= keys
  }

}