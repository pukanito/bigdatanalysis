package com.gmail.at.pukanito.model.store

import com.gmail.at.pukanito.model.container.{GraphItem,GraphPath,GraphItemKey}

/**
 * Simple in memory storage.
 */
class MemoryMapGraphItemStore[T <: GraphItem[T]] extends GraphItemStore[T] {

  private var leafs: Map[GraphItemKey, T] = Map.empty
  private var children: Map[GraphItemKey, MemoryMapGraphItemStore[T]] = Map.empty

  private def put(path: GraphPath, value: T): Unit = {
    path match {
      case GraphPath() => throw new RuntimeException("Cannot store in MemoryMapGraphItemStore without a path")
      case GraphPath(key) => leafs += key -> value
      case GraphPath(headKey, tail @ _*) => (children.get(headKey) match {
          case Some(m) => m
          case None => val m = new MemoryMapGraphItemStore[T](); children += headKey -> m; m
        }).put(GraphPath(tail:_*), value)
    }
  }

  def put(values: T*): Unit = {
    values foreach { v => v.paths foreach { p => put(p, v) } }
  }

  def apply(paths: GraphPath*): Set[T] = {
    (paths map { p =>
      p match {
        case GraphPath() => throw new RuntimeException("Cannot get from MemoryMapGraphItemStore without a path")
        case GraphPath(key) => leafs(key)
        case GraphPath(headKey, tail @ _*) => children(headKey)(GraphPath(tail:_*)).first
      }
    } ) (collection.breakOut)
  }

  def get(paths: GraphPath*): Set[Option[T]] = {
    (paths map { p =>
      p match {
        case GraphPath() => throw new RuntimeException("Cannot get from MemoryMapGraphItemStore without a path")
        case GraphPath(key) => leafs.get(key)
        case GraphPath(headKey, tail @ _*) => children.get(headKey).map { _.get(GraphPath(tail:_*)).first.get }
      }
    } ) (collection.breakOut)
  }

  def contains(paths: GraphPath*): Map[GraphPath, Boolean] = {
    (paths map { p => (p,
      p match {
        case GraphPath() => throw new RuntimeException("Cannot contain in MemoryMapGraphItemStore without a path")
        case GraphPath(key) => leafs contains key
        case GraphPath(headKey, tail @ _*) => (children contains headKey) &&
                                              ((children(headKey) contains GraphPath(tail:_*))(tail.head))
      }
    ) } ) (collection.breakOut)
  }

  def delete(paths: GraphPath*) = {
    paths foreach { p =>
      p match {
        case GraphPath() => throw new RuntimeException("Cannot delete from MemoryMapGraphItemStore without a path")
        case GraphPath(key) => leafs -= key; children -= key
        case GraphPath(headKey, tail @ _*) => children(headKey).delete(GraphPath(tail:_*))
      }
    }
  }

}