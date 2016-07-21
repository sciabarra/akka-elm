package dashboard

import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpRequest
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.RouteResult.Complete
import akka.stream.scaladsl.{Sink, Source}

/**
  * Created by msciab on 12/06/16.
  */
trait ElmProxyRoutes extends BaseRoutes {

  lazy val tgtHost = config.getString("elm.host")
  lazy val tgtPort = config.getInt("elm.port")

  val elmProxyRoutes = Route { ctx =>
    // get request path and check if it is reactor path
    val path = ctx.request.uri.path.toString
    if (path.endsWith(".elm") || path.startsWith("/_")) {
      // proxy the request to the reactor
      val flow = Http(system).outgoingConnection(tgtHost, tgtPort)
      Source.single(HttpRequest(uri = path))
        .via(flow)
        .runWith(Sink.head)
        .map(Complete(_))
    } else {
      ctx.reject()
    }
  }
}