# TODO


## DSL for attribute definition

It should be easy to create attribute definitions for example:

    create attribute "name" {
      type String|Integer|...
      with keys "id1", "id2", ...
      with parents "id1", "id2", ...
      with children "id1", "id2", ...
    }

