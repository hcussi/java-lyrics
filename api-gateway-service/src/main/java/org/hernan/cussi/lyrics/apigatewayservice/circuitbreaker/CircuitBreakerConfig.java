package org.hernan.cussi.lyrics.apigatewayservice.circuitbreaker;

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
          .failureRateThreshold(50) // 50%
          .slidingWindowSize(20)  // last 20 requests
          .minimumNumberOfCalls(10)
          .waitDurationInOpenState(Duration.ofSeconds(10))
          .build()
      ).timeLimiterConfig(
        TimeLimiterConfig.custom()
          .timeoutDuration(Duration.ofSeconds(5))
          .build()
      )
      .build());
  }
}
