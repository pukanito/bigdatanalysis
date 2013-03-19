package com.gmail.at.pukanito.controller.config

import com.typesafe.config._
import com.gmail.at.pukanito.model.repository.GraphItemRepository

/**
 * Wrapper for https://github.com/typesafehub/config.
 *
 * Get configuration items for a specific environment and specification, or a default if a
 * specific platform and/or specification is not defined.
 *
 * With environment and specification given the path priorization order will be:
 *
 *  1.  environment.specification.path
 *  2.  specification.path
 *  3.  environment.path
 *  4.  path
 *
 * With only environment:
 *
 *  1.  environment.path
 *  2.  path
 *
 * With only specification:
 *
 *  1. specification.path
 *  2. path
 *
 * Without environment or specification:
 *
 *  1. path
 *
 * @constructor Open a configuration for a specific environment.
 * @param environment The name of the environment.
 */
class Configuration (
  private val environment: String = ""
) {
  private val conf = ConfigFactory.load

  /**
   * Returns a list consisting of:
   * - prefix.path (if prefix is not empty string)
   * - path
   */
  private def explodePathWithPrefix(path: String, prefix: String): List[String] = {
    (if (prefix.isEmpty) List() else List(prefix + "." + path)) ++  List(path)
  }

  /**
   * Returns the highest prioritized path that can be created from 'environment',
   * 'spec' and 'path' and has a configuration value defined, or None.
   *
   * @param path Base path to the configuration value.
   * @param spec Optional specialized value for the configuration value.
   */
  def whichPath(path: String, spec: String = ""): Option[String] = {
    explodePathWithPrefix(path, spec).map((p) =>
      explodePathWithPrefix(p, environment))
    .flatten.find(conf.hasPath(_))
  }

  /**
   * Returns true when a path has a configuration value defined.
   *
   * @param path Base path to the configuration value.
   * @param spec Optional specialized value for the configuration value.
   */
  def hasPath(path: String, spec: String = ""): Boolean = {
    ! whichPath(path, spec).isEmpty
  }

  /**
   * Returns the string value of a path.
   *
   * @param path Base path to the configuration value.
   * @param spec Optional specialized value for the configuration value.
   */
  def getString(path: String, spec: String = ""): String = {
    whichPath(path, spec) match {
      case Some(p) => conf.getString(p)
      case None => ""
    }
  }

}