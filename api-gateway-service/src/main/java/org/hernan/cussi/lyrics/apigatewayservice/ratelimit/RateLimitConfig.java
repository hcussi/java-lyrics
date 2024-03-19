package org.hernan.cussi.lyrics.apigatewayservice.ratelimit;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "ratelimit")
@Getter
@Setter
public class RateLimitConfig {

  @Value("${defaultReplenishRate:100}")
  private int defaultReplenishRate;

  @Value("${defaultBurstCapacity:100}")
  private int defaultBurstCapacity;

  @Value("${defaultRequestedTokens:1}")
  private int defaultRequestedTokens;

}
