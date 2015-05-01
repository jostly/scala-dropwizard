package example.application.resource

import javax.ws.rs.core.MediaType
import javax.ws.rs._

@Path("/basic")
@Consumes(Array(MediaType.TEXT_PLAIN))
@Produces(Array(MediaType.TEXT_PLAIN))
class BasicResource {

  @GET
  @Path("ping")
  def ping() = "Pong"

  @GET
  @Path("hello")
  def hello(@QueryParam("name") name: String) = s"Hello, $name!"

  @GET
  @Path("x2")
  def x2(@QueryParam("x") x: Int) = x*2

  @GET
  @Path("greet/{name}")
  @Produces(Array(MediaType.TEXT_PLAIN))
  def greet(@PathParam("name") name: String) = s"Greetings, $name!"

}
