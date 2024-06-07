package demo.reactive;

import demo.Fortune;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/reactive")
public class ReactiveAPI {

  private static final Logger logger = LoggerFactory.getLogger(ReactiveAPI.class);

  @RestClient
  FortunesReactiveClient client;

  @GET
  public Uni<Fortune> fetchOne() {
    logger.info("Fetch one fortune (reactive)");
    return client.fetchFortune();
  }

  @GET
  @Path("/stream")
  @Produces(MediaType.SERVER_SENT_EVENTS)
  public Multi<Fortune> stream() {
    logger.info("Starting streaming (reactive)");

    return fetchOne().repeat().atMost(50)
      .onItem().invoke(() -> logger.info("Sending event (reactive)"))
      .onCompletion().invoke(() -> logger.info("Stopped streaming (reactive)"));
  }
}
