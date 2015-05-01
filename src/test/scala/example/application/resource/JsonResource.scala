package example.application.resource

import javax.ws.rs._
import javax.ws.rs.core.MediaType

case class Doubler(ordinal: Int, value: String)

@Path("/json")
@Produces(Array(MediaType.APPLICATION_JSON))
@Consumes(Array(MediaType.APPLICATION_JSON))
class JsonResource {

  /*
  @GET
  @Path("hey")
  @Produces(Array(MediaType.TEXT_PLAIN))
  def hey(@QueryParam("names") names: Array[String]) = {
    val nameList = names.mkString(", ")
    s"Hey, $nameList!"
  }
  */

  @POST
  @Path("doubler")
  def double(a: Doubler) = Doubler(a.ordinal * 2, a.value * 2)

  @POST
  @Path("sorter")
  def sort(a: List[Doubler]) = a.sortWith((a, b) => a.value < b.value)

}
