package demo.classic;

import demo.Fortune;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/fortune")
@RegisterRestClient(configKey = "fortunes-api")
public interface FortunesClassicClient {

  @GET
  Response fetch();

  default Fortune fetchFortune() {
    Response response = fetch();
    var index = Integer.valueOf(response.getHeaderString("Fortune-Index"));
    var text = response.readEntity(String.class);
    return new Fortune(index, text);
  }
}
