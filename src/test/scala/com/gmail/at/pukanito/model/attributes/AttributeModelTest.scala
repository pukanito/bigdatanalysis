package com.gmail.at.pukanito.model.attributes

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

class AttributeModelTest extends FunSpec with ShouldMatchers {

  describe("AttributeModel") {

    it("should model parent-child relations between attribute definitions") (pending)

    it("should throw an exception when a cycle is detected") (pending)

    it("should model attribute definitions without a parent as root attributes") (pending)

    it("should be possible to uniquely identify a modelled attribute definition by its path (root -> child -> child -> ...)") (pending)

    it("should throw a NoSuchElementException when a non modelled path is retrieved") (pending)

    it("should throw a DuplicateAttributeException when an attribute definition is added with an already existing path") (pending)

    it("should throw a NoSuchElementException when a deleted attribute definition is retrieved by its path") (pending)

    it("should throw a NoSuchElementException when a renamed attribute definition is retrieved by its old path") (pending)

    it("should be possible to retrieve the modelled children of a modelled attribute definition") (pending)

    it("should be possible to retrieve the modelled parents of a modelled attribute definition") (pending)

  }


}