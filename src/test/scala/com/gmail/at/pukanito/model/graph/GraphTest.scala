package com.gmail.at.pukanito.model.graph

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

class GraphTest extends FunSpec with ShouldMatchers {

  describe("Graph") {

    it("should be possible to add root nodes to a graph") (pending)

    it("should be possible to retrieve the root nodes of a graph") (pending)

    it("should be possible to retrieve the nodes at the end of a path") (pending)

    it("should be possible for a node to be at different locations in a graph without being duplicated") (pending)

    it("should be possible for a node to be in two different graphs") (pending)

    it("should model parent-child relations between nodes") (pending)

    it("should be possible to compare different graphs with each other") (pending)

    it("should be possible to take a copy of a graph or subgraph") (pending)

    it("should throw an exception when a cycle is detected when adding a child that is also parent") (pending)

    it("should throw an exception when a cycle is detected when adding a child that has a child that is also parent") (pending)

    it("should throw an exception when a cycle is detected in specific use cases") (pending)

    it("should throw a NoSuchElementException when a non existing path is retrieved") (pending)

    it("should throw a DuplicateAttributeException when a child node is added with an already existing key") (pending)

  }

}
