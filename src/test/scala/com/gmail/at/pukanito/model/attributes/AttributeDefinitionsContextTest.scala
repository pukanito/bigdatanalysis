package com.gmail.at.pukanito.model.attributes

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

class AttributeDefinitionsContextTest extends FunSpec with ShouldMatchers {

  describe("AttributeDefinitionsContext") {

    it("should be possible to retrieve an existing attribute definition by its identifier") (pending)

    it("should throw a NoSuchElementException when a non existing attribute definition is retrieved by its identifier") (pending)

    it("should throw a DuplicateAttributeException when an attribute definition is added with an already existing identifier") (pending)

    it("should throw a NoSuchElementException when a deleted attribute definition is retrieved by its identifier") (pending)

    it("should throw a NoSuchElementException when a renamed attribute definition is retrieved by its old identifier") (pending)

  }


}