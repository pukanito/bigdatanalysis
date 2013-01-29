package com.gmail.at.pukanito.model.container

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

class GraphPathTest extends FunSpec with ShouldMatchers  {

  describe("GraphPath") {

    it("should be possible to represent a root item") {
      val x = GraphPath()
      x should have size (0)
    }

    it("should be possible to represent a path") {
      val x = GraphPath("A" -> 1)
      x should have size (1)
    }

    it("should be possible to add a path to another path giving a new path") {
      val x = GraphPath("A" -> 1)
      val y = GraphPath("A" -> 1)
      val z = x + y
      z should have size (2)
    }

    it("should return the first item of the path with 'head'") {
      val x = GraphPath("A" -> 1, "B" -> 2, "C" -> 3)
      x.head should have size (1)
      x.head should equal (GraphItemKey("A" -> 1))
    }

    it("should return the remainder of the path with 'tail'") {
      val x = GraphPath("A"->1, "B" -> 2, "C" -> 3)
      x.tail should have size (2)
      x.tail should equal (GraphPath("B" -> 2, "C" -> 3))
    }

    it("should behave correctly when testing equality") {
      val p1, p2 = GraphPath("A" -> 1, "B" -> 2)
      p1 should equal (p2)
      p2 should equal (p1)
      p1.hashCode should equal (p2.hashCode)
      val p2a: Any = p2
      p1 should equal (p2a)
      p2a should equal (p1)
      p1.hashCode should equal (p2a.hashCode)
    }

    it("Should create a special GraphPath when using implicit conversion from String to GraphItemKey") {
      import GraphItemKey.string2GraphItemKey
      GraphPath("A", "B") should equal (GraphPath(string2GraphItemKey("A"), string2GraphItemKey("B")))
    }

  }

}