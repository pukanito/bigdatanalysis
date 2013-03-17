package com.gmail.at.pukanito.controller.config

import com.typesafe.config._

/**
 * Wrapper for https://github.com/typesafehub/config
 */
class Configuration (
  private val environment: String = "",
  private val spec: String = ""
) {
  private val conf = ConfigFactory.load

  def whichPath(path: String): Option[String] = {
    ((if (!environment.isEmpty) List(environment+"."+path) else List()) ++  List(path)).map(
        (p) => (if (!spec.isEmpty) List(p+"."+spec) else List()) ++  List(p)
    ).flatten.find(conf.hasPath(_))
  }

}