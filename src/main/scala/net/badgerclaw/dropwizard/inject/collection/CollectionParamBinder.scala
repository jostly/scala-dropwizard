package net.badgerclaw.dropwizard.inject.collection

import javax.inject.Singleton
import javax.ws.rs.QueryParam

import org.glassfish.hk2.api.{InjectionResolver, TypeLiteral}
import org.glassfish.hk2.utilities.binding.AbstractBinder
import org.glassfish.jersey.server.spi.internal.ValueFactoryProvider

class CollectionParamBinder extends AbstractBinder {
  override def configure(): Unit = {
    bind(classOf[ScalaCollectionsQueryParamFactoryProvider]).to(classOf[ValueFactoryProvider]).in(classOf[Singleton])

    bind(classOf[ScalaCollectionsQueryParamFactoryProvider.InjectionResolver]).
      to(new TypeLiteral[InjectionResolver[QueryParam]]() {
    }).in(classOf[Singleton])
  }
}

