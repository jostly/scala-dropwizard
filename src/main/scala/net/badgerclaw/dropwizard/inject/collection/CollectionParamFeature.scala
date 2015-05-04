package net.badgerclaw.dropwizard.inject.collection

import javax.ws.rs.core.{Feature, FeatureContext}

class CollectionParamFeature extends Feature {
  override def configure(context: FeatureContext): Boolean = {
    context.register(new CollectionParamBinder, 0)
    true
  }
}
