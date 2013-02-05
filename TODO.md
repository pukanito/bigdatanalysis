# TODO


## DSL for attribute definition

It should be easy to create attribute definitions for example:

    attribute("id") {
      is StringAttribute | IntegerAttribute | ...
      [ has keys "id1" [ and "id2" [ and ... ] ]
      [ has keys("id1", ...) ]
      [ has parents "id1" [ and "id2" [ and ... ] ] ]
      [ has parents("id1", ...) ]
      [ has children "id1" [ and "id2" [ and ... ] ] ]
      [ has children("id1", ...) ]
    }

