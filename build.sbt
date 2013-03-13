name := "Big Data Analysis"

version := "0.1"

scalaVersion := "2.10.0"

scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature")

libraryDependencies += "org.mongodb" %% "casbah" % "2.5.0"

libraryDependencies += "org.scalatest" % "scalatest_2.10" % "2.0.M5b" % "test"