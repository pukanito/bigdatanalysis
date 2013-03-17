package com.gmail.at.pukanito.controller.config

import com.typesafe.config._

object Configuration {

  private val conf = ConfigFactory.load

  def hasPath(path: String) = conf.hasPath(path)
}