package com.gmail.at.pukanito.model.store

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

import com.gmail.at.pukanito.model.container.{GraphItem,GraphItemKey}

class GraphItemStoreTest extends FunSpec with ShouldMatchers {

  private class TestSimpleGraphItem(val k: String, val v: Int) extends GraphItem[TestSimpleGraphItem] {
    override def key = k
  }

  describe("GraphItemStore") {

    it("should be possible to add and retrieve items to/from the store") {
      val store = new MemoryMapGraphItemStore[TestSimpleGraphItem]
      val t1 = new TestSimpleGraphItem("A", 1)
      val t2 = new TestSimpleGraphItem("B", 2)
      store.put(t1, t2)
      store(t1.key).first should equal (t1)
      store(t2.key).first should equal (t2)
      intercept[NoSuchElementException] { store(t1.key, t2.key, "C") }
      store.get(t1.key, "C") should equal (Set(Some(t1), None))
    }

    it("should be possible to check if items exist in the store") {
      val store = new MemoryMapGraphItemStore[TestSimpleGraphItem]
      val t1 = new TestSimpleGraphItem("A", 1)
      val t2 = new TestSimpleGraphItem("B", 2)
      val t3 = new TestSimpleGraphItem("C", 3)
      store.put(t1, t2)
      store.contains(t1.key, t3.key) should equal (Map(t1.key -> true, t3.key -> false))
    }

    it("should be possible to replace items in the store") {
      val store = new MemoryMapGraphItemStore[TestSimpleGraphItem]
      val t1 = new TestSimpleGraphItem("A", 1)
      val t2 = new TestSimpleGraphItem("B", 2)
      val t3 = new TestSimpleGraphItem("A", 3)
      store.put(t1, t2)
      store(t1.key).first should equal (t1)
      store(t2.key).first should equal (t2)
      store.put(t3)
      store(t1.key).first should equal (t3)
    }

    it("should be possible to delete items from the store") {
      val store = new MemoryMapGraphItemStore[TestSimpleGraphItem]
      val t1 = new TestSimpleGraphItem("A", 1)
      val t2 = new TestSimpleGraphItem("B", 2)
      val t3 = new TestSimpleGraphItem("C", 3)
      store.put(t1, t2, t3)
      store.contains(t1.key, t2.key, t3.key) should equal (Map(t1.key -> true, t2.key -> true, t3.key -> true))
      store.delete(t2.key, t1.key)
      store.contains(t1.key, t2.key, t3.key) should equal (Map(t1.key -> false, t2.key -> false, t3.key -> true))
    }

    it("should be possible to create an item by its path") (pending)
    // There can be multiple paths due to multiple parents.

    it("should be possible to modify an item by its path") (pending)

    it("should be possible to delete an item by its path") (pending)

    it("should be possible to retrieve an item by its path") (pending)
  }

}