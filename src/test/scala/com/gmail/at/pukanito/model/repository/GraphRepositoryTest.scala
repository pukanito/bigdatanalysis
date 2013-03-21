package com.gmail.at.pukanito.model.repository

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers
import com.gmail.at.pukanito.model.graph
import com.gmail.at.pukanito.controller.config.Configuration

class GraphRepositoryTest extends FunSpec with ShouldMatchers {

  private class TestSimpleNode(val k: String, var v: Int) extends graph.Node[TestSimpleNode] {
    override def key = k

    def copy: TestSimpleNode = new TestSimpleNode(k, v)

    override def equals(other: Any): Boolean = {
      other match {
        case that: TestSimpleNode =>
          (that canEqual this) && this.children == that.children &&
          that.k == this.k && that.v == this.v
        case _ => false
      }
    }

    override def hashCode: Int = 41 * (key.hashCode + (41 + v)) //+super.hashCode

    def canEqual(other: Any): Boolean = other.isInstanceOf[TestSimpleNode]

    override def toString: String = {
      "TestSimpleNode(" + key + "," + v + "," + children.toString + ")"
    }

  }

  private object TestRepository {
    def getNewRepository: GraphRepository[TestSimpleNode] = {
      new Configuration("configuration") getString("repository", "test") match {
        case "M" => new MemoryMapGraphRepository[TestSimpleNode]
        // Will throw match error exception in other cases.
      }
    }
  }

