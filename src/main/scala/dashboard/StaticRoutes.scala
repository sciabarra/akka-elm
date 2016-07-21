package dashboard

import akka.http.scaladsl.model.{ContentType, MediaType, HttpResponse}
import akka.http.scaladsl.model.headers.`Content-Type`
import akka.http.scaladsl.server.Directives._


/**
  * Created by msciab on 12/06/16.
  */
trait StaticRoutes extends BaseRoutes {
  val homePage =
    """
    """.stripMargin

  val staticRoutes = pathSingleSlash {
    //logger.debug("static routes")
    getFromFile(config.getString("app.home"))
  } ~ pathPrefix("www") {
    getFromBrowseableDirectory(config.getString("app.www"))
  }
}
