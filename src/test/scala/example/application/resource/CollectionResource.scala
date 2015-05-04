package example.application.resource

import javax.ws.rs._
import javax.ws.rs.core.MediaType

import org.slf4j.{LoggerFactory, Logger}

@Path("/collection")
@Consumes(Array(MediaType.TEXT_PLAIN))
@Produces(Array(MediaType.TEXT_PLAIN))
class CollectionResource {

  private final val logger: Logger = LoggerFactory.getLogger(getClass)

  @GET
  @Path("sorter")
  def sorter(@QueryParam("list") list: Seq[String]) = {
    logger.info("Received {}", list)
    list.sorted.mkString(", ")
  }

}
