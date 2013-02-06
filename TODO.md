# TODO


## DSL for attribute definition

It should be easy to create attribute definitions for example:

    attribute("id") {
      is StringAttribute | IntegerAttribute | ...
      [ has keys "id1" [ and "id2" [ and ... ] ] // implicitly include id1 and id2 as children
      [ has keys("id1", ...) ]
      [ has parents "id1" [ and "id2" [ and ... ] ] ]
      [ has parents("id1", ...) ]
      [ has children "id1" [ and "id2" [ and ... ] ] ]
      [ has children("id1", ...) ]
    }

keys: the key for an attribute value.

Without keys the key is: attributeId.
