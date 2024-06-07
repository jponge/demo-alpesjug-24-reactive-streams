package demo.reactive;

import demo.Fortune;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/fortune")
@RegisterRestClient(configKey = "fortunes-api")
public interface FortunesReactiveClient {

  @GET
  Uni<Response> fetch();

  default Uni<Fortune> fetchFortune() {
    return fetch().onItem().transform(response -> {
      var index = Integer.valueOf(response.getHeaderString("Fortune-Index"));
      var text = response.readEntity(String.class);
      return new Fortune(index, text);
    });
  }
}
