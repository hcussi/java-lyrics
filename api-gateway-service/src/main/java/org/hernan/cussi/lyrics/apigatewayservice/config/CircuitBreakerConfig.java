package org.hernan.cussi.lyrics.apigatewayservice.config;

import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class CircuitBreakerConfig {

  @Bean
  public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {
    return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
      .circuitBreakerConfig(
        io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.custom()
          .failureRateThreshold(80) // 80%
          .slidingWindowSize(10)  // last 20 requests
          .minimumNumberOfCalls(3)
          .waitDurationInOpenState(Duration.ofSeconds(5))
          .build()
      ).timeLimiterConfig(
        TimeLimiterConfig.custom()
          .timeoutDuration(Duration.ofSeconds(10))
          .build()
      )
      .build());
  }
}
