package com.gmail.at.pukanito.model.dsl

import com.gmail.at.pukanito.model.attributes.{AttributeIdentifier,AttributeDefinition}

trait AttributeModel {

  private var definedAttributes: Map[AttributeIdentifier, AttributeDefinition] = Map()

  private class isOfType(val id: String) {
  }

  private val IntegerAttribute = new isOfType("Integer")

  private class hasName {  }

  private class hasKey(val h: hasWord) {
    def and(id: AttributeIdentifier) = { h.attributeValueKeyIds += id; this }
  }

  private class hasParents(val h: hasWord) {
    def and(id: AttributeIdentifier) = { h.initialParents += id; this }
  }

  private class hasChildren(val h: hasWord) {
    def and(id: AttributeIdentifier) = { h.initialChildren += id; this }
  }

  private class hasWord {
    private var attributeId: AttributeIdentifier = ""
    private[AttributeModel] var attributeValueKeyIds: Set[AttributeIdentifier] = Set()
    private[AttributeModel] var initialChildren: Set[AttributeIdentifier] = Set()
    private[AttributeModel] var initialParents: Set[AttributeIdentifier] = Set()
    private[AttributeModel] def clear: Unit = { attributeId = ""; attributeValueKeyIds = Set(); initialChildren = Set(); initialParents = Set() }
    private[AttributeModel] def build: AttributeDefinition = {
      new AttributeDefinition(attributeId, attributeValueKeyIds.toList, initialChildren.map(definedAttributes(_)), initialParents.map(definedAttributes(_)))
    }
    def name(id: AttributeIdentifier): hasName = { attributeId = id; new hasName }
    def keys(ids: AttributeIdentifier*): hasKey = { attributeValueKeyIds ++= ids; new hasKey(this)  }
    def parents(ids: AttributeIdentifier*): hasParents = { initialParents ++= ids; new hasParents(this) }
    def children(ids: AttributeIdentifier*): hasChildren = { initialChildren ++= ids; new hasChildren(this) }
  }

  protected[AttributeModel] val has = new hasWord

  private class attributeWord(val t: isOfType = IntegerAttribute) {
    def apply(body: => Unit): AttributeDefinition = {
      has.clear
      body
      val a = has.build
      definedAttributes += a.attributeId -> a
      return a
    }
  }

  protected[AttributeModel] val attribute = new attributeWord

}

object testmodel extends AttributeModel {

  val x = "testprefix"

  attribute {
    has name x + "test"
    has keys "A" and "B"
    has parents "P" and "Q"
    has children "X" and "Y" and "Z"
    has children ("A", "B", "N", "M", "O", "P")
  }
}