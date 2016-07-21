lazy val r = project.in(file(".")).enablePlugins(ElmPlugin)

lazy val plg = project.in(file("plugin"))

name := "akka-elm"

organization  := "com.sciabarra"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http-core" % "2.4.7"
  , "com.typesafe.akka" %% "akka-http-experimental" % "2.4.7"
  , "org.scalactic" %% "scalactic" % "2.2.6"
  , "org.scalatest" %% "scalatest" % "2.2.6" % "test"
)

mainClass in reStart := Some("dashboard.AkkaServer")

elmReactorPort := 1969