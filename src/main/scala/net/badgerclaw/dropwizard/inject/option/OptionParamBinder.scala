package net.badgerclaw.dropwizard.inject.option

import javax.inject.Singleton
import javax.ws.rs.ext.ParamConverterProvider

import org.glassfish.hk2.utilities.binding.AbstractBinder

class OptionParamBinder extends AbstractBinder {
  override def configure(): Unit =
    bind(classOf[OptionParamConverterProvider]).to(classOf[ParamConverterProvider]).in(classOf[Singleton])
}
