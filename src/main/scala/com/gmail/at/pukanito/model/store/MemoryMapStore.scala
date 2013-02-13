package com.gmail.at.pukanito.model.store

import com.gmail.at.pukanito.model.container.{GraphItem,GraphPath}

/**
 * Simple in memory storage.
 */
class MemoryMapGraphItemStore[T <: GraphItem[T]] extends GraphItemStore[T] {

  private var items: Map[GraphPath, T] = Map.empty

  def put(values: T*) = {
    items ++= { values map (x => (GraphPath(x.key), x)) }
  }

  def apply(paths: GraphPath*): Set[T] = {
    (for (p <- paths ) yield items(p)) (collection.breakOut)
  }

  def get(paths: GraphPath*): Set[Option[T]] = {
    (for (p <- paths ) yield items.get(p)) (collection.breakOut)
  }

  def contains(paths: GraphPath*): Map[GraphPath, Boolean] = {
    (paths map { p => (p, items contains p) }) (collection.breakOut)
  }

  def delete(paths: GraphPath*) = {
    items --= paths
  }

}