package com.gmail.at.pukanito.model.graph

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

class NodeTest extends FunSpec with ShouldMatchers {

  private class TestNode(val k: Int) extends Node[TestNode] {
    override def key = "A" -> k

    override def copy: TestNode = new TestNode(k)

    override def equals(other: Any): Boolean = {
      other match {
        case that: TestNode =>
          (that canEqual this) && that.key == this.key && that.children == this.children
        case _ => false
      }
    }

    override def hashCode: Int = key.hashCode

    def canEqual(other: Any): Boolean = other.isInstanceOf[TestNode]

  }

  private class TestSimpleNode (val k: String) extends Node[TestSimpleNode] {
    override def key = k

    override def copy: TestSimpleNode = new TestSimpleNode(k)
  }

  describe("Node") {

    it("should compare keys of different nodes with the same key as equal") {
      val t1 = new TestNode(1)
      val t2 = new TestNode(1)
      t1.key should equal (t2.key)
    }

    it("should compare keys of different nodes with different keys as not equal") {
      val t1 = new TestNode(1)
      val t2 = new TestNode(2)
      t1.key should not equal (t2.key)
    }

    it("should model parent-child relations between nodes") {
      val t1 = new TestNode(1)
      val t2 = new TestNode(1)
      t1 += t2
      t1.parents should have size (0)
      t1.children should have size (1)
      t2.parents should have size (1)
      t2.children should have size (0)
      t2.parents.head should be theSameInstanceAs (t1)
      t1.children(t2.key) should be theSameInstanceAs (t2)
    }

    it("should be possible to get the path(s) of a node") {
      val t1 = new TestNode(1)
      val t2 = new TestNode(2)
      val t3 = new TestNode(3)
      t1 += t2
      t3 += t2
      t2.paths should have size (2)
      t2.path should have size (2)
    }

    it("should be possible to compare different graphs with each other") {
      val t1 = new TestNode(1)
      val t2 = new TestNode(2)
      t1 += t2
      val s1 = new TestNode(1)
      val s2 = new TestNode(2)
      s1 += s2
      s1 should equal (t1)
      val r1 = new TestNode(1)
      val r2 = new TestNode(3)
      r1 += r2
      r1 should not equal (t1)
    }

    it("should be possible to take a copy of a node") {
      val t1 = new TestNode(1)
      val t2 = t1.copy
      t1 should equal (t2)
      t2 should equal (t1)
      t1 should not be theSameInstanceAs (t2)
    }

    it("should be possible to take a copy of a graph or subgraph") {
      val t1 = new TestNode(1)
      val t2 = new TestNode(2)
      val t3 = new TestNode(3)
      t1 += t2
      t2 += t3
      t1 += t3
      val s1 = new TestNode(1)
      val s2 = new TestNode(2)
      val s3 = new TestNode(3)
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
      val t1 = new TestNode(1)
      def testRecursively(depth: Int, item: TestNode): Unit = {
        intercept[GraphCycleException] { item += t1 }
        val t = new TestNode(1)
        item += t
        if (depth > 0) testRecursively(depth-1, t)
      }
      testRecursively(10, t1)
    }

    it("should throw an exception when a cycle is detected when adding a child that has a child that is also parent") {
      val t1 = new TestNode(1)
      def testRecursively(depth: Int, item1: TestNode, item2: TestNode): Unit = {
        val t3 = new TestNode(1)
        val t4 = new TestNode(1)
        item1 += t3
        t4 += item2
        intercept[GraphCycleException] { t3 += t4 }
        if (depth > 0) testRecursively(depth-1, t3, t4)
      }
      testRecursively(10, t1, t1)
    }

    it("should throw an exception when a cycle is detected in specific use cases") {
      val t4 = new TestNode(1)
      val t3 = new TestNode(2)
      t3 += t4
      val t2 = new TestNode(3)
      val t1 = new TestNode(4)
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

    it("should be possible to uniquely identify a node by its path (root / childkey / childkey / ...)") {
      val t1 = new TestNode(1)
      val t2 = new TestNode(2)
      val t3 = new TestNode(3)
      t1 += t2
      t1 += t3
      val toT2 = Path("A" -> 2)
      val toT3 = Path("A" -> 3)
      t1 should be theSameInstanceAs(t1(Path()))
      t2 should be theSameInstanceAs(t1(toT2))
      t3 should be theSameInstanceAs(t1(toT3))
      val t4 = new TestNode(4)
      val t5 = new TestNode(5)
      t2 += t4
      t3 += t5
      val toT4 = Path("A" -> 2, "A" -> 4)
      val toT5 = Path("A" -> 3, "A" -> 5)
      t4 should be theSameInstanceAs(t1(toT4))
      t5 should be theSameInstanceAs(t1(toT5))
      t4 should be theSameInstanceAs(t2(toT4.tail))
      t5 should be theSameInstanceAs(t3(toT5.tail))
    }

    it("should throw a NoSuchElementException when a non existing path is retrieved") {
      val t1 = new TestNode(1)
      intercept[NoSuchElementException] { t1(Path("non-existing-key"->0)) }
    }

    it("should throw a DuplicateAttributeException when a child node is added with an already existing key") {
      val t1 = new TestNode(1)
      val t2 = new TestNode(2)
      val t3 = new TestNode(2)
      t1 += t2
      intercept[DuplicateChildNodeException] { t1 += t2 }
      intercept[DuplicateChildNodeException] { t1 += t3 }
    }

    it("should not be possible to add different node types to each other") {
      val t1 = new TestNode(1)
      val t2 = new TestSimpleNode("A")
      // implicit by compiler error
      //t1 += t2
    }

  }

  describe("simple Node") {

    it("should compare keys of different nodes with the same key as equal") {
      val t1 = new TestSimpleNode("A")
      val t2 = new TestSimpleNode("A")
      t1.key should equal (t2.key)
    }

    it("should compare keys of different nodes with different keys as not equal") {
      val t1 = new TestSimpleNode("A")
      val t2 = new TestSimpleNode("B")
      t1.key should not equal (t2.key)
    }

    it("should be possible to uniquely identify a node by its path (root / childkey / childkey / ...)") {
      val t1 = new TestSimpleNode("1")
      val t2 = new TestSimpleNode("2")
      val t3 = new TestSimpleNode("3")
      t1 += t2
      t1 += t3
      val toT2 = Path("2")
      val toT3 = Path("3")
      t1 should be theSameInstanceAs(t1(Path()))
      t2 should be theSameInstanceAs(t1(toT2))
      t3 should be theSameInstanceAs(t1(toT3))
      val t4 = new TestSimpleNode("4")
      val t5 = new TestSimpleNode("5")
      t2 += t4
      t3 += t5
      val toT4 = Path("2", "4")
      val toT5 = Path("3", "5")
      t4 should be theSameInstanceAs(t1(toT4))
      t5 should be theSameInstanceAs(t1(toT5))
      t4 should be theSameInstanceAs(t2(toT4.tail))
      t5 should be theSameInstanceAs(t3(toT5.tail))
    }

  }

}