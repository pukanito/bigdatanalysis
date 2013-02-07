package com.gmail.at.pukanito.model.dsl

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

class AttributeModelTest extends FunSpec with ShouldMatchers {

  describe("AttributeModel") {

    it("should throw an exception when an attribute with the same identifier is defined more than once in the same model") (pending)

    it("should not throw an exception when an attribute with the same identifier is defined in different models") (pending)

    it("should be possible to create a parent-child hierarchy of attribute definitions") (pending)

    it("should be possible to add parents and or children after the first definition") (pending)

    it("should throw an exception when keys are added after the first definition") (pending)

    it("should be possible to import a copy of another model") (pending)

  }

}