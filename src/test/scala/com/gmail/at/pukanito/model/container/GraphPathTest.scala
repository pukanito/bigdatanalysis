package com.gmail.at.pukanito.model.container

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

class GraphPathTest extends FunSpec with ShouldMatchers  {

  describe("GraphPath") {

    it("should be possible to represent a root item") {
      val x = new GraphPath()
      x should have size (0)
    }

    it("should be possible to represent a path") {
      val x = new GraphPath(Map("A"->1))
      x should have size (1)
    }

    it("should be possible to add a path to another path giving a new path") {
      val x = new GraphPath(Map("A"->1))
      val y = new GraphPath(Map("A"->1))
      val z = x + y
      z should have size (2)
    }

  }

}