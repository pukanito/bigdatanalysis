package com.gmail.at.pukanito.model.dsl

import com.gmail.at.pukanito.model.attributes.{AttributeIdentifier,AttributeDefinition, AttributeDefinitionNode}

/**
 * Exception thrown when an attribute definition is added to a model with an already existing id.
 *
 * @constructor Create a duplicate attribute definition exception.
 * @param value The item that failed to added.
 */
class DuplicateAttributeDefinitionException(value: AttributeDefinition)
  extends RuntimeException("Duplicate attribute definition: " + value.attributeId) {}

/**
 * Trait for constructing an attribute definition nodes hierarchy with a special DSL.
 * The attribute definition nodes hierarchy is the attribute model.
 *
 * To create a model:
 * {{{
 * object someModel extends AttributeModel {
 *   attribute("key1") { }
 *   attribute("key2") { }
 *   attribute("parent") { has keys("key1", "key2") }
 *   attribute("child1") { has parents "parent" }
 *   attribute("child2") {  has parents "parent" }
 *   attribute("child3") {  has parents "parent" }
 *   attribute("child1ofchild1") { has parents "child1" and "parent" }
 *   attribute("child2ofchild1") { has parents "child1" }
 *   attribute("child1ofchild3") { has parents "child3" }
 * }
 * }}}
 *
 * or
 *
 * {{{
 * val someModel = new AttributeModel {
 *   attribute("key1") { }
 *   attribute("key2") { }
 *   attribute("parent") { has keys("key1", "key2") }
 *   attribute("child1") { has parents "parent" }
 *   attribute("child2") {  has parents "parent" }
 *   attribute("child3") {  has parents "parent" }
 *   attribute("child1ofchild1") { has parents "child1" and "parent" }
 *   attribute("child2ofchild1") { has parents "child1" }
 *   attribute("child1ofchild3") { has parents "child3" }
 * }
 * }}}
 *
 * To access attribute definitions inside the model use the 'attributes' map,
 * it contains all AttributeDefinitionNodes by AttributeIdentifier:
 * {{{
 * val attributeDefinitionNode = someModel.attributes("id")
 * }}}
 *
 * This will return the attribute definition node of the specified attribute and from there
 * parents and children will also be accessible.
 */
trait AttributeModel {

  /**
   * Map of known attribute nodes in this model.
   */
  private var definedAttributes: Map[AttributeIdentifier, AttributeDefinitionNode] = Map()

  private class isOfType(val id: String) {}

  private val IntegerAttribute = new isOfType("Integer")

  /**
   * Returns a map of all defined attribute nodes.
   */
  def attributes = definedAttributes

  /**
   * Get the ids of the root attributes (i.e. attributes without parents)
   */
  def rootAttributes: Set[AttributeIdentifier] = {
    (for (
      (id, definition) <- definedAttributes
       if definition.parents.isEmpty
    ) yield id) (collection.breakOut)
  }

  /** The DSL fields and methods **/

  /**
   * Class which can handle 'and' word in 'has keys .. and .. and ..'
   *
   * @param h the hasWord instance which manages the keys.
   * @returns this.
   */
  private class hasKey(val h: hasWord) {
    def and(id: AttributeIdentifier) = { h.attributeValueKeyIds += id; this }
  }

  /**
   * Class which can handle 'and' word in 'has children .. and .. and ..'
   *
   * @param h the hasWord instance which manages the children.
   * @returns this.
   */
  private class hasChildren(val h: hasWord) {
    def and(id: AttributeIdentifier) = { h.initialChildren += id; this }
  }

  /**
   * Class which can handle 'and' word in 'has parents .. and .. and ..'
   *
   * @param h the hasWord instance which manages the parents.
   * @returns this.
   */
  private class hasParents(val h: hasWord) {
    def and(id: AttributeIdentifier) = { h.initialParents += id; this }
  }

  /**
   * Builder class for an attribute definition node.
   */
  private class hasWord {
    /**
     * Attribute property: specified key ids.
     */
    private[AttributeModel] var attributeValueKeyIds: Set[AttributeIdentifier] = Set()

