package com.gmail.at.pukanito.controller.config

import com.typesafe.config._

/**
 * Wrapper for https://github.com/typesafehub/config
 */
object Configuration {

  private val conf = ConfigFactory.load

  def hasPath(path: String, spec: String = "") =
    if (spec == "") conf.hasPath(path)
    else conf.getConfig(spec).hasPath(path)
}