  describe("GraphRepository") {

    it("should be possible to add and retrieve nodes to/from the repository") {
      val repository = TestRepository.getNewRepository
      val t1 = new TestSimpleNode("A", 1)
      val t2 = new TestSimpleNode("B", 2)
      repository.put(t1, t2)
      repository(t1.key).head should equal (new TestSimpleNode("A", 1))
      repository(t2.key).head should equal (new TestSimpleNode("B", 2))
      intercept[NoSuchElementException] { repository(t1.key, t2.key, graph.Path("C")) }
      repository.get(t1.key, graph.Path("C")) should equal (Set(Some(t1), None))
    }

    it("should be possible to check if nodes exist in the repository") {
      val repository = TestRepository.getNewRepository
      val t1 = new TestSimpleNode("A", 1)
      val t2 = new TestSimpleNode("B", 2)
      val t3 = new TestSimpleNode("C", 3)
      repository.put(t1, t2)
      repository.contains(t1.key, t3.key) should equal (
        Map(graph.Path(t1.key) -> true, graph.Path(t3.key) -> false))
    }

    it("should be possible to replace nodes in the repository") {
      val repository = TestRepository.getNewRepository
      val t1 = new TestSimpleNode("A", 1)
      val t2 = new TestSimpleNode("B", 2)
      val t3 = new TestSimpleNode("A", 3)
      repository.put(t1, t2)
      repository(t1.key).head should equal (t1)
      repository(t2.key).head should equal (t2)
      repository.put(t3)
      repository(t1.key).head should equal (new TestSimpleNode("A", 3))
    }

    it("should be possible to delete nodes from the repository") {
      val repository = TestRepository.getNewRepository
      val t1 = new TestSimpleNode("A", 1)
      val t2 = new TestSimpleNode("B", 2)
      val t3 = new TestSimpleNode("C", 3)
      repository.put(t1, t2, t3)
      repository.contains(t1.key, t2.key, t3.key) should equal (
        Map(graph.Path(t1.key) -> true, graph.Path(t2.key) -> true, graph.Path(t3.key) -> true))
      repository.delete(t2.key, t1.key)
      repository.contains(t1.key, t2.key, t3.key) should equal (
        Map(graph.Path(t1.key) -> false, graph.Path(t2.key) -> false, graph.Path(t3.key) -> true))
    }

    it("should be possible to create and check a node by its path") {
      val repository = TestRepository.getNewRepository
      val t1 = new TestSimpleNode("A", 1)
      val t2 = new TestSimpleNode("B", 2)
      val t3 = new TestSimpleNode("C", 3)
      repository.put(t1)
      repository.contains(t1.key) should equal (Map(graph.Path(t1.key) -> true))
      t1 += t2
      repository.put(t2)
      repository.contains(t1.key) should equal (Map(graph.Path(t1.key) -> true))
      repository.contains(t2.key) should equal (Map(graph.Path(t2.key) -> false))
      val p2 = graph.Path(t1.key, t2.key)
      repository.contains(p2) should equal (Map(p2 -> true))
      t2 += t3
      repository.put(t3)
      val p3 = graph.Path(t1.key, t2.key, t3.key)
      repository.contains(p3) should equal (Map(p3 -> true))
    }

    it("should be possible to create and check a graph by its path") {
      val repository = TestRepository.getNewRepository
      val t1 = new TestSimpleNode("A", 10)
      val t2a = new TestSimpleNode("Ba", 20)
      val t2b = new TestSimpleNode("Bb", 20)
      val t3 = new TestSimpleNode("C", 30)
      t1 += t2b
      t1 += t2a
      t2a += t3
      repository.put(t1)
      repository(t1.key).head should equal (t1)
    }

    it("should be possible to modify a node by its path") {
      val repository = TestRepository.getNewRepository
      val t1 = new TestSimpleNode("A", 1)
      val t2 = new TestSimpleNode("B", 2)
      val t2a = new TestSimpleNode("B", 3)
      repository.put(t1)
      t1 += t2
      repository.put(t2)
      t2.v = 3
      repository.put(t2)
      val p = graph.Path(t1.key, t2.key)
      repository.contains(p) should equal (Map(p -> true))
      repository(p).head should equal (t2a)
    }

    it("should be possible to delete a node and all its children (a graph) by its path") {
      val repository = TestRepository.getNewRepository
      val t1 = new TestSimpleNode("A", 10)
      val t2a = new TestSimpleNode("Ba", 20)
      val t2b = new TestSimpleNode("Bb", 20)
      val t3 = new TestSimpleNode("C", 30)
      t1 += t2b
      val r1 = t1.copyGraph
      r1 should equal (t1)
      t1 += t2a
      t2a += t3
      repository.put(t1)
      repository(t1.key).head should not equal (r1)
      repository.delete(t2a.path)
      repository(t1.key).head should equal (r1)
      repository.get(graph.Path(t1.key, t2a.key, t3.key)).head should equal (None)
    }

    it("should be possible to retrieve a graph by its path") {
      val repository = TestRepository.getNewRepository
      val t1 = new TestSimpleNode("A", 10)
      val t2a = new TestSimpleNode("Ba", 20)
      val t2b = new TestSimpleNode("Bb", 20)
      val t3 = new TestSimpleNode("C", 30)
      repository.put(t1)
      t1 += t2a
      repository(t1.key).head should not equal (t1)
      repository.put(t2a)
      repository(t1.key).head should equal (t1)
      t1 += t2b
      repository(t1.key).head should not equal (t1)
      repository.put(t2b)
      repository(t1.key).head should equal (t1)
      t2a += t3
      repository(t1.key).head should not equal (t1)
      repository.put(t3)
      repository(t1.key).head should equal (t1)
      val r1 = new TestSimpleNode("A", 10)
      val r2a = new TestSimpleNode("Ba", 20)
      val r2b = new TestSimpleNode("Bb", 20)
      val r3 = new TestSimpleNode("C", 30)
      r1 += r2a
      r1 += r2b
      r2a += r3
      repository(t1.key).head should equal (r1)
      repository(graph.Path(t1.key, t2a.key)).head should equal (r2a)
    }

    it("should throw an exception or return None when a non existing path is retreived") {
      val repository = TestRepository.getNewRepository
      val t1 = new TestSimpleNode("A", 10)
      val t2a = new TestSimpleNode("Ba", 20)
      val t2b = new TestSimpleNode("Bb", 20)
      val t2c = new TestSimpleNode("Bc", 30)
      val t3 = new TestSimpleNode("C", 30)
      t1 += t2b
      t1 += t2a
      t2a += t3
      repository.put(t1)
      intercept[NoSuchElementException] { repository(t1.key, t2c.key) }
      repository.get(t2a.key).head should equal (None)
      repository.get(graph.Path(t1.key, t2a.key)).head should not equal (None)
      repository.get(graph.Path(t1.key, t2b.key)).head should not equal (None)
      repository.get(graph.Path(t1.key, t2c.key)).head should equal (None)
    }

    it("should be possible to retrieve all graphs with a specific sub path, even from the root") {
      val repository = TestRepository.getNewRepository
      val t1 = new TestSimpleNode("A", 10)
      val t2a = new TestSimpleNode("Ba", 20)
      val t2b = new TestSimpleNode("Bb", 20)
      val t2c = new TestSimpleNode("Bc", 30)
      val t3 = new TestSimpleNode("C", 30)
      val t4 = new TestSimpleNode("D", 40)
      t1 += t2b
      t1 += t2a
      t1 += t2c
      t2a += t3
      repository.put(t1)
      repository.put(t4)
      repository(t1.key).head.children.values.toSet should equal (Set(t2a, t2b, t2c))
      repository(t4.key).head should equal (t4)
      repository() should equal (Set(t1, t4))
      repository.get() should equal(Set(Option(t1), Option(t4)))
    }

    it("should be possible to create a graph by its path") {
      val repository = TestRepository.getNewRepository
      val t1 = new TestSimpleNode("A", 10)
      val t2a = new TestSimpleNode("Ba", 20)
      val t2b = new TestSimpleNode("Bb", 20)
      val t3 = new TestSimpleNode("C", 30)
      repository.put(t1)
      t1 += t2b
      t1 += t2a
      t2a += t3
      repository.put(t2a)
      repository.put(t2b)
      repository(t1.key).head should equal (t1)
    }

    it("should be possible to modify a graph by its path") {
      val repository = TestRepository.getNewRepository
      val t1 = new TestSimpleNode("A", 10)
      val t2a = new TestSimpleNode("Ba", 20)
      val t2b = new TestSimpleNode("Bb", 20)
      val t3 = new TestSimpleNode("C", 30)
      t1 += t2a
      t1 += t2b
      t2a += t3
      repository.put(t1)
      val r1 = new TestSimpleNode("A", 10)
      val r2a = new TestSimpleNode("Ba", 40)
      val r2b = new TestSimpleNode("Bb", 20)
      val r3 = new TestSimpleNode("C", 30)
      r1 += r2a
      repository.put(r1)
      r1 += r2b
      r2a += r3
      repository(t1.key).head should equal (r1)
    }

    it("should throw an exception when deleting an item without path") {
      val repository = TestRepository.getNewRepository
      intercept[RuntimeException] { repository.delete(graph.Path()) }
    }

    it("should throw an exception when checking an item without path") {
      val repository = TestRepository.getNewRepository
      intercept[RuntimeException] { repository.contains(graph.Path()) }
    }

    it("should be possible to get the keys of the root items") (pending)

    it("should be possible to get the keys of all items at a given path") (pending)

  }

}