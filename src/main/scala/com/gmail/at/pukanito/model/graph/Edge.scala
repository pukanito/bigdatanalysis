package com.gmail.at.pukanito.model.graph

/**
 * An edge is a relationship between two nodes.
 *
 * @param T the type that can be contained within the graph this edge belongs to.
 * @param from source of the edge.
 * @param to destination of the edge.
 */
sealed abstract class Edge[T <: Node[T]] (
  val from: T,
  val to: T
)

/**
 * Edge to represent a parent to child relationship.
 */
case class EdgeToChild[T <: Node[T]] (
  source: T,
  child: T
) extends Edge[T](source, child)

/**
 * Edge to represent a child to parent relationship.
 */
case class EdgeToParent[T <: Node[T]] (
  source: T,
  parent: T
) extends Edge[T](source, parent)
