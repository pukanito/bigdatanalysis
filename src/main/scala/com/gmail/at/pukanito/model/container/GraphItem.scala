package com.gmail.at.pukanito.model.container

/**
 * Trait for making items graph compatible.
 */
trait GraphItem {
  private var parentGraphs: Set[GraphItem] = Set()

  /**
   * Set of containers which contain this item.
   */
  def containers: Set[GraphItem] = { parentGraphs }

}