package com.gmail.at.pukanito.controller.config

import com.typesafe.config._

/**
 * Wrapper for https://github.com/typesafehub/config
 */
class Configuration (
  private val environment: String = ""
) {
  private val conf = ConfigFactory.load

  def whichPath(path: String, spec: String = ""): Option[String] = {
    ((if (!spec.isEmpty) List(spec+"."+path) else List()) ++  List(path)).map(
        (p) => (if (!environment.isEmpty) List(environment+"."+p) else List()) ++  List(p)
    ).flatten.find(conf.hasPath(_))
  }

  def hasPath(path: String, spec: String = ""): Boolean = {
    !whichPath(path, spec).isEmpty
  }

  def getString(path: String, spec: String = ""): String = {
    conf.getString(whichPath(path, spec).get)
  }
}