package model

trait Datamodel {
    def id: String
    def attributes = Map[String, Attribute]()
}

sealed trait Attribute {
    type ValueType
    def id: String
    def createValue(value: ValueType): AttributeValue
}

case class StringAttribute(id: String) extends Attribute {
    type ValueType = String
    def createValue(value: String) = StringAttributeValue(this, value)
}

case class IntAttribute(id: String) extends Attribute {
    type ValueType = Int
    def createValue(value: Int) = IntAttributeValue(this, value)
}
