package dashboard

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import akka.http.scaladsl.server.Directives._

/**
  * Created by msciab on 12/06/16.
  */
/**
  * Main class for the server
  */
object
AkkaServer
  extends App
  with BaseRoutes
  with ElmProxyRoutes
  with StaticRoutes {

  // define common elements
  val config = ConfigFactory.load()
  implicit val system = ActorSystem("ActorSystem")
  implicit val materializer = ActorMaterializer()
  implicit val ec = system.dispatcher

  // start working
  Http().bindAndHandle(
    staticRoutes ~ elmProxyRoutes,
    config.getString("app.host"),
    config.getInt("app.port"))
}
