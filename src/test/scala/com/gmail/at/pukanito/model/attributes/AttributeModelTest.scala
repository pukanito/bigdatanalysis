package com.gmail.at.pukanito.model.attributes

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

class AttributeModelTest extends FunSpec with ShouldMatchers {

  describe("AttributeModel") {

    it("should be possible to add attribute definitions to the model") (pending)

    it("should be possible to model parent-child relations between attribute definitions") (pending)

    it("attribute definitions in the model without a parent are root attributes") (pending)

    it("should be possible to retrieve a modelled attribute definition by its path (root -> child -> child -> ...") (pending)

    it("should throw a NoSuchElementException when a non modelled attribute definition is retrieved by its path") (pending)

    it("should throw a DuplicateAttributeException when an attribute definition is added with an already modelled identifier") (pending)

    it("should throw a NoSuchElementException when a deleted attribute definition is retrieved by its identifier") (pending)

    it("should throw a NoSuchElementException when a renamed attribute definition is retrieved by its old identifier") (pending)

    it("should be possible to retrieve the modelled children of a modelled attribute definition") (pending)

    it("should be possible to retrieve the modelled parents of a modelled attribute definition") (pending)

  }


}