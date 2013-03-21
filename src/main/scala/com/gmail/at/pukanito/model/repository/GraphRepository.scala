package com.gmail.at.pukanito.model.repository

import com.gmail.at.pukanito.model.graph.{Node,Path}

/**
 * Trait for storing graphs.
 */
trait GraphRepository[T <: Node[T]] {

  /**
   * Stores values.
   *
   * @param value the value(s) to store.
   */
  def put(value: T*)

  /**
   * Returns graphs belonging to the specified paths.
   * Specifying an empty path returns all graphs from the root.
   *
   * @param paths the path(s) of the item(s) to retrieve.
   * @throws NoSuchElementException when an item does not exist.
   */
  def apply(paths: Path*): Set[T]

  /**
   * Returns graph Options belonging to the specified paths.
   * Specifying an empty path returns all graphs from the root.
   *
   * @param paths the path(s) of the item(s) to retrieve.
   */
  def get(paths: Path*): Set[Option[T]]

  /**
   * Check existence of values.
   *
   * @param paths the path(s) of the item(s) to check.
   * @throws RuntimeException when the graph path is empty.
   */
  def contains(paths: Path*): Map[Path, Boolean]

  /**
   * Delete values.
   *
   * @param paths the path(s) of the item(s) to delete.
   * @throws RuntimeException when the graph path is empty.
   */
  def delete(paths: Path*)

}