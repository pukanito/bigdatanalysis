package com.gmail.at.pukanito.model.store

import com.gmail.at.pukanito.model.container.{GraphItem,GraphItemKey}

/**
 * Trait for storing graph items.
 */
trait GraphItemStore[T <: GraphItem[T]] {

  /**
   * Store values.
   *
   * @param value the value(s) to store.
   */
  def put(value: T*)

  /**
   * Retrieve values.
   *
   * @param key the key(s) of the item(s) to retrieve.
   */
  def apply(key: GraphItemKey*): Set[T]

  /**
   * Retrieve values.
   *
   * @param key the key(s) of the item(s) to retrieve.
   */
  def get(key: GraphItemKey*): Set[Option[T]]

  /**
   * Check existence of values.
   *
   * @param key the key(s) of the item(s) to check.
   */
  def contains(key: GraphItemKey*): Map[GraphItemKey, Boolean]

  /**
   * Delete values.
   *
   * @param key the key(s) of the item(s) to delete.
   */
  def delete(key: GraphItemKey*)

}