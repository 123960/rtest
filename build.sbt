name := """play-scala"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  specs2 % Test
)

libraryDependencies += "io.reactivex" %% "rxscala" % "0.25.0"

libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.12.4" % "test"

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test"

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator


fork in run := true