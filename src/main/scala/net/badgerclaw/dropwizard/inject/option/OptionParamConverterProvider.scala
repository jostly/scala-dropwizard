package net.badgerclaw.dropwizard.inject.option

import java.lang.annotation.Annotation
import java.lang.reflect.Type
import javax.inject.{Inject, Singleton}
import javax.ws.rs.ext.{ParamConverter, ParamConverterProvider}

import org.glassfish.hk2.api.ServiceLocator
import org.glassfish.jersey.internal.inject.Providers
import org.glassfish.jersey.internal.util.ReflectionHelper
import org.glassfish.jersey.internal.util.collection.ClassTypePair

@Singleton
class OptionParamConverterProvider @Inject()(locator: ServiceLocator)
extends ParamConverterProvider {
  override def getConverter[T](rawType: Class[T],
                               genericType: Type,
                               annotations: Array[Annotation]): ParamConverter[T] = {

    if (rawType == classOf[Option[_]]) {
      val ctps = ReflectionHelper.getTypeArgumentAndClass(genericType)

      val ctp: ClassTypePair = if (ctps.size == 1) ctps.get(0) else null

      if (ctp == null || ctp.rawClass == classOf[String]) {
        new ParamConverter[T] {
          def fromString(value: String): T = {
            rawType.cast(Option(value))
          }

          def toString(value: T): String = {
            value.toString
          }
        }
      } else {
        import scala.collection.JavaConversions._

        val converterProviders =
          asScalaSet(Providers.getProviders(locator, classOf[ParamConverterProvider]))

        val converter = converterProviders.
          map(_.getConverter(ctp.rawClass, ctp.`type`, annotations)).
          filter(_ != null).
          map(converter => new ParamConverter[T] {
          override def fromString(value: String): T = Option(value).map(v => converter.fromString(v)).asInstanceOf[T]

          override def toString(value: T): String = value.toString
        }).headOption

        converter.asInstanceOf[Option[ParamConverter[T]]].orNull

      }
    } else null
  }
}
