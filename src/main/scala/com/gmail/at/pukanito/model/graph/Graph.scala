package com.gmail.at.pukanito.model.graph

/**
 * A graph is a set of nodes and a set of edges.
 *
 * @param T the type that can be contained within this graph.
 */
class Graph[T <: Node[T]] {

  /**
   * All the nodes in the graph.
   */
  private var nodes: Set[T] = Set()

  /**
   * All the edges in the graph.
   */
  private var edges: Set[Edge[T]] = Set()

  /**
   * Add a root node (including child nodes).
   */
  def addAtRoot(node: Node[T]): Graph[T] = {

    this
  }

}
