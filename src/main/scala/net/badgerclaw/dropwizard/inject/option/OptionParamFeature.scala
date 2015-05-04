package net.badgerclaw.dropwizard.inject.option

import javax.ws.rs.core.{Feature, FeatureContext}

class OptionParamFeature extends Feature {
  override def configure(context: FeatureContext): Boolean = {
    context.register(new OptionParamBinder, 0)
    true
  }
}
