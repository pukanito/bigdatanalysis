package com.gmail.at.pukanito.model.container

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

class GraphItemKeyTest extends FunSpec with ShouldMatchers {

  describe("GraphItemKey") {

    it("should be possible to add items to the graph item key") {
      var k: GraphItemKey = GraphItemKey("A" -> 1, "B" -> 2)
      k.size should equal (2)
      k = k + ("C" -> 3)
      k.size should equal (3)
    }

    it("should be possible to remove items from the graph item key") {
      var k: GraphItemKey = GraphItemKey("A" -> 1, "B" -> 2, "C" -> 3)
      k.size should equal (3)
      k -= "B"
      k.size should equal (2)
    }

    it("should be possible to request non existing items with 'get'") {
      var k: GraphItemKey = GraphItemKey("A" -> 1, "B" -> 2)
      k get "A" should equal (Some(1))
      k get "B" should equal (Some(2))
      k get "C" should equal (None)
    }

    it("should not be possible to request non existing items with 'apply'") {
      var k: GraphItemKey = GraphItemKey("A" -> 1, "B" -> 2)
      k("A") should equal (1)
      k("B") should equal (2)
      intercept[NoSuchElementException] { k("C") }
    }

    it("should not be possible to iterate over items with 'iterator'") {
      var k: GraphItemKey = GraphItemKey("A" -> 1, "B" -> 2)
      val i = k.iterator
      i.hasNext should equal (true)
      i.next should equal ("A" -> 1)
    i.hasNext should equal (true)
      i.next should equal ("B" -> 2)
      i.hasNext should equal (false)
    }

  }

}