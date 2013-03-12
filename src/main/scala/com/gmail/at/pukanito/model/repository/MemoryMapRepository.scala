package com.gmail.at.pukanito.model.repository

import com.gmail.at.pukanito.model.container.{GraphItem,GraphPath,GraphItemKey}

/**
 * Simple in memory storage.
 */
class MemoryMapGraphItemRepository[T <: GraphItem[T]] extends GraphItemRepository[T] {

  private var leafs: Map[GraphItemKey, T] = Map.empty
  private var children: Map[GraphItemKey, MemoryMapGraphItemRepository[T]] = Map.empty

  /**
   * Returns the MemoryMapGraphItemRepository[T] that belongs to children(key)
   *
   * Returns an existing repository or creates a new one and adds it to children.
   *
   * @param key the key of the memory map graph item repository to get or create.
   */
  private def getOrCreateChildrenRepository(key: GraphItemKey): MemoryMapGraphItemRepository[T] = {
    children.getOrElse(key, { val m = new MemoryMapGraphItemRepository[T](); children += key -> m; m })
  }

  private def putChildren(value: T): Unit = {
    value.children foreach { case (childKey, childValue) =>
      val childMap = getOrCreateChildrenRepository(value.key)
      childMap.leafs += childKey -> childValue.copy;
      childMap.putChildren(childValue)
    }
  }

  private def put(path: GraphPath, value: T): Unit = {
    path match {
      case GraphPath() => throw new RuntimeException("Cannot store in MemoryMapGraphItemStore without a path")
      case GraphPath(key) => leafs += key -> value.copy; putChildren(value)
      case GraphPath(headKey, tail @ _*) => getOrCreateChildrenRepository(headKey).put(GraphPath(tail:_*), value)
    }
  }

  override def put(values: T*): Unit = {
    values foreach { v => v.paths foreach { p => put(p, v) } }
  }

  /**
   * Get a copy of the subgraph starting at item.
   *
   * @return a copy of the item and children.
   */
  private def getChildren(item: Option[T]): Option[T] = {
    item foreach { (i) =>
      children.get(i.key) foreach { (m) =>
        m.leafs foreach { case (_, leafItem) =>
          i += m.getChildren(Some(leafItem.copy)).get
        }
      }
    }
    item
  }

  override def apply(paths: GraphPath*): Set[T] = {
    // When an empty path is supplied, get all the root leaves.
    if (paths.isEmpty)
      apply((leafs map {case (key,_) => GraphPath(key) }).toSeq:_*)
    else (paths map { p =>
      p match {
        case GraphPath() => throw new RuntimeException("Cannot get from MemoryMapGraphItemRepository without a path")
        case GraphPath(key) => getChildren(Some(leafs(key).copy)).get
        case GraphPath(headKey, tail @ _*) => children(headKey)(GraphPath(tail:_*)).head
      }
    } ) (collection.breakOut)
  }

  override def get(paths: GraphPath*): Set[Option[T]] = {
    // When an empty path is supplied, get all the root leaves.
    if (paths.isEmpty)
      get((leafs map {case (key,_) => GraphPath(key) }).toSeq:_*)
    else (paths map { p =>
      p match {
        case GraphPath() => throw new RuntimeException("Cannot get from MemoryMapGraphItemRepository without a path")
        case GraphPath(key) => getChildren(leafs.get(key) map {_.copy})
        case GraphPath(headKey, tail @ _*) => children.get(headKey).flatMap { _.get(GraphPath(tail:_*)).head }
      }
    } ) (collection.breakOut)
  }

  override def contains(paths: GraphPath*): Map[GraphPath, Boolean] = {
    (paths map { p => (p,
      p match {
        case GraphPath() => throw new RuntimeException("Cannot contain in MemoryMapGraphItemRepository without a path")
        case GraphPath(key) => leafs contains key
        case GraphPath(headKey, tail @ _*) => (children contains headKey) &&
                                              ((children(headKey) contains GraphPath(tail:_*))(GraphPath(tail:_*)))
      }
    ) } ) (collection.breakOut)
  }

  override def delete(paths: GraphPath*) = {
    paths foreach { p =>
      p match {
        case GraphPath() => throw new RuntimeException("Cannot delete from MemoryMapGraphItemRepository without a path")
        case GraphPath(key) => leafs -= key; children -= key
        case GraphPath(headKey, tail @ _*) => children(headKey).delete(GraphPath(tail:_*))
      }
    }
  }

}