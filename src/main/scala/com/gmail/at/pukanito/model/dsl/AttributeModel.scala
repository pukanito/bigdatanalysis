package com.gmail.at.pukanito.model.dsl

import com.gmail.at.pukanito.model.attributes.{AttributeIdentifier,AttributeDefinition}

/**
 * Trait for constructing attribute definitions with a special DSL.
 */
trait AttributeModel {

  /**
   * Map of known attributes in this model.
   */
  private var definedAttributes: Map[AttributeIdentifier, AttributeDefinition] = Map()

  private class isOfType(val id: String) {}

  private val IntegerAttribute = new isOfType("Integer")

  /**
   * Class which can handle 'and' word in 'has keys .. and .. and ..'
   *
   * @param h the hasWord instance which manages the keys.
   */
  private class hasKey(val h: hasWord) {
    def and(id: AttributeIdentifier) = { h.attributeValueKeyIds += id; this }
  }

  /**
   * Class which can handle 'and' word in 'has children .. and .. and ..'
   *
   * @param h the hasWord instance which manages the children.
   */
  private class hasChildren(val h: hasWord) {
    def and(id: AttributeIdentifier) = { h.initialChildren += id; this }
  }

  /**
   * Class which can handle 'and' word in 'has parents .. and .. and ..'
   *
   * @param h the hasWord instance which manages the parents.
   */
  private class hasParents(val h: hasWord) {
    def and(id: AttributeIdentifier) = { h.initialParents += id; this }
  }

  /**
   * Builder class for an attribute definition.
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
     * Build the attribute definition from the collected properties or add properties to an already existing definition.
     *
     * @param id the id of the attribute to create.
     */
    private[AttributeModel] def build(id: String): AttributeDefinition = {
      definedAttributes get id match {
        case Some(a) =>
          if (attributeValueKeyIds.size > 0) throw new RuntimeException("Cannot add attribute keys after first definition of attribute '" + a.attributeId + "'")
          initialChildren foreach (a += definedAttributes(_))
          initialParents foreach (definedAttributes(_) += a)
          a
        case None =>
          val a = new AttributeDefinition(id, attributeValueKeyIds.toList, (initialChildren ++ attributeValueKeyIds).map(definedAttributes(_)), initialParents.map(definedAttributes(_)))
          definedAttributes += a.attributeId -> a
          a
      }
    }

    /**
     * Handler for the 'keys' word in 'has keys .. and .. and ..'
     *
     * @param ids the specified keys.
     */
    def keys(ids: AttributeIdentifier*): hasKey = { attributeValueKeyIds ++= ids; new hasKey(this)  }

    /**
     * Handler for the 'parents' word in 'has parents .. and .. and ..'
     *
     * @param ids the specified parent ids.
     */
    def parents(ids: AttributeIdentifier*): hasParents = { initialParents ++= ids; new hasParents(this) }

    /**
     * Handler for the 'children' word in 'has children .. and .. and ..'
     *
     * @param ids the specified children ids.
     */
    def children(ids: AttributeIdentifier*): hasChildren = { initialChildren ++= ids; new hasChildren(this) }
  }

  /**
   * Definition of the 'has' word so that it can be used in attribute definitions.
   */
  protected[AttributeModel] val has = new hasWord

  /**
   * Class which implements the control structure for creating attribute definitions: the 'attribute' word.
   *
   * @param t the type of the attribute definition.
   */
  private class attributeWord(val t: isOfType = IntegerAttribute) {
    def apply(id: String)(body: => Unit): AttributeDefinition = {
      has.clear
      body
      has.build(id)
    }
  }

  /**
   * Definition of the 'attribute' word so that it can be used in attribute models.
   */
  protected[AttributeModel] val attribute = new attributeWord

}
