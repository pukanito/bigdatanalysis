package com.gmail.at.pukanito.model.store

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

import com.gmail.at.pukanito.model.container.{GraphItem,GraphPath}

class GraphItemStoreTest extends FunSpec with ShouldMatchers {

  private class TestSimpleGraphItem(val k: String, var v: Int) extends GraphItem[TestSimpleGraphItem] {
    override def key = k

    override def equals(other: Any): Boolean = {
      other match {
        case that: TestSimpleGraphItem =>
          (that canEqual this) &&
          that.key == this.key && that.v == this.v
        case _ => false
      }
    }

    override def hashCode: Int = key.hashCode() + 41 * (41 + v)

    def canEqual(other: Any): Boolean = other.isInstanceOf[TestSimpleGraphItem]

    override def toString: String = {
      "TestSimpleGraphItem(" + key + "," + v + ")"
    }

  }

  describe("GraphItemStore") {

    it("should be possible to add and retrieve items to/from the store") {
      val store = new MemoryMapGraphItemStore[TestSimpleGraphItem]
      val t1 = new TestSimpleGraphItem("A", 1)
      val t2 = new TestSimpleGraphItem("B", 2)
      store.put(t1, t2)
      store(t1.key).first should equal (new TestSimpleGraphItem("A", 1))
      store(t2.key).first should equal (new TestSimpleGraphItem("B", 2))
      intercept[NoSuchElementException] { store(t1.key, t2.key, GraphPath("C")) }
      store.get(t1.key, GraphPath("C")) should equal (Set(Some(t1), None))
    }

    it("should be possible to check if items exist in the store") {
      val store = new MemoryMapGraphItemStore[TestSimpleGraphItem]
      val t1 = new TestSimpleGraphItem("A", 1)
      val t2 = new TestSimpleGraphItem("B", 2)
      val t3 = new TestSimpleGraphItem("C", 3)
      store.put(t1, t2)
      store.contains(t1.key, t3.key) should equal (
        Map(GraphPath(t1.key) -> true, GraphPath(t3.key) -> false))
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
      store(t1.key).first should equal (new TestSimpleGraphItem("A", 3))
    }

    it("should be possible to delete items from the store") {
      val store = new MemoryMapGraphItemStore[TestSimpleGraphItem]
      val t1 = new TestSimpleGraphItem("A", 1)
      val t2 = new TestSimpleGraphItem("B", 2)
      val t3 = new TestSimpleGraphItem("C", 3)
      store.put(t1, t2, t3)
      store.contains(t1.key, t2.key, t3.key) should equal (
        Map(GraphPath(t1.key) -> true, GraphPath(t2.key) -> true, GraphPath(t3.key) -> true))
      store.delete(t2.key, t1.key)
      store.contains(t1.key, t2.key, t3.key) should equal (
        Map(GraphPath(t1.key) -> false, GraphPath(t2.key) -> false, GraphPath(t3.key) -> true))
    }

    it("should be possible to create and check an item by its path") {
      val store = new MemoryMapGraphItemStore[TestSimpleGraphItem]
      val t1 = new TestSimpleGraphItem("A", 1)
      val t2 = new TestSimpleGraphItem("B", 2)
      store.put(t1)
      store.contains(t1.key) should equal (Map(GraphPath(t1.key) -> true))
      t1 += t2
      store.put(t2)
      store.contains(t1.key) should equal (Map(GraphPath(t1.key) -> true))
      store.contains(t2.key) should equal (Map(GraphPath(t2.key) -> false))
      val p = GraphPath(t1.key, t2.key)
      store.contains(p) should equal (Map(p -> true))
    }

    it("should be possible to modify an item by its path") {
      val store = new MemoryMapGraphItemStore[TestSimpleGraphItem]
      val t1 = new TestSimpleGraphItem("A", 1)
      val t2 = new TestSimpleGraphItem("B", 2)
      val t2a = new TestSimpleGraphItem("B", 3)
      store.put(t1)
      t1 += t2
      store.put(t2)
      t2.v = 3
      store.put(t2)
      val p = GraphPath(t1.key, t2.key)
      store.contains(p) should equal (Map(p -> true))
      store(p).first should equal (t2a)
    }

    it("should be possible to delete an item by its path") (pending)

    it("should be possible to delete an item and all its children (a graph) by its path") (pending)

    it("should be possible to retrieve an item by its path") (pending)

    it("should be possible to retrieve all items with a specific sub path") (pending)

    it("should throw an exception when creating an item without path") (pending)

    it("should throw an exception when modifying an item without path") (pending)

    it("should throw an exception when deleting an item without path") {
      val store = new MemoryMapGraphItemStore[TestSimpleGraphItem]
      intercept[RuntimeException] { store.delete(GraphPath()) }
    }

    it("should throw an exception when checking an item without path") {
      val store = new MemoryMapGraphItemStore[TestSimpleGraphItem]
      intercept[RuntimeException] { store.contains(GraphPath()) }
    }

    it("should be possible to create a graph by its path") (pending)

    it("should be possible to modify a graph by its path") (pending)

    it("should be possible to retrieve a graph by its path") (pending)

  }

}