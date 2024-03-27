package org.hernan.cussi.lyrics.clientservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
public class AppConfig {

  @Value("${client.base-url}")
  private String baseUrl;
}
