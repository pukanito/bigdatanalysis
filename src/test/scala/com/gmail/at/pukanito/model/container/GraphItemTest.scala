package com.gmail.at.pukanito.model.container

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

class GraphItemTest extends FunSpec with ShouldMatchers {

  private class TestGraphItem(val k: Int) extends GraphItem[TestGraphItem] {
    override def key = "A" -> k

    override def copy: TestGraphItem = new TestGraphItem(k)

    override def equals(other: Any): Boolean = {
      other match {
        case that: TestGraphItem =>
          (that canEqual this) && that.key == this.key && that.children == this.children
        case _ => false
      }
    }

    override def hashCode: Int = key.hashCode

    def canEqual(other: Any): Boolean = other.isInstanceOf[TestGraphItem]

  }

  private class TestSimpleGraphItem (val k: String) extends GraphItem[TestSimpleGraphItem] {
    override def key = k

    override def copy: TestSimpleGraphItem = new TestSimpleGraphItem(k)
  }

  describe("GraphItem") {

    it("should compare keys of different graph items with the same key as equal") {
      val t1 = new TestGraphItem(1)
      val t2 = new TestGraphItem(1)
      t1.key should equal (t2.key)
    }

    it("should compare keys of different graph items with different keys as not equal") {
      val t1 = new TestGraphItem(1)
      val t2 = new TestGraphItem(2)
      t1.key should not equal (t2.key)
    }

    it("should model parent-child relations between graph items") {
      val t1 = new TestGraphItem(1)
      val t2 = new TestGraphItem(1)
      t1 += t2
      t1.parents should have size (0)
      t1.children should have size (1)
      t2.parents should have size (1)
      t2.children should have size (0)
      t2.parents.head should be theSameInstanceAs (t1)
      t1.children(t2.key) should be theSameInstanceAs (t2)
    }

    it("should be possible to get the graph path(s) of an item") {
      val t1 = new TestGraphItem(1)
      val t2 = new TestGraphItem(2)
      val t3 = new TestGraphItem(3)
      t1 += t2
      t3 += t2
      t2.paths should have size (2)
      t2.path should have size (2)
    }

    it("should be possible to compare different graphs with each other") {
      val t1 = new TestGraphItem(1)
      val t2 = new TestGraphItem(2)
      t1 += t2
      val s1 = new TestGraphItem(1)
      val s2 = new TestGraphItem(2)
      s1 += s2
      s1 should equal (t1)
      val r1 = new TestGraphItem(1)
      val r2 = new TestGraphItem(3)
      r1 += r2
      r1 should not equal (t1)
    }

    it("should be possible to take a copy of a single graph item") {
      val t1 = new TestGraphItem(1)
      val t2 = t1.copy
      t1 should equal (t2)
      t2 should equal (t1)
      t1 should not be theSameInstanceAs (t2)
    }

    it("should be possible to take a copy of a graph or subgraph") {
      val t1 = new TestGraphItem(1)
      val t2 = new TestGraphItem(2)
      val t3 = new TestGraphItem(3)
      t1 += t2
      t2 += t3
      t1 += t3
      val s1 = new TestGraphItem(1)
      val s2 = new TestGraphItem(2)
      val s3 = new TestGraphItem(3)
      s1 += s2
      s2 += s3
      s1 += s3
      t1 should equal (s1)
      t1.copy should not equal (s1)
      t2 should equal (s2)
      t2.copyGraph should equal (s2)
      t3.copyGraph should equal (s3)
    }

    it("should throw an exception when a cycle is detected when adding a child that is also parent") {
      val t1 = new TestGraphItem(1)
      def testRecursively(depth: Int, item: TestGraphItem): Unit = {
        intercept[GraphCycleException] { item += t1 }
        val t = new TestGraphItem(1)
        item += t
        if (depth > 0) testRecursively(depth-1, t)
      }
      testRecursively(10, t1)
    }

    it("should throw an exception when a cycle is detected when adding a child that has a child that is also parent") {
      val t1 = new TestGraphItem(1)
      def testRecursively(depth: Int, item1: TestGraphItem, item2: TestGraphItem): Unit = {
        val t3 = new TestGraphItem(1)
        val t4 = new TestGraphItem(1)
        item1 += t3
        t4 += item2
        intercept[GraphCycleException] { t3 += t4 }
        if (depth > 0) testRecursively(depth-1, t3, t4)
      }
      testRecursively(10, t1, t1)
    }

    it("should throw an exception when a cycle is detected in specific use cases") {
      val t4 = new TestGraphItem(1)
      val t3 = new TestGraphItem(2)
      t3 += t4
      val t2 = new TestGraphItem(3)
      val t1 = new TestGraphItem(4)
      t1 += t2
      intercept[GraphCycleException] { t1 += t1 }
      intercept[GraphCycleException] { t2 += t1 }
      intercept[GraphCycleException] { t2 += t2 }
      intercept[GraphCycleException] { t3 += t3 }
      intercept[GraphCycleException] { t4 += t3 }
      intercept[GraphCycleException] { t4 += t4 }
      t1 += t3
      t1 += t4
      t2 += t3
      t2 += t4
    }

    it("should be possible to uniquely identify a graph item by its path (root / childkey / childkey / ...)") {
      val t1 = new TestGraphItem(1)
      val t2 = new TestGraphItem(2)
      val t3 = new TestGraphItem(3)
      t1 += t2
      t1 += t3
      val toT2 = GraphPath("A" -> 2)
      val toT3 = GraphPath("A" -> 3)
      t1 should be theSameInstanceAs(t1(GraphPath()))
      t2 should be theSameInstanceAs(t1(toT2))
      t3 should be theSameInstanceAs(t1(toT3))
      val t4 = new TestGraphItem(4)
      val t5 = new TestGraphItem(5)
      t2 += t4
      t3 += t5
      val toT4 = GraphPath("A" -> 2, "A" -> 4)
      val toT5 = GraphPath("A" -> 3, "A" -> 5)
      t4 should be theSameInstanceAs(t1(toT4))
      t5 should be theSameInstanceAs(t1(toT5))
      t4 should be theSameInstanceAs(t2(toT4.tail))
      t5 should be theSameInstanceAs(t3(toT5.tail))
    }

    it("should throw a NoSuchElementException when a non existing path is retrieved") {
      val t1 = new TestGraphItem(1)
      intercept[NoSuchElementException] { t1(GraphPath("non-existing-key"->0)) }
    }

    it("should throw a DuplicateAttributeException when a child graph item is added with an already existing key") {
      val t1 = new TestGraphItem(1)
      val t2 = new TestGraphItem(2)
      val t3 = new TestGraphItem(2)
      t1 += t2
      intercept[DuplicateGraphItemException] { t1 += t2 }
      intercept[DuplicateGraphItemException] { t1 += t3 }
    }

    it("should not be possible to add different graph item types to each other") {
      val t1 = new TestGraphItem(1)
      val t2 = new TestSimpleGraphItem("A")
      // implicit by compiler error
      //t1 += t2
    }

  }

