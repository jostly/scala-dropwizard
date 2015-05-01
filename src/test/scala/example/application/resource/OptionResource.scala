package example.application.resource

import javax.ws.rs.core.MediaType
import javax.ws.rs._

@Path("/option")
class OptionResource {
  @GET
  @Path("to-string")
  @Produces(Array(MediaType.TEXT_PLAIN))
  def optionToString(@QueryParam("option") option: Option[Integer]) =
    option.map(s => s"option: $s").getOrElse("<missing>")

  @GET
  @Path("to-option")
  @Produces(Array(MediaType.TEXT_PLAIN))
  def optionToOption(@QueryParam("option") option: Option[Integer]) =
    option.map(s => s"option: $s")

  @GET
  @Path("with-default")
  @Produces(Array(MediaType.TEXT_PLAIN))
  def optionWithDefault(@QueryParam("option") @DefaultValue("-1") option: Option[Integer]) =
    option.map(s => s"option: $s")

}
