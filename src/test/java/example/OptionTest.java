package example;

import org.junit.Test;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class OptionTest extends AbstractApplicationTest {

    @Override
    public String base() {
        return "option";
    }

    @Test
    public void testGetWithOptionQueryParameter() {
        WebTarget target = client().path("to-string");

        Response response = target.queryParam("option", 13).request().get();
        assertThat(response.getStatus(), is(200));
        assertThat(response.readEntity(String.class), is("option: 13"));

        response = target.request().get();
        assertThat(response.getStatus(), is(200));
        assertThat(response.readEntity(String.class), is("<missing>"));
    }

    @Test
    public void testGetWithOptionQueryParameterAndOptionResult() {
        WebTarget target = client().path("to-option");

        Response response = target.queryParam("option", 13).request().get();
        assertThat(response.getStatus(), is(200));
        assertThat(response.readEntity(String.class), is("option: 13"));

        response = target.request().get();
        assertThat(response.getStatus(), is(404));
    }

    @Test
    public void testGetWithOptionQueryParameterAndDefaultValue() {
        WebTarget target = client().path("with-default");

        Response response = target.queryParam("option", 13).request().get();
        assertThat(response.getStatus(), is(200));
        assertThat(response.readEntity(String.class), is("option: 13"));

        response = target.request().get();
        assertThat(response.getStatus(), is(200));
        assertThat(response.readEntity(String.class), is("option: -1"));
    }



}