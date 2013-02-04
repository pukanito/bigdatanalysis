# TODO


## DSL for attribute definition

It should be easy to create attribute definitions for example:

    attribute "id" {
      type String|Integer|...
      keys "id1", "id2", ...
      [ parents [ "id1" [ , "id2" [ , ... ] ] ] ]
      [ children [ "id1" [ , "id2" [ , ... ] ] ] ]
    }

