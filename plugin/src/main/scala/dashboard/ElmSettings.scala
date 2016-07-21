package dashboard

import java.lang.Process

import sbt._, Keys._
import java.util.concurrent.atomic.AtomicReference

import scala.util.{Failure, Success, Try}

trait ElmSettings {

  import ElmKeys._

  // status
  val currentReactor = new AtomicReference[Option[java.lang.Process]](None)

  // tasks
  lazy val elmReactorTask = elmReactor := {

    val args: Seq[String] = Def.spaceDelimited("<arg>").parsed

    val port = elmReactorPort.value.toInt
    val base = elmReactorDirectory.value
    val log = streams.value.log
    val usage = "usage: start | stop | status"

    def isRunning(quietRun: Boolean, quietNot: Boolean) =
      Try(new java.net.Socket("localhost", port).close) match {
        case Success(_) =>
          //println("success: running")
          if (!quietRun)
            println(s"elm-reactor running in port ${port}")
          true
        case Failure(_) =>
          //println("failure: not running")
          if (!quietNot)
            println(s"elm-reactor not running")
          false
      }

    args.headOption match {
      case None => println(usage)

      case Some("status") =>
        isRunning(false, false)

      case Some("stop") =>
        if (isRunning(true, false)) {
          /* does not work....
          val process = currentReactor.get.get
          val processClass = process.getClass
          println(s"stopping ${process} ${processClass}")
          if (processClass.getName.equals("java.lang.UNIXProcess")) {
            // on unix, need to send an INT signal
            val field = processClass.getDeclaredField("pid")
            field.setAccessible(true)
            val pid = field.get(process).toString
            val cmd = s"kill -INT ${pid}"
            println(cmd)
            java.lang.Runtime.getRuntime.exec(cmd).waitFor
          */
          if (java.io.File.separatorChar == '/') {
            // solution fine on mac... hopefully on linux too
            java.lang.Runtime.getRuntime.exec("killall -9 elm-reactor").waitFor
          } else {
            // to be tested on windows....
            currentReactor.get.map(_.destroy())
          }

          if (!isRunning(true, true)) {
            currentReactor.set(None)
          } else {
            println("cannot stop process - you need to kill manually")
          }
        }
      case Some("start") =>
        if (!isRunning(false, true)) {
          val pb = new java.lang.ProcessBuilder("/bin/sh", "-c", s"elm-reactor -p ${port}")
          pb.directory(elmReactorDirectory.value)
          pb.redirectErrorStream(true)
          pb.redirectOutput(ProcessBuilder.Redirect.INHERIT)
          val process = pb.start
          currentReactor set Some(process)
          println(s"starting elm-reactor ${process}")
        }
      case Some(thing) =>
        println(usage)
    }
  }

  // settings
  val elmSettings = Seq(elmReactorTask
    , elmReactorDirectory := baseDirectory.value / "src" / "main" / "elm"
    , elmReactorPort := 8000
  )
}


