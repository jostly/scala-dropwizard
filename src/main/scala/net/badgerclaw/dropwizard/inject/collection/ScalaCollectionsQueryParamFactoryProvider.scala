package net.badgerclaw.dropwizard.inject.collection

import javax.inject.{Inject, Singleton}
import javax.ws.rs.QueryParam

import net.badgerclaw.dropwizard.inject.collection.ScalaCollectionsQueryParamFactoryProvider.QueryParamValueFactory
import org.glassfish.hk2.api.{InjectionResolver, ServiceLocator, TypeLiteral}
import org.glassfish.hk2.utilities.binding.AbstractBinder
import org.glassfish.jersey.internal.inject.ExtractorException
import org.glassfish.jersey.server.ParamException
import org.glassfish.jersey.server.internal.inject._
import org.glassfish.jersey.server.model.Parameter
import org.glassfish.jersey.server.spi.internal.ValueFactoryProvider

object ScalaCollectionsQueryParamFactoryProvider {

  class InjectionResolver extends ParamInjectionResolver[QueryParam](classOf[ScalaCollectionsQueryParamFactoryProvider]) {}

  class QueryParamValueFactory(extractor: MultivaluedParameterExtractor[_], decode: Boolean)
    extends AbstractContainerRequestValueFactory[AnyRef] {

    def provide: AnyRef = try {
      extractor.extract(getContainerRequest.getUriInfo.getQueryParameters(decode)).asInstanceOf[AnyRef]
    } catch {
      case e: ExtractorException =>
        throw new ParamException.QueryParamException(e.getCause, extractor.getName, extractor.getDefaultValueString)
    }
  }
}

class ScalaCollectionsQueryParamFactoryProvider @Inject()(mpep: MultivaluedParameterExtractorProvider, locator: ServiceLocator)
  extends AbstractValueFactoryProvider(mpep, locator, Parameter.Source.QUERY) {

  def createValueFactory(parameter: Parameter): AbstractContainerRequestValueFactory[_] =
    parameter.getSourceName match {
      case parameterName if parameterName != null && parameterName.length() != 0 =>
        Option(buildExtractor(parameterName, parameter.getDefaultValue, parameter.getRawType)).
          map(new QueryParamValueFactory(_, !parameter.isEncoded)).orNull
      case _ => null
    }

  private def buildExtractor(name: String, default: String, klass: Class[_]): MultivaluedParameterExtractor[_] = {
    if (klass == classOf[Seq[String]]) {
      new ScalaCollectionStringReaderExtractor[Seq](name, default, Seq)
    } else if (klass == classOf[List[String]]) {
      new ScalaCollectionStringReaderExtractor[List](name, default, List)
    } else if (klass == classOf[Vector[String]]) {
      new ScalaCollectionStringReaderExtractor[Vector](name, default, Vector)
    } else if (klass == classOf[IndexedSeq[String]]) {
      new ScalaCollectionStringReaderExtractor[IndexedSeq](name, default, IndexedSeq)
    } else if (klass == classOf[Set[String]]) {
      new ScalaCollectionStringReaderExtractor[Set](name, default, Set)
    } else null
  }
}