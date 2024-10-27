package tech.springboot.websocketServerApp.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.WebSocketService;
import org.springframework.web.reactive.socket.server.support.HandshakeWebSocketService;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import org.springframework.web.reactive.socket.server.upgrade.TomcatRequestUpgradeStrategy;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Configuration
@EnableWebSocket
public class WebSocketServerConfig {

  @Qualifier ("webSocketHandler")
  @Autowired
  private WebSocketHandler webSocketHandler;

  @Bean
  public SimpleUrlHandlerMapping handlerMapping () {
    log.info ("Mapping WebSocket endpoint at /text-message");

    Map<String, WebSocketHandler> map = new HashMap<> ();
    map.put ("/ws/messages", webSocketHandler);

    return new SimpleUrlHandlerMapping (map, -1);
  }


  @Bean
  public WebSocketHandlerAdapter handlerAdapter () {
    log.info ("handlerAdapter");
    return new WebSocketHandlerAdapter (webSocketService ());
  }

  @Bean
  public WebSocketService webSocketService () {
    log.info ("webSocketService");
    TomcatRequestUpgradeStrategy strategy = new TomcatRequestUpgradeStrategy ();
    strategy.setMaxSessionIdleTimeout (0L);
    return new HandshakeWebSocketService (strategy);
  }
}
