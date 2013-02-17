package com.gmail.at.pukanito.model.store

import com.gmail.at.pukanito.model.container.{GraphItem,GraphPath,GraphItemKey}

/**
 * Simple in memory storage.
 */
class MemoryMapGraphItemStore[T <: GraphItem[T]] extends GraphItemStore[T] {

  private var leafs: Map[GraphItemKey, T] = Map.empty
  private var children: Map[GraphItemKey, MemoryMapGraphItemStore[T]] = Map.empty

  def put(path: GraphPath, value: T): Unit = {
    path.toList match {
      case List() => throw new RuntimeException("Cannot store in MemoryMapGraphItemStore without a path")
      case List(key) => leafs += key -> value
      case head :: tail =>
        children.getOrElse(head, new MemoryMapGraphItemStore[T]()).put(GraphPath(tail:_*), value)
    }
  }

  def put(values: T*): Unit = {
    values foreach { v => v.paths foreach { p => put(p, v) } }
  }

  def apply(paths: GraphPath*): Set[T] = {
    (paths map { p =>
      p.toList match {
        case List() => throw new RuntimeException("Cannot get from MemoryMapGraphItemStore without a path")
        case List(key) => leafs(key)
        case head :: tail => children(head)(GraphPath(tail:_*)).first
      }
    } ) (collection.breakOut)
  }

  def get(paths: GraphPath*): Set[Option[T]] = {
    (paths map { p =>
      p.toList match {
        case List() => throw new RuntimeException("Cannot get from MemoryMapGraphItemStore without a path")
        case List(key) => leafs.get(key)
        case head :: tail => children.get(head).map { _.get(GraphPath(tail:_*)).first.get }
      }
    } ) (collection.breakOut)
  }

  def contains(paths: GraphPath*): Map[GraphPath, Boolean] = {
    (paths map { p => (p,
      p.toList match {
        case List() => throw new RuntimeException("Cannot contain in MemoryMapGraphItemStore without a path")
        case List(key) => leafs contains key
        case head :: tail => (children contains head) && ((children(head) contains (GraphPath(tail:_*)))(head))
      }
    ) } ) (collection.breakOut)
  }

  def delete(paths: GraphPath*) = {
    (paths foreach { p =>
      p.toList match {
        case List() => throw new RuntimeException("Cannot delete from MemoryMapGraphItemStore without a path")
        case List(key) => leafs -= key
        case head :: tail => children(head).delete(GraphPath(tail:_*))
      }
    } )
  }

}