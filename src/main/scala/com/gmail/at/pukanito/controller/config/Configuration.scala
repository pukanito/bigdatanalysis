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
   * Returns the highest prioritized path that can be created from 'environment',
   * 'spec' and 'path' or None.
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

  /**
   * Returns true when a path has a configuration value defined.
   */
  def hasPath(path: String, spec: String = ""): Boolean = {
    ! whichPath(path, spec).isEmpty
  }

  /**
   * Returns the string value of a path.
   */
  def getString(path: String, spec: String = ""): String = {
    whichPath(path, spec) match {
      case Some(p) => conf.getString(p)
      case None => ""
    }
  }
}