    /**
     * Attribute property: specified children ids.
     */
    private[AttributeModel] var initialChildren: Set[AttributeIdentifier] = Set()

    /**
     * Attribute property: specified parent ids.
     */
    private[AttributeModel] var initialParents: Set[AttributeIdentifier] = Set()

    /**
     * Clear the builder.
     */
    private[AttributeModel] def clear: Unit = { attributeValueKeyIds = Set(); initialChildren = Set(); initialParents = Set() }

    /**
     * Build the attribute definition node from the collected properties or add properties to an already existing definition.
     *
     * @param id the id of the attribute to create.
     * @returns the constructed attribute definition node.
     */
    private[AttributeModel] def build(id: String): AttributeDefinitionNode = {
      val attr = definedAttributes get id match {
        case Some(attr) =>
          if (attributeValueKeyIds.size > 0) throw new RuntimeException("Cannot add attribute keys after first definition of attribute '" + attr.attributeId + "'")
          attr
        case None =>
          val attr = new AttributeDefinitionNode(id, attributeValueKeyIds.toList)
          definedAttributes += attr.attributeId -> attr
          attributeValueKeyIds foreach (attr += definedAttributes(_))
          attr
      }
      initialChildren foreach (attr += definedAttributes(_))
      initialParents foreach (definedAttributes(_) += attr)
      attr
    }

    /**
     * Handler for the 'keys' word in 'has keys .. and .. and ..'
     *
     * @param ids the specified keys.
     * @returns a hasKey instance.
     */
    def keys(ids: AttributeIdentifier*): hasKey = { attributeValueKeyIds ++= ids; new hasKey(this)  }

    /**
     * Handler for the 'parents' word in 'has parents .. and .. and ..'
     *
     * @param ids the specified parent ids.
     * @returns a hasParents instance.
     */
    def parents(ids: AttributeIdentifier*): hasParents = { initialParents ++= ids; new hasParents(this) }

    /**
     * Handler for the 'children' word in 'has children .. and .. and ..'
     *
     * @param ids the specified children ids.
     * @returns a hasChildren instance.
     */
    def children(ids: AttributeIdentifier*): hasChildren = { initialChildren ++= ids; new hasChildren(this) }
  }

  /**
   * Definition of the 'has' word so that it can be used in attribute definition nodes.
   */
  protected[AttributeModel] val has = new hasWord

  /**
   * Class which implements the control structure for creating attribute definition nodes: the 'attribute' word.
   *
   * @param t the type of the attribute definition.
   */
  private class attributeWord(val t: isOfType = IntegerAttribute) {

    /**
     * Handler for the 'attribute' word.
     *
     * @param id the id of the attribute to create.
     * @returns the constructed attribute definition.
     */
    def apply(id: String)(body: => Unit): AttributeDefinitionNode = {
      has.clear
      body
      has.build(id)
    }
  }

  /**
   * Definition of the 'attribute' word so that it can be used in attribute models.
   */
  protected[AttributeModel] val attribute = new attributeWord

  /**
   * Class which implements including another attribute model using the 'include' word.
   *
   * The included model will be a copy of the original because AttributeDefinitionNode is not immutable.
   */
  private class includeWord {
    def apply(that: AttributeModel): Unit = {
      // Create copies of attributes first.
      that.definedAttributes foreach { case (thatAttrId, thatAttrDef) =>
        if (definedAttributes contains thatAttrId) throw new DuplicateAttributeDefinitionException(thatAttrDef)
        definedAttributes += thatAttrId -> thatAttrDef.copy
      }
      // Then add parent-child relationships of all added attributes.
      that.definedAttributes foreach { case (thatAttrId, thatAttrDef) =>
        thatAttrDef.children foreach { case (_, childAttrDef) => definedAttributes(thatAttrId) +=  definedAttributes(childAttrDef.attributeId)} }
    }
  }

  /**
   * Definition of the 'include' word so that it can be used in attribute models.
   */
  protected[AttributeModel] val include = new includeWord

}
