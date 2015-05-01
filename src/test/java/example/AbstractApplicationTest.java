package example;

import example.application.ExampleApplication;
import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.ClassRule;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

public abstract class AbstractApplicationTest {

    public abstract String base();

    @ClassRule
    public static final DropwizardAppRule<Configuration> RULE =
            new DropwizardAppRule<Configuration>(ExampleApplication.class, ResourceHelpers.resourceFilePath("test.yml"));

    private static Client _client;

    public WebTarget client() {
        if (_client == null) _client = new JerseyClientBuilder(RULE.getEnvironment()).build("test client");
        return _client.target(String.format("http://localhost:%d/%s", RULE.getLocalPort(), base()));
    }

}
