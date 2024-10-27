package tech.springboot.websocketServerApp.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ServerMessage {

  private String messageId;
  private String messageContext;
  private Date sendTime;

}
