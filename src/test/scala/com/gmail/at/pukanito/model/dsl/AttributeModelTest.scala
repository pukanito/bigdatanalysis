package com.gmail.at.pukanito.model.dsl

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

class AttributeModelTest extends FunSpec with ShouldMatchers {

  describe("AttributeModel") {

    it("should be possible to create a parent-child hierarchy of attribute definitions") {
      object testmodel extends AttributeModel {
        attribute("key1") { }
        attribute("key2") { }
        attribute("parent") { has keys("key1", "key2") }
        attribute("child1") { has parents "parent" }
        attribute("child2") {  has parents "parent" }
        attribute("child3") {  has parents "parent" }
        attribute("child1ofchild1") { has parents "child1" and "parent" }
        attribute("child2ofchild1") { has parents "child1" }
        attribute("child1ofchild3") { has parents "child3" }
      }
      testmodel.attributes("parent").parents should have size (0)
      // Next one has keys and children as children
      testmodel.attributes("parent").children should have size (6)
      testmodel.attributes("parent").attributeValueKeyIds should have size (2)
      testmodel.attributes("child1").parents should have size (1)
      testmodel.attributes("child1").children should have size (2)
      testmodel.attributes("child2").parents should have size (1)
      testmodel.attributes("child2").children should have size (0)
      testmodel.attributes("child3").parents should have size (1)
      testmodel.attributes("child3").children should have size (1)
      testmodel.attributes("child1ofchild1").parents should have size (2)
      testmodel.attributes("child1ofchild1").children should have size (0)
      testmodel.attributes("child2ofchild1").parents should have size (1)
      testmodel.attributes("child2ofchild1").children should have size (0)
      testmodel.attributes("child1ofchild3").parents should have size (1)
      testmodel.attributes("child1ofchild3").children should have size (0)
    }

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

    it("should be possible to import a copy of another model") (pending)

  }

}