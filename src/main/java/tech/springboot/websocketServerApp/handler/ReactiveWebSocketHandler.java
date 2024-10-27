package tech.springboot.websocketServerApp.handler;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.springboot.websocketServerApp.model.ServerMessage;

@Component ("webSocketHandler")
@Log4j2
public class ReactiveWebSocketHandler implements WebSocketHandler {

  private static final ObjectMapper objectMapper = new ObjectMapper ();

  private final Flux<String> eventFlux = Flux.generate (sink -> {
    ServerMessage serverMessage = ServerMessage.builder ()
        .sendTime (new Date ())
        .messageId (UUID.randomUUID ().toString ())
        .messageContext ("test")
        .build ();
    try {
      sink.next (objectMapper.writeValueAsString (serverMessage));
    }
    catch (JsonProcessingException e) {
      throw new RuntimeException (e);
    }
  });

  private final Flux<String> intervalFlux = Flux.interval (Duration.ofMillis (1000L))
      .zipWith (eventFlux, (time, event) -> event);

  @Override
  public List<String> getSubProtocols () {
    log.info ("getSubProtocols");
    return List.of ("test");
  }

  @Override
  public Mono<Void> handle (WebSocketSession webSocketSession) {

    String protocol = webSocketSession.getHandshakeInfo ().getSubProtocol ();
    log.info ("protocol...{}", protocol);
    return webSocketSession.send (intervalFlux
                                      .map (webSocketSession::textMessage))
        .and (webSocketSession.receive ()
                  .map (WebSocketMessage::getPayloadAsText).log ());
  }
}

