package example;

import org.junit.Test;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CollectionTest extends AbstractApplicationTest {

    @Override
    public String base() {
        return "collection";
    }

    @Test
    public void testWithQueryParam() {
        WebTarget target = client().path("sorter");

        Response response = target.queryParam("list", "d", "q", "a", "c").request().get();
        assertThat(response.getStatus(), is(200));
        assertThat(response.readEntity(String.class), is("a, c, d, q"));
    }



}