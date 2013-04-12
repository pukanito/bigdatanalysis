package com.gmail.at.pukanito.model.graph

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

class NodeKeyTest extends FunSpec with ShouldMatchers {

  describe("NodeKey") {

    it("should throw an exception when creating an empty NodeKey") {
      intercept[IllegalArgumentException] { NodeKey() }
    }

    it("should be possible to add items to the node key") {
      var k: NodeKey = NodeKey("A" -> 1, "B" -> 2)
      k.size should equal (2)
      k = k + ("C" -> 3)
      k.size should equal (3)
    }

    it("should be possible to remove items from the node key") {
      var k: NodeKey = NodeKey("A" -> 1, "B" -> 2, "C" -> 3)
      k.size should equal (3)
      k -= "B"
      k.size should equal (2)
    }

    it("should be possible to request non existing items with 'get'") {
      val k: NodeKey = NodeKey("A" -> 1, "B" -> 2)
      k get "A" should equal (Some(1))
      k get "B" should equal (Some(2))
      k get "C" should equal (None)
    }

    it("should not be possible to request non existing items with 'apply'") {
      val k: NodeKey = NodeKey("A" -> 1, "B" -> 2)
      k("A") should equal (1)
      k("B") should equal (2)
      intercept[NoSuchElementException] { k("C") }
    }

    it("should be possible to iterate over items with 'iterator'") {
      val i = NodeKey("A" -> 1, "B" -> 2).iterator
      i.hasNext should equal (true)
      i.next should equal ("A" -> 1)
      i.hasNext should equal (true)
      i.next should equal ("B" -> 2)
      i.hasNext should equal (false)
    }

    it("should compare different node keys with equal value as equal") {
      NodeKey("A" -> 1, "B" -> 2) should equal ( NodeKey("B" -> 2, "A" -> 1))
    }

    it("should compare different node keys with different value as not equal") {
      NodeKey("A" -> 1, "B" -> 2) should not equal (NodeKey("B" -> 1, "A" -> 2))
    }

  }

}