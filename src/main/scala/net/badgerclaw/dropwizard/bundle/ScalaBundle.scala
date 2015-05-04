package net.badgerclaw.dropwizard.bundle

import com.fasterxml.jackson.module.scala.DefaultScalaModule
import io.dropwizard.Bundle
import io.dropwizard.setup.{Bootstrap, Environment}
import net.badgerclaw.dropwizard.inject.collection.{ScalaCollectionsQueryParamFactoryProvider, CollectionParamFeature}
import net.badgerclaw.dropwizard.inject.option.{OptionMessageBodyWriter, OptionParamFeature}

class ScalaBundle extends Bundle {
  def initialize(bootstrap: Bootstrap[_]) {
    bootstrap.getObjectMapper.registerModule(new DefaultScalaModule())
  }

  def run(environment: Environment) {
    environment.jersey().getResourceConfig.
      register(classOf[OptionParamFeature]).
      register(classOf[OptionMessageBodyWriter]).
      register(classOf[CollectionParamFeature])

  }
}
