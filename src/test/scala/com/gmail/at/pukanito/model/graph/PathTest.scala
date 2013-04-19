package com.gmail.at.pukanito.model.graph

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

class PathTest extends FunSpec with ShouldMatchers  {

  describe("Path") {

    it("should be possible to represent a root item") {
      val x = Path()
      x should have size (0)
    }

    it("should be possible to represent a path for simple and compound keys") {
      val x = Path("A" -> 1)
      x should have size (1)
      val y = Path(NodeKey("A" -> 1, "B" -> 2))
      y should have size (1)
    }

    it("should be possible to add a path to another path giving a new path") {
      val x = Path("A" -> 1)
      val y = Path("A" -> 1)
      val z = x + y
      z should have size (2)
      z should not be theSameInstanceAs(x)
      z should not be theSameInstanceAs(y)
    }

    it("should be possible to add an empty path to another path giving the other path") {
      val x = Path()
      val y = Path("A" -> 1)
      val z = x + y
      z should have size (1)
      z should be theSameInstanceAs(y)
    }

    it("should be possible to add a path to an empty path giving the original path") {
      val x = Path("A" -> 1)
      val y = Path()
      val z = x + y
      z should have size (1)
      z should be theSameInstanceAs(x)
    }

    it("should return the first item of the path with 'head'") {
      val x = Path("A" -> 1, "B" -> 2, "C" -> 3)
      x.head should have size (1)
      x.head should equal (NodeKey("A" -> 1))
    }

    it("should return the remainder of the path with 'tail' for simple and compound keys") {
      val x = Path("A"->1, "B" -> 2, "C" -> 3)
      x.tail should have size (2)
      x.tail should equal (Path("B" -> 2, "C" -> 3))
      val y = Path("A"->1, NodeKey("B" -> 2, "C" -> 3))
      y.tail should have size (1)
      y.tail should equal (Path(NodeKey("B" -> 2, "C" -> 3)))
    }

    it("should be possible to iterate over path items") {
      val i = Path("A"->1, "B" -> 2, "C" -> 3).iterator
      i.hasNext should equal (true)
      i.next should equal (NodeKey("A" -> 1))
      i.hasNext should equal (true)
      i.next should equal (NodeKey("B" -> 2))
      i.hasNext should equal (true)
      i.next should equal (NodeKey("C" -> 3))
      i.hasNext should equal (false)
    }

    it("should be possible to iterate over an empty path") {
      val i = Path().iterator
      i.hasNext should equal (false)
    }

    it("should behave correctly when testing equality") {
      val p1, p2 = Path("A" -> 1, "B" -> 2)
      p1 should equal (p2)
      p2 should equal (p1)
      p1.hashCode should equal (p2.hashCode)
      val p2a: Any = p2
      p1 should equal (p2a)
      p2a should equal (p1)
      p1.hashCode should equal (p2a.hashCode)
    }

    it("should create a special Path when using implicit conversion from String to NodeKey") {
      import NodeKey.string2NodeKey
      Path("A", "B") should equal (Path(string2NodeKey("A"), string2NodeKey("B")))
    }

    it("should be possible to extract a Path to GraphItemKeys in a match expression") {
      val p1 = Path("A" -> 1, "B" -> 2, "C" -> 3)
      p1 match {
        case Path() => fail("Error in extracting GraphItemKeys")
        case Path(a) => fail("Error in extracting GraphItemKeys")
        case Path(a, rest @ _*) =>
          a should equal (NodeKey("A" -> 1))
          rest should equal (Path(NodeKey("B" -> 2), NodeKey("C" -> 3)))
      }
      val p2 = Path("A" -> 1)
      p2 match {
        case Path() => fail("Error in extracting GraphItemKeys")
        case Path(a) => a should equal (NodeKey("A" -> 1))
        case Path(a, _*) => fail("Error in extracting GraphItemKeys")
      }
    }

  }

}
