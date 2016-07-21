package samples

import akka.actor.ActorSystem
import akka.stream._
import akka.stream.scaladsl._
import scala.concurrent.duration._

import scala.concurrent.Await

/**
  * Created by msciab on 14/06/16.
  */
class Streams extends App {
  implicit val system = ActorSystem("ActorSystem")
  implicit val materializer = ActorMaterializer()
  implicit val ec = system.dispatcher

  def hello(): Unit = {
    val src = Source(0 to 3)
    val f = src.runForeach(println)
    val r = Await.result(f, 1 second)
    println(r)
  }

  def hello1(): Unit = {
    val src = Source(2 to 10)
    val facts = src.scan( (1,1) ) {
      (acc, next) =>
        next -> acc._2 * next
    }
    val pr = facts.runForeach(println)
    val r = Await.result(pr, 1 second)
    println(r)
  }

/*
  def hello2(): Unit = {

    val src = Source(1 to 10)
    val conc = src.scan( "" ) {
      (acc, next) =>
        acc + src.toString
    }
    val r = Await.result(conc, 1.second)
    println(r)
  }
*/
  //hello2()
}
