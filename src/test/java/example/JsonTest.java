package example;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.google.common.collect.Lists;
import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.hamcrest.core.Is;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class JsonTest extends AbstractApplicationTest {

    @Override
    public String base() {
        return "json";
    }

    @Test
    public void testPostWithSingleJsonEntity() {
        Response response = client().path("doubler").request().post(Entity.json(new Store(17, "AbcD")));

        assertThat(response.getStatus(), is(200));
        Store entity = response.readEntity(Store.class);
        assertThat(entity.ordinal, is(34));
        assertThat(entity.value, is("AbcDAbcD"));
    }

    @Test
    public void testPostWithJsonEntityList() {
        Response response = client().path("sorter").request().post(Entity.json(
                Lists.newArrayList(new Store(1, "D"), new Store(2, "A"), new Store(3, "C"))));

        assertThat(response.getStatus(), is(200));

        Store[] entity = response.readEntity(Store[].class);
        assertThat(entity, is(new Store[]{new Store(2, "A"), new Store(3, "C"), new Store(1, "D")}));
    }

    public static class Store {
        public Integer ordinal;
        public String value;

        public Store(@JsonProperty("ordinal") Integer ordinal, @JsonProperty("value") String value) {
            this.ordinal = ordinal;
            this.value = value;
        }

        @Override
        public String toString() {
            return "Store{" +
                    "ordinal=" + ordinal +
                    ", value='" + value + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Store store = (Store) o;

            if (ordinal != null ? !ordinal.equals(store.ordinal) : store.ordinal != null) return false;
            if (value != null ? !value.equals(store.value) : store.value != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = ordinal != null ? ordinal.hashCode() : 0;
            result = 31 * result + (value != null ? value.hashCode() : 0);
            return result;
        }
    }
}