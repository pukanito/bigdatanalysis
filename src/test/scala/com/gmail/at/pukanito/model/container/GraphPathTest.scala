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

    it("should return the first item of the path with 'head'") {
      val x = new GraphPath(Map("A"->1), Map("B"->2), Map("C"->3))
      x.head should have size (1)
      x.head should equal (Map("A"->1))
    }

    it("should return the remainder of the path with 'tail'") {
      val x = new GraphPath(Map("A"->1), Map("B"->2), Map("C"->3))
      x.tail should have size (2)
      x.tail should equal (new GraphPath(Map("B"->2), Map("C"->3)))
    }

    it("should behave correctly when testing equality") {
      val p1, p2 = new GraphPath(Map("A"->1), Map("B"->2))
      p1 should equal (p2)
      p2 should equal (p1)
      p1.hashCode should equal (p2.hashCode)
      val p2a: Any = p2
      p1 should equal (p2a)
      p2a should equal (p1)
      p1.hashCode should equal (p2a.hashCode)
    }

  }

}