TODO
====

Simplify AttributeValue: remove parent child from class to separate container class.

Move maps from model.attributes to model.containers


ParentChildContainer: hierarchical structure with nodes having multiple parents and multiple children.

RootNode: has no parents, only children

ChildNode: has multiple parents and multiple children

Cycles are not allowed: a child my not have one of its parents as its direct child.