  describe("simple GraphItem") {

    it("should compare keys of different graph items with the same key as equal") {
      val t1 = new TestSimpleGraphItem("A")
      val t2 = new TestSimpleGraphItem("A")
      t1.key should equal (t2.key)
    }

    it("should compare keys of different graph items with different keys as not equal") {
      val t1 = new TestSimpleGraphItem("A")
      val t2 = new TestSimpleGraphItem("B")
      t1.key should not equal (t2.key)
    }

    it("should be possible to uniquely identify a graph item by its path (root / childkey / childkey / ...)") {
      val t1 = new TestSimpleGraphItem("1")
      val t2 = new TestSimpleGraphItem("2")
      val t3 = new TestSimpleGraphItem("3")
      t1 += t2
      t1 += t3
      val toT2 = GraphPath("2")
      val toT3 = GraphPath("3")
      t1 should be theSameInstanceAs(t1(GraphPath()))
      t2 should be theSameInstanceAs(t1(toT2))
      t3 should be theSameInstanceAs(t1(toT3))
      val t4 = new TestSimpleGraphItem("4")
      val t5 = new TestSimpleGraphItem("5")
      t2 += t4
      t3 += t5
      val toT4 = GraphPath("2", "4")
      val toT5 = GraphPath("3", "5")
      t4 should be theSameInstanceAs(t1(toT4))
      t5 should be theSameInstanceAs(t1(toT5))
      t4 should be theSameInstanceAs(t2(toT4.tail))
      t5 should be theSameInstanceAs(t3(toT5.tail))
    }

  }

}