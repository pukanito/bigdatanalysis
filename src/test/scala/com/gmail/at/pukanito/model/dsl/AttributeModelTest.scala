package com.gmail.at.pukanito.model.dsl

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

class AttributeModelTest extends FunSpec with ShouldMatchers {

  describe("AttributeModel") {

    it("should not throw an exception when an attribute with the same identifier is defined in different models") {
      object testmodel1 extends AttributeModel {
        attribute("test") { }
      }
      object testmodel2 extends AttributeModel {
        attribute("test") { }
      }
      // Due to lazy initialization of an object it has to be pushed a little bit.
      testmodel1.attributes
      testmodel2.attributes
    }

    it("should be possible to create a parent-child hierarchy of attribute definitions") (pending)

    it("should throw an exception when a non existing child is added on creation") {
      object testmodel extends AttributeModel {
        intercept[NoSuchElementException] { attribute("test") { has children "child1" } }
      }
      testmodel.attributes
    }

    it("should throw an exception when a non existing parent is added on creation") {
      object testmodel extends AttributeModel {
        intercept[NoSuchElementException] { attribute("test") { has parents "parent1" } }
      }
      testmodel.attributes
    }

    it("should throw an exception when a non existing child is added after creation") {
      object testmodel extends AttributeModel {
        attribute("test") { }
        intercept[NoSuchElementException] { attribute("test") { has children "child1" } }
      }
      testmodel.attributes
    }

    it("should throw an exception when a non existing parent is added after creation") {
            object testmodel extends AttributeModel {
        attribute("test") { }
        intercept[NoSuchElementException] { attribute("test") { has children "child1" } }
      }
      testmodel.attributes
    }

    it("should be possible to add parents and or children after creation") {
      object testmodel extends AttributeModel {
        attribute("key1") { }
        attribute("child1") { }
        attribute("test") { has keys "key1" }
        attribute("test") { has children "child1" }
      }
      testmodel.attributes
    }

    it("should throw an exception when keys are added after creation") {
      object testmodel extends AttributeModel {
        attribute("key1") { }
        attribute("key2") { }
        attribute("test") { has keys "key1" }
        intercept[RuntimeException] { attribute("test") { has keys "key2" } }
      }
      testmodel.attributes
    }

    it("should be possible to import a copy of another model") (pending)

  }

}