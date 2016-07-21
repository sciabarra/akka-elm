package dashboard

import java.io.File

import sbt._, Keys._

object ElmKeys {

  //keys
  lazy val elmReactor = inputKey[Unit]("elm-reactor")
  lazy val elmReactorPort = settingKey[Int]("elm-reactor-port")
  lazy val elmReactorDirectory = settingKey[File]("elm-reactor-directory")
}
