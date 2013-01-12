TODO
====

- <del>Simplify AttributeValue: remove parent child from class to separate container class.</del>
- <del>Move maps from model.attributes to model.containers.</del>
- ParentChildContainer: hierarchical structure with nodes having multiple parents and multiple children.
- RootNode: has no parents, only children.
- ChildNode: has multiple parents and multiple children.
- Cycles are not allowed: a child my not have one of its parents as its direct child.
- Attribute definition and attribute value have different containers. In Attribute definition container a leaf attribute definition may appear only once on each node. In Attribute value container a leaf attribute value may appear multiple times as long as the keys are different.
