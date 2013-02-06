package com.gmail.at.pukanito.model.attributes

import com.gmail.at.pukanito.model.container.{GraphItem,GraphItemKey}

/**
 * The base class for all attribute definitions.
 *
 * @constructor Create a base attribute definition with a specific id.
 * @param attributeId The attribute identifier to assign to the AttributeDefinition
 * @param attributeValueKeyIds List of identifiers that are part of the key of an attribute value
 *          that is associated with this attribute definition.
 */
class AttributeDefinition (
  val attributeId: AttributeIdentifier,
  val attributeValueKeyIds: List[AttributeIdentifier] = List(),
  initialChildren: Set[GraphItem[AttributeDefinition]] = Set[GraphItem[AttributeDefinition]](),
  initialParents: Set[GraphItem[AttributeDefinition]] = Set[GraphItem[AttributeDefinition]]()
) extends GraphItem[AttributeDefinition](initialChildren, initialParents) {

  override def key = attributeId.toString

  def this(attributeId: AttributeIdentifier, body: => Unit) = {
    this(attributeId)
    body
  }

}

trait AttributeModel {

  private[AttributeModel] class isOfType(val id: String) {
  }

  private[AttributeModel] val IntegerAttribute = new isOfType("Integer")

  private[AttributeModel] class hasName {  }

  private[AttributeModel] class hasKey(val h: hasWord) {
    def and(id: AttributeIdentifier) = { h.attributeValueKeyIds += id; this }
  }

  private[AttributeModel] class hasParents(val h: hasWord) {
    def and(id: AttributeIdentifier) = { h.initialParents += id; this }
  }

  private[AttributeModel] class hasChildren(val h: hasWord) {
    def and(id: AttributeIdentifier) = { h.initialChildren += id; this }
  }

  private[AttributeModel] class hasWord {
    private[AttributeModel] var attributeId: AttributeIdentifier = ""
    private[AttributeModel] var attributeValueKeyIds: Set[AttributeIdentifier] = Set()
    private[AttributeModel] var initialChildren: Set[AttributeIdentifier] = Set()
    private[AttributeModel] var initialParents: Set[AttributeIdentifier] = Set()
    private[AttributeModel] def clear: Unit = { attributeId = ""; attributeValueKeyIds = Set(); initialChildren = Set(); initialParents = Set() }
    private[AttributeModel] def build: AttributeDefinition = { new AttributeDefinition("x") }
    def name(id: AttributeIdentifier): hasName = { attributeId = id; new hasName }
    def keys(ids: AttributeIdentifier*): hasKey = { attributeValueKeyIds ++= ids; new hasKey(this)  }
    def parents(ids: AttributeIdentifier*): hasParents = { initialParents ++= ids; new hasParents(this) }
    def children(ids: AttributeIdentifier*): hasChildren = { initialChildren ++= ids; new hasChildren(this) }
  }

  protected[AttributeModel] val has = new hasWord

  private[AttributeModel] class attributeWord(val t: isOfType = IntegerAttribute) {
    def apply(body: => Unit): AttributeDefinition = {
      has.clear
      body
      has.build
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