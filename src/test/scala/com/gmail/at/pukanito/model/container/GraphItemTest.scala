package com.gmail.at.pukanito.model.container

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

class TestGraphItem(val k: Int) extends GraphItem {
  override def key: GraphPath.GraphItemKey = { Map("A" -> k) }
}

class GraphItemTest extends FunSpec with ShouldMatchers {

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

    it("should throw an exception when a cycle is detected") {
      // Root item.
      val t1 = new TestGraphItem(1)
      // Test helper method, add 'depth' child levels to 'item' and check cycle exception.
      def testRecursively(depth: Int, item: GraphItem): Unit = {
        // Try to add 't1' as child to 'item' (should throw cycle exception)
        intercept[GraphCycleException] { item += t1 }
        // Add child levels.
        val t = new TestGraphItem(1)
        item += t
        if (depth > 0) testRecursively(depth-1, t)
      }
      testRecursively(40, t1)
    }

    it("should be possible to uniquely identify a graph item by its path (root / childkey / childkey / ...)") {
      val t1 = new TestGraphItem(1)
      val t2 = new TestGraphItem(2)
      val t3 = new TestGraphItem(3)
      t1 += t2
      t1 += t3
      val toT2 = new GraphPath(Map("A"->2))
      val toT3 = new GraphPath(Map("A"->3))
      t1 should be theSameInstanceAs(t1(new GraphPath))
      t2 should be theSameInstanceAs(t1(toT2))
      t3 should be theSameInstanceAs(t1(toT3))
      val t4 = new TestGraphItem(4)
      val t5 = new TestGraphItem(5)
      t2 += t4
      t3 += t5
      val toT4 = new GraphPath(Map("A"->2), Map("A"->4))
      val toT5 = new GraphPath(Map("A"->3), Map("A"->5))
      t4 should be theSameInstanceAs(t1(toT4))
      t5 should be theSameInstanceAs(t1(toT5))
      t4 should be theSameInstanceAs(t2(toT4.tail))
      t5 should be theSameInstanceAs(t3(toT5.tail))
    }

    it("should throw a NoSuchElementException when a non existing path is retrieved") {
      val t1 = new TestGraphItem(1)
      intercept[NoSuchElementException] { t1(new GraphPath(Map("non-existing-key"->0))) }
    }

    it("should throw a DuplicateAttributeException when a child graph item is added with an already existing key") {
      val t1 = new TestGraphItem(1)
      val t2 = new TestGraphItem(2)
      t1 += t2
      intercept[DuplicateGraphItemException] { t1 += t2 }
    }

    it("should throw a NoSuchElementException when a deleted graph item is retrieved by its path") (pending)

    it("should throw a NoSuchElementException when a renamed graph item is retrieved by its old path") (pending)

    it("should be possible to retrieve the children of a graph item") (pending)

    it("should be possible to retrieve the parents of a graph item") (pending)

  }

}