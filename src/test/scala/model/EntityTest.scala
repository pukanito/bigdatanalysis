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
        }
        
        it("should be able to contain different attribute types") {
            val attr1 = StringAttribute("ATTR1")
            val attr2 = StringAttribute("ATTR2")
            val attr3 = IntAttribute("ATTR3")
            val val1 = attr1.createValue("aaa")
            val val2 = attr2.createValue("bbb")
            val val3 = attr3.createValue(123)
            val entity = DefaultEntity("ENTITY_1", Map(
                    val1.attribute.id -> val1,
                    val2.attribute.id -> val2,
                    val3.attribute.id -> val3))
            assert(entity.values("ATTR1") === val1)
            assert(entity.values("ATTR2") === val2)
            assert(entity.values("ATTR3") === val3)
        }
    }
}