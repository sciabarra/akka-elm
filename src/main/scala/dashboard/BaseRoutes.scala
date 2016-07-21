package dashboard

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.typesafe.config.Config
import scala.concurrent.ExecutionContext

/**
  * Created by msciab on 14/06/16.
  */
trait BaseRoutes {

  // common settings
  val config: Config
  implicit val system: ActorSystem
  implicit val materializer: ActorMaterializer
  implicit val ec: ExecutionContext

}