package org.hernan.cussi.lyrics.apigatewayservice;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties
public class UriConfiguration {

  private String userServiceEndpoint = "lb://user-service";
  private String lyricsServiceEndpoint = "lb://lyrics-service";
  private String authenticationServiceEndpoint = "lb://authentication-service";

}
