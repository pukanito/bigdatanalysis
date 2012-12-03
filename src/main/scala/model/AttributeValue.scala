package model

sealed trait AttributeValue {
    def attribute: Attribute
}
case class StringAttributeValue(attribute: Attribute, value: String) extends AttributeValue
case class IntAttributeValue(attribute: Attribute, value: Int) extends AttributeValue