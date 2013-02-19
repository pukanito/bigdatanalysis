package com.gmail.at.pukanito.model.store

import com.gmail.at.pukanito.model.container.{GraphItem,GraphPath,GraphItemKey}

/**
 * Simple in memory storage.
 */
class MemoryMapGraphItemStore[T <: GraphItem[T]] extends GraphItemStore[T] {

  private var leafs: Map[GraphItemKey, T] = Map.empty
  private var children: Map[GraphItemKey, MemoryMapGraphItemStore[T]] = Map.empty

  private def put(path: GraphPath, value: T): Unit = {
    path.toList match {
      case Nil => throw new RuntimeException("Cannot store in MemoryMapGraphItemStore without a path")
      case List(key) => leafs += key -> value
      case headKey :: tail => (children.get(headKey) match {
          case Some(m) => m
          case None => val m = new MemoryMapGraphItemStore[T](); children += headKey -> m; m
        }).put(GraphPath(tail), value)
    }
  }

  def put(values: T*): Unit = {
    values foreach { v => v.paths foreach { p => put(p, v) } }
  }

  def apply(paths: GraphPath*): Set[T] = {
    (paths map { p =>
      p.toList match {
        case Nil => throw new RuntimeException("Cannot get from MemoryMapGraphItemStore without a path")
        case List(key) => leafs(key)
        case headKey :: tail => children(headKey)(GraphPath(tail)).first
      }
    } ) (collection.breakOut)
  }

  def get(paths: GraphPath*): Set[Option[T]] = {
    (paths map { p =>
      p.toList match {
        case Nil => throw new RuntimeException("Cannot get from MemoryMapGraphItemStore without a path")
        case List(key) => leafs.get(key)
        case headKey :: tail => children.get(headKey).map { _.get(GraphPath(tail)).first.get }
      }
    } ) (collection.breakOut)
  }

  def contains(paths: GraphPath*): Map[GraphPath, Boolean] = {
    (paths map { p => (p,
      p.toList match {
        case Nil => throw new RuntimeException("Cannot contain in MemoryMapGraphItemStore without a path")
        case List(key) => leafs contains key
        case headKey :: tail => (children contains headKey) &&
                                ((children(headKey) contains GraphPath(tail))(tail.head))
      }
    ) } ) (collection.breakOut)
  }

  def delete(paths: GraphPath*) = {
    paths foreach { p =>
      p.toList match {
        case Nil => throw new RuntimeException("Cannot delete from MemoryMapGraphItemStore without a path")
        case List(key) => leafs -= key; children -= key
        case headKey :: tail => children(headKey).delete(GraphPath(tail))
      }
    }
  }

}