package com.gmail.at.pukanito.model.dsl

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

class AttributeModelTest extends FunSpec with ShouldMatchers {

  describe("AttributeModel") {

    it("should not throw an exception when an attribute with the same identifier is defined in different models") {
      println("#")
      object testmodel1 extends AttributeModel {
        println("1")
        attribute("test") { }
      }
      object testmodel2 extends AttributeModel {
        println("2")
        attribute("test") { }
      }
    }

    it("should be possible to create a parent-child hierarchy of attribute definitions") (pending)

    it("should be possible to add parents and or children after the first definition") (pending)

    it("should throw an exception when keys are added after the first definition") (pending)

    it("should be possible to import a copy of another model") (pending)

  }

}