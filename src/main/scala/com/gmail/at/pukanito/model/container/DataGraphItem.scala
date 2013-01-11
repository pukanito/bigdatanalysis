package com.gmail.at.pukanito.model.container

/**
 * Trait for making items data graph compatible.
 */
trait DataGraphItem {
  private var parentGraphs: Set[DataGraphItem] = Set()

  /**
   * Set of containers which contain this item.
   */
  def containers: Set[DataGraphItem] = { parentGraphs }

}