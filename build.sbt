name := "Big Data Analysis"

version := "0.1"

scalaVersion := "2.9.2"

resolvers += "repo.novus rels" at "http://repo.novus.com/releases/"

resolvers += "repo.novus snaps" at "http://repo.novus.com/snapshots/"

libraryDependencies += "com.novus" %% "salat-core" % "1.9.1"

libraryDependencies += "org.mongodb" %% "casbah" % "2.4.1"

libraryDependencies += "cc.abstra.pasilla" % "scuby" % "0.1.8"
