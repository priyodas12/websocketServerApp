package tech.springboot.websocketServerApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@EnableWebSocket
@SpringBootApplication
public class WebsocketServerAppApplication {

  public static void main (String[] args) {
    SpringApplication.run (WebsocketServerAppApplication.class, args);
  }

}
