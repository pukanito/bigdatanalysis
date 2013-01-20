package com.gmail.at.pukanito.model.container

import GraphPath.GraphItemKey

/**
 * Representation of a relative or absolute path through GraphItems.
 */
abstract class GraphPath (val path: GraphItemKey*){

  def size = path.size

  def isAbsolute: Boolean
}

object GraphPath {
  type GraphItemKey = Map[String, Any]
}

class AbsolutePath(path: GraphItemKey*) extends GraphPath(path: _*) {

  def +(p: RelativePath): AbsolutePath = {
    new AbsolutePath((this.path ++ p.path): _*)
  }

  val isAbsolute = true

}

class RelativePath(path: GraphItemKey*) extends GraphPath(path: _*) {

  def +(p: RelativePath): RelativePath = {
    new RelativePath((this.path ++ p.path): _*)
  }

  val isAbsolute = false

}
