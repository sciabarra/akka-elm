package samples

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{StatusCode, HttpResponse, Uri, HttpRequest}
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Sink, Source}
import akka.util.ByteString
import scala.concurrent.duration._
import akka.http.scaladsl.model._, HttpMethods._


import scala.concurrent.Await

/**
  * Created by msciab on 13/06/16.
  */
object HttpGet extends App {

  implicit val system = ActorSystem("ActorSystem")
  implicit val materializer = ActorMaterializer()
  implicit val ec = system.dispatcher

  val uri = Uri("http://localhost:8000/src/Hello.elm")
  val host = uri.authority.host.toString
  val port = uri.authority.port

  val req = HttpRequest(uri = "/src/Hello.elm")

  println("req new:" + req)

  val flow = Http(system).outgoingConnection(host, port)

  val out = Source.single(req)
    .via(flow)
    .runWith(Sink.head)

  var res = Await.result(out, 30.second)

  val collect = Sink.reduce[ByteString](_ concat _)

  val bs = Await.result(res.entity.dataBytes.runWith(collect), 1.second)

  println(bs.decodeString("UTF-8"))

  system.terminate()
}
