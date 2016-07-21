// addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.1.1")

addSbtPlugin("se.marcuslonnberg" % "sbt-docker" % "1.4.0")

addSbtPlugin("io.spray" % "sbt-revolver" % "0.8.0")

val me = project.in(file(".")).dependsOn( file("plugin").toURI)
