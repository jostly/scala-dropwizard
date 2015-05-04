package example.application

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature._
import example.application.resource.{CollectionResource, BasicResource, OptionResource, JsonResource}
import io.dropwizard.Configuration
import io.dropwizard.setup.{Bootstrap, Environment}
import net.badgerclaw.dropwizard.ScalaApplication
import net.badgerclaw.dropwizard.bundle.ScalaBundle
import org.slf4j.{Logger, LoggerFactory}

class ExampleApplication extends ScalaApplication[Configuration] {
  val jsonResource = new JsonResource
  val optionResource = new OptionResource
  val basicResource = new BasicResource
  val collectionResource = new CollectionResource

  override def getName = "ExampleApplication"

  override def initialize(bootstrap: Bootstrap[Configuration]) {
    bootstrap.addBundle(new ScalaBundle)
    //bootstrap.addBundle(new ScalaValidatorsBundle)
  }

  def run(configuration: Configuration, environment: Environment) {
    val objectMapper: ObjectMapper = environment.getObjectMapper
    objectMapper.enable(INDENT_OUTPUT)
    objectMapper.enable(WRITE_DATES_AS_TIMESTAMPS)
    environment.jersey.register(basicResource)
    environment.jersey.register(optionResource)
    environment.jersey.register(jsonResource)
    environment.jersey.register(collectionResource)
    logger.info("ProductCatalogApplication started!")
  }

  private final val logger: Logger = LoggerFactory.getLogger(getClass)
}
