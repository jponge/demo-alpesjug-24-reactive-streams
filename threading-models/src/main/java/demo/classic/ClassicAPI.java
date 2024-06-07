package demo.classic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import demo.Fortune;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.sse.Sse;
import jakarta.ws.rs.sse.SseBroadcaster;
import jakarta.ws.rs.sse.SseEventSink;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/classic")
public class ClassicAPI {

  private static final Logger logger = LoggerFactory.getLogger(ClassicAPI.class);

  @RestClient
  FortunesClassicClient client;

  @GET
  public Fortune fetchOne() {
    logger.info("Fetch one fortune (classic)");
    return client.fetchFortune();
  }

  @GET
  @Path("/stream")
  @Produces(MediaType.SERVER_SENT_EVENTS)
  public void stream(Sse sse, SseEventSink sink) throws JsonProcessingException {
    logger.info("Starting streaming (classic)");

    SseBroadcaster broadcaster = sse.newBroadcaster();
    broadcaster.register(sink);

    ObjectMapper jsonMapper = new ObjectMapper();
    for (int i = 0; i < 50; i++) {
      logger.info("Sending event (classic)");
      var fortune = client.fetchFortune();
      var json = jsonMapper.writeValueAsString(fortune);
      broadcaster.broadcast(sse.newEvent(json));
    }

    broadcaster.close();
    logger.info("Stopped streaming (classic)");
  }
}
