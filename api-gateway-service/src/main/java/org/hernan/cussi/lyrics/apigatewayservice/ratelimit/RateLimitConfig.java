package org.hernan.cussi.lyrics.apigatewayservice.ratelimit;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
public class RateLimitConfig {

  @Value("${ratelimit.default-replenish-rate}")
  private int defaultReplenishRate;

  @Value("${ratelimit.default-burst-capacity}")
  private int defaultBurstCapacity;

  @Value("${ratelimit.default-requested-tokens}")
  private int defaultRequestedTokens;

}
