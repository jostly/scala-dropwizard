package net.badgerclaw.dropwizard.inject.option

import java.io.OutputStream
import java.lang.annotation.Annotation
import java.lang.reflect.{ParameterizedType, Type}
import javax.inject.{Provider, Inject}
import javax.ws.rs.{Produces, ext, NotFoundException}
import javax.ws.rs.core.{MultivaluedMap, MediaType}
import javax.ws.rs.ext.MessageBodyWriter

import org.glassfish.jersey.message.MessageBodyWorkers

@ext.Provider
@Produces(Array(MediaType.WILDCARD))
class OptionMessageBodyWriter @Inject()(mbw: Provider[MessageBodyWorkers]) extends MessageBodyWriter[Option[AnyRef]] {

  override def isWriteable(`type`: Class[_],
                           genericType: Type,
                           annotations: Array[Annotation],
                           mediaType: MediaType): Boolean =
    classOf[Option[_]].isAssignableFrom(`type`)

  override def writeTo(t: Option[AnyRef],
                       `type`: Class[_],
                       genericType: Type,
                       annotations: Array[Annotation],
                       mediaType: MediaType,
                       httpHeaders: MultivaluedMap[String, AnyRef],
                       entityStream: OutputStream): Unit = {
    if (t.isDefined) {
      val v = t.get
      val actualGenericType: Type = genericType.asInstanceOf[ParameterizedType].getActualTypeArguments()(0)
      val c: Class[AnyRef] = v.getClass.asInstanceOf[Class[AnyRef]]
      val writer = mbw.get.getMessageBodyWriter[AnyRef](c, actualGenericType, annotations, mediaType)
      writer.writeTo(v, c, actualGenericType, annotations, mediaType, httpHeaders, entityStream)
    } else {
      throw new NotFoundException
    }
  }

  override def getSize(t: Option[AnyRef],
                       `type`: Class[_],
                       genericType: Type,
                       annotations: Array[Annotation],
                       mediaType: MediaType): Long = 0
}
