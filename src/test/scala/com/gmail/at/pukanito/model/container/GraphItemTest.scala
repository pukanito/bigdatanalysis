package com.gmail.at.pukanito.model.container

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

class TestGraphItem(val k: Int) extends GraphItem {
  override def key: GraphItemKey = { Map("A" -> k) }
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

    it("should throw an exception when a cycle is detected") (pending)

    it("should be possible to uniquely identify a graph item by its path (root / childkey / childkey / ...)") (pending)

    it("should throw a NoSuchElementException when a non existing path is retrieved") (pending)

    it("should throw a DuplicateAttributeException when a graph item is added with an already existing path") (pending)

    it("should throw a NoSuchElementException when a deleted graph item is retrieved by its path") (pending)

    it("should throw a NoSuchElementException when a renamed graph item is retrieved by its old path") (pending)

    it("should be possible to retrieve the children of a graph item") (pending)

    it("should be possible to retrieve the parents of a graph item") (pending)

  }

}