package example;

import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class BasicTest extends AbstractApplicationTest {

    @Override
    public String base() {
        return "basic";
    }

    @Test
    public void testSimpleGet() {
        Response response = client().path("ping").request().get();

        assertThat(response.getStatus(), is(200));
        assertThat(response.readEntity(String.class), is("Pong"));
    }

    @Test
    public void testGetWithStringQueryParameter() {
        Response response = client().path("hello").queryParam("name", "johan").request().get();

        assertThat(response.getStatus(), is(200));
        assertThat(response.readEntity(String.class), is("Hello, johan!"));
    }

    @Test
    public void testGetWithIntQueryParameter() {
        Response response = client().path("x2").queryParam("x", 47).request().get();
        assertThat(response.getStatus(), is(200));
        assertThat(response.readEntity(Integer.class), is(94));
    }

    @Test
    public void testGetWithStringPathParameter() {
        Response response = client().path("greet/johan").request().get();

        assertThat(response.getStatus(), is(200));
        assertThat(response.readEntity(String.class), is("Greetings, johan!"));
    }
}