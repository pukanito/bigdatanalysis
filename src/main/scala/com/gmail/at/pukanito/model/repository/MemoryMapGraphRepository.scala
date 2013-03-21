package com.gmail.at.pukanito.model.repository

import com.gmail.at.pukanito.model.graph.{Node,Path,NodeKey}

/**
 * Simple in memory storage for graphs.
 */
class MemoryMapGraphRepository[T <: Node[T]] extends GraphRepository[T] {

  private var leafs: Map[NodeKey, T] = Map.empty
  private var children: Map[NodeKey, MemoryMapGraphRepository[T]] = Map.empty

  /**
   * Returns the MemoryMapGraphRepository[T] that belongs to children(key).
   *
   * Returns an existing repository or creates a new one and adds it to children.
   *
   * @param key the key of the memory map graph item repository to get or create.
   */
  private def getOrCreateChildrenRepository(key: NodeKey): MemoryMapGraphRepository[T] = {
    children.getOrElse(key, { val m = new MemoryMapGraphRepository[T](); children += key -> m; m })
  }

  private def putChildren(value: T): Unit = {
    value.children foreach { case (childKey, childValue) =>
      val childMap = getOrCreateChildrenRepository(value.key)
      childMap.leafs += childKey -> childValue.copy;
      childMap.putChildren(childValue)
    }
  }

  private def put(path: Path, value: T): Unit = {
    path match {
      case Path() => throw new RuntimeException("Cannot store in MemoryMapGraphRepository without a path")
      case Path(key) => leafs += key -> value.copy; putChildren(value)
      case Path(headKey, tail @ _*) => getOrCreateChildrenRepository(headKey).put(Path(tail:_*), value)
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

  override def apply(paths: Path*): Set[T] = {
    // When an empty path is supplied, get all the root leaves.
    if (paths.isEmpty)
      apply((leafs map {case (key,_) => Path(key) }).toSeq:_*)
    else (paths map { p =>
      p match {
        case Path() => throw new RuntimeException("Cannot get from MemoryMapGraphRepository without a path")
        case Path(key) => getChildren(Some(leafs(key).copy)).get
        case Path(headKey, tail @ _*) => children(headKey)(Path(tail:_*)).head
      }
    } ) (collection.breakOut)
  }

  override def get(paths: Path*): Set[Option[T]] = {
    // When an empty path is supplied, get all the root leaves.
    if (paths.isEmpty)
      get((leafs map {case (key,_) => Path(key) }).toSeq:_*)
    else (paths map { p =>
      p match {
        case Path() => throw new RuntimeException("Cannot get from MemoryMapGraphRepository without a path")
        case Path(key) => getChildren(leafs.get(key) map {_.copy})
        case Path(headKey, tail @ _*) => children.get(headKey).flatMap { _.get(Path(tail:_*)).head }
      }
    } ) (collection.breakOut)
  }

  override def contains(paths: Path*): Map[Path, Boolean] = {
    (paths map { p => (p,
      p match {
        case Path() => throw new RuntimeException("Cannot contain in MemoryMapGraphRepository without a path")
        case Path(key) => leafs contains key
        case Path(headKey, tail @ _*) => (children contains headKey) &&
                                              ((children(headKey) contains Path(tail:_*))(Path(tail:_*)))
      }
    ) } ) (collection.breakOut)
  }

  override def delete(paths: Path*) = {
    paths foreach { p =>
      p match {
        case Path() => throw new RuntimeException("Cannot delete from MemoryMapGraphRepository without a path")
        case Path(key) => leafs -= key; children -= key
        case Path(headKey, tail @ _*) => children(headKey).delete(Path(tail:_*))
      }
    }
  }

}