package org.hernan.cussi.lyrics.apigatewayservice;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties
public class UriConfiguration {

  private String userServiceEndpoint = "http://localhost:8081";
  private String lyricsServiceEndpoint = "http://localhost:8083";
  private String authenticationServiceEndpoint = "http://localhost:8085";

}
