package com.gmail.at.pukanito.model.container

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

class AttributeDefinitionGraphTest extends FunSpec with ShouldMatchers {

  describe("AttributeDefinitionGraph") {

    it("should model parent-child relations between data graph items") (pending)

    it("should throw an exception when a cycle is detected") (pending)

    it("should be possible to uniquely identify a data graph item by its path (root -> child -> child -> ...)") (pending)

    it("should throw a NoSuchElementException when a non modelled path is retrieved") (pending)

    it("should throw a DuplicateAttributeException when a data graph item is added with an already existing path") (pending)

    it("should throw a NoSuchElementException when a deleted data graph item is retrieved by its path") (pending)

    it("should throw a NoSuchElementException when a renamed data graph item is retrieved by its old path") (pending)

    it("should be possible to retrieve the children of a data graph item") (pending)

    it("should be possible to retrieve the parents of a data graph item") (pending)

  }


}