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

trait attributeWord {

  class hasKey(val h: hasWord) {
    def and(key: String): hasKey = {
      this
    }
  }

  class hasParents(val h: hasWord) {
    def and(key: String): hasParents = {
      this
    }
  }

  class hasChildren(val h: hasWord) {
    def and(key: String): hasChildren = {
      this
    }
  }

  class hasWord {
    def keys(key: String*): hasKey = {
      new hasKey(this)
    }
    def parents(key: String*): hasParents = {
      new hasParents(this)
    }
    def children(key: String*): hasChildren = {
      new hasChildren(this)
    }
    def x = {}
  }

  val has = new hasWord

  class isOfType(val id: String) {
  }

  val IntegerAttribute = new isOfType("Integer")

  class attributeWord {
    def apply(id: String)(body: => Unit): AttributeDefinition = {
      body
      new AttributeDefinition(id)
    }
  }

  val attribute = new attributeWord

}

object testmodel extends attributeWord {
  attribute("test") {
    IntegerAttribute
    has keys "A" and "B"
    has parents "P" and "Q"
    has children "X" and "Y" and "Z"
    has children ("A", "B", "N", "M", "O", "P")
  }
}