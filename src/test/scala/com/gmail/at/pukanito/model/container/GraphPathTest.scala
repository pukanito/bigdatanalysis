package com.gmail.at.pukanito.model.container

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

class GraphPathTest extends FunSpec with ShouldMatchers  {

  describe("GraphPath") {

    it("should be possible to represent a root item") {
      val x = GraphPath()
      x should have size (0)
    }

    it("should be possible to represent a path for simple and compound keys") {
      val x = GraphPath("A" -> 1)
      x should have size (1)
      val y = GraphPath(GraphItemKey("A" -> 1, "B" -> 2))
      y should have size (1)
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

    it("should return the remainder of the path with 'tail' for simple and compound keys") {
      val x = GraphPath("A"->1, "B" -> 2, "C" -> 3)
      x.tail should have size (2)
      x.tail should equal (GraphPath("B" -> 2, "C" -> 3))
      val y = GraphPath("A"->1, GraphItemKey("B" -> 2, "C" -> 3))
      y.tail should have size (1)
      y.tail should equal (GraphPath(GraphItemKey("B" -> 2, "C" -> 3)))
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

    it("should create a special GraphPath when using implicit conversion from String to GraphItemKey") {
      import GraphItemKey.string2GraphItemKey
      GraphPath("A", "B") should equal (GraphPath(string2GraphItemKey("A"), string2GraphItemKey("B")))
    }

    it("should be possible to extract a GraphPath to GraphItemKeys in a match expression") {
      val p1 = GraphPath("A" -> 1, "B" -> 2, "C" -> 3)
      p1.toList match {
        case List() => fail("Error in extracting GraphItemKeys")
        case List(a) => fail("Error in extracting GraphItemKeys")
        case a :: rest =>
          a should equal (GraphItemKey("A" -> 1))
          rest should equal (List(GraphItemKey("B" -> 2), GraphItemKey("C" -> 3)))
      }
      val p2 = GraphPath("A" -> 1)
      p2.toList match {
        case List() => fail("Error in extracting GraphItemKeys")
        case List(a) => a should equal (GraphItemKey("A" -> 1))
        case a :: rest => fail("Error in extracting GraphItemKeys")
      }
    }

  }

}