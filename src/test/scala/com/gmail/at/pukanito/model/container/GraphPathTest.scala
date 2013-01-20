package com.gmail.at.pukanito.model.container

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

class GraphPathTest extends FunSpec with ShouldMatchers  {

  describe("GraphPath") {

    it("should be possible to represent a root item") {
      val x = new AbsolutePath()
      x should have size (0)
      x.isRoot should equal (true)
    }

    it("should be possible to represent an absolute path") {
      val x = new AbsolutePath(Map("A"->1))
      x should have size (1)
      x.isRoot should equal (false)
      x.isAbsolute should equal (true)
    }

    it("should be possible to represent a relative path") {
      val x = new RelativePath(Map("A"->1, "B"->2))
      x should have size (1)
      x.isRoot should equal (false)
      x.isAbsolute should equal (false)
    }

    it("should be possible to add a relative path to a relative path giving a new relative path") {
      val x = new AbsolutePath(Map("A"->1))
      val y = new RelativePath(Map("A"->1))
      val z = x + y
      z should have size (2)
      z.isAbsolute should equal (true)
    }

    it("should be possible to add a relative path to an absolute path giving an absolute path") {
      val x = new RelativePath(Map("A"->1))
      val y = new RelativePath(Map("A"->1))
      val z = x + y
      z should have size (2)
      z.isAbsolute should equal (false)
    }

    it("should not e possible to add an absolute path to an absolute path") (pending)

    it("should not e possible to add an absolute path to a relative path") (pending)

  }

}