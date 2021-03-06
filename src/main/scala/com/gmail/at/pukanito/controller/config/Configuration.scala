package com.gmail.at.pukanito.controller.config

import com.typesafe.config._

/**
 * Wrapper for https://github.com/typesafehub/config.
 *
 * Get configuration values for a specific environment and specification, or fall back
 * to lower prioritized configuration values if the specific ones are not defined.
 *
 * With environment and specification given the path prioritization will be:
 *
   - environment.specification.path
   - specification.path
   - environment.path
   - path
 *
 * With only environment:
 *
   - environment.path
   - path
 *
 * With only specification:
 *
   - specification.path
   - path
 *
 * Without environment or specification:
 *
   - path
 *
 * The configuration value that is defined at the highest prioritized
 * path will be returned.
 *
 * This allows for creating a default configuration, environment specific
 * configuration settings (for example development, test, production) and
 * settings depending on another specification (for example platform):
 *
 * {{{
 * configuration {
 *   setting1 = A
 *   setting2 = X
 * }
 *
 * test.configuration {
 *   setting1 = B
 * }
 *
 * test.platform.configuration {
 *   setting1 = C
 * }
 *
 * }}}
 *
 * Retrieving path 'configuration.setting1' would result in A.
 *
 * Retrieving path 'configuration.setting1' for environment 'test'
 * would result in B.
 *
 * Retrieving path 'configuration.setting1' for environment 'test'
 * and specification 'platform' would result in C.
 *
 * Retrieving path 'configuration.setting2' for environment 'test'
 * and specification 'platform' would result in X. It is not defined
 * for the environment and/or specification so falls back to a lower
 * prioritized path.
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
   * 'specification' and 'path' and has a configuration value defined, or None.
   *
   * @param path Base path to the configuration value.
   * @param specification Optional specialized value for the configuration value.
   */
  def whichPath(path: String, specification: String = ""): Option[String] = {
    /**
     * Returns a list consisting of:
     * - prefix.path (if prefix is not empty string)
     * - path
     */
    def explodePathWithPrefix(path: String, environment: String): List[String] = {
      (if (environment.isEmpty) List() else List(environment + "." + path)) ++  List(path)
    }
    explodePathWithPrefix(path, specification)
      .map((p) => explodePathWithPrefix(p, environment))
      .flatten.find(conf.hasPath(_))
  }

  /**
   * Returns true when a path has a configuration value defined.
   *
   * @param path Base path to the configuration value.
   * @param specification Optional specialized value for the configuration value.
   */
  def hasPath(path: String, specification: String = ""): Boolean = {
    ! whichPath(path, specification).isEmpty
  }

  /**
   * Returns the string value of a path.
   *
   * @param path Base path to the configuration value.
   * @param specification Optional specialized value for the configuration value.
   */
  def getString(path: String, specification: String = ""): String = {
    whichPath(path, specification) match {
      case Some(p) => conf.getString(p)
      case None => ""
    }
  }
}
