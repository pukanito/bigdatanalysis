package model

import org.scalatest.FunSpec

class EntityTest extends FunSpec {

    describe("Entity") {
        it("should be comparable to other entities") {
            val strAttr = StringAttribute("MY_STRING")
            val strValue1 = strAttr.createValue("some string value")
            val strValue2 = strAttr.createValue("some other value")
            val entity0 = DefaultEntity("ENTITY_1")
            val entity1 = DefaultEntity("ENTITY_1", Map(strValue1.attribute.id -> strValue1))
            val entity2 = entity1.update(strValue2)
            assert(entity0.update(strValue1) === entity1)
            assert(entity1 != entity2)
            println(entity1)
            println(entity2)
        }
    }
}