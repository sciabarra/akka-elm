package dashboard

import sbt._, Keys._
import sbt.plugins.JvmPlugin

object ElmPlugin
  extends AutoPlugin
  with ElmSettings {

  override def requires = JvmPlugin

  val autoImport = ElmKeys

  override val projectSettings = elmSettings


}
