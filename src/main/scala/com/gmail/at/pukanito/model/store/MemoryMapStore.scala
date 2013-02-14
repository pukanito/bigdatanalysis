package com.gmail.at.pukanito.model.store

import com.gmail.at.pukanito.model.container.{GraphItem,GraphPath,GraphItemKey}

/**
 * Simple in memory storage.
 */
class MemoryMapGraphItemStore[T <: GraphItem[T]] extends GraphItemStore[T] {

  private var leafs: Map[GraphItemKey, T] = Map.empty
  private var children: Map[GraphItemKey, MemoryMapGraphItemStore[T]] = Map.empty

  def put(path: GraphPath, value: T): Unit = {
    if (path.size > 1) {
      if (!(children contains path.head)) {
    	children += path.head -> new MemoryMapGraphItemStore[T]()
      }
      children(path.head).put(path.tail, value)
    }
    else {
      leafs += path.head -> value
    }
  }

  def put(values: T*): Unit = {
    values foreach { v => v.paths foreach { p => put(p, v) } }
  }

  def apply(paths: GraphPath*): Set[T] = {
    (paths map { p =>
      if (p.size > 1)
        children(p.head)(p.tail).first
      else
        leafs(p.head)
    } ) (collection.breakOut)
  }

  def get(paths: GraphPath*): Set[Option[T]] = {
    (paths map { p =>
      if (p.size > 1)
        children.get(p.head).map { _.get(p.tail).first.get }
      else
        leafs.get(p.head)
    } ) (collection.breakOut)
  }

  def contains(paths: GraphPath*): Map[GraphPath, Boolean] = {
    (paths map { p => (p,
      if (p.size > 1)
        (children contains p.head) && (children(p.head).contains(p.tail)(p.head))
      else
        leafs contains p.head
    ) } ) (collection.breakOut)
  }

  def delete(paths: GraphPath*) = {
    (paths foreach { p =>
      if (p.size > 1)
        children(p.head).delete(p.tail)
      else
        leafs -= p.head
    } )
  }

}