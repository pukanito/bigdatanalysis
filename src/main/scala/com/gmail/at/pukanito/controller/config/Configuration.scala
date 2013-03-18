package com.gmail.at.pukanito.controller.config

import com.typesafe.config._

/**
 * Wrapper for https://github.com/typesafehub/config.
 *
 * Get configuration items for a specific environment and specification, or a default if a
 * specific platform and/or specification is not defined.
 *
 * @constructor Open a configuration for a specific environment.
 * @param environment The name of the environment.
 */
class Configuration (
  private val environment: String = ""
) {
  private val conf = ConfigFactory.load

  /**
   * Check all possible paths which can be created from 'environment', 'spec' and 'path'
   * and return the highest prioritized one or None.
   *
   * Priority of path is:
   * 1. environment.spec.path
   * 2. spec.path
   * 3. environment.path
   * 4. path
   */
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