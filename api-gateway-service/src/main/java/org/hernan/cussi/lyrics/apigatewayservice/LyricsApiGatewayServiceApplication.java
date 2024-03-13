package org.hernan.cussi.lyrics.apigatewayservice;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import io.netty.resolver.DefaultAddressResolverGroup;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@SpringBootApplication
@RestController
@EnableConfigurationProperties(UriConfiguration.class)
public class LyricsApiGatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LyricsApiGatewayServiceApplication.class, args);
	}

	@Bean
	public HttpClient httpClient() {
		return HttpClient.create().resolver(DefaultAddressResolverGroup.INSTANCE);
	}

	@Bean
	public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {
		return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
			.circuitBreakerConfig(
				CircuitBreakerConfig.custom()
					.failureRateThreshold(50)
					.slidingWindowSize(20)
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

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder, UriConfiguration uriConfiguration) {
		return builder.routes()
			.route(r -> r.path("/api/v1/users/**")
				// .filters(f -> f.addRequestHeader("Hello", "World"))
				// .filters(f -> f.requestRateLimiter(c -> c.setRateLimiter(redisRateLimiter())))
				// .filters(f -> f.rewritePath("/user-service/(?<suburl>.*)", "/api/users/${suburl}"))
				.filters(f -> f.circuitBreaker(c -> c.setName("appCircuitBreaker").setFallbackUri("forward:/fallback")))
				.uri(uriConfiguration.getUserServiceEndpoint()))
			.route(r -> r.path("/api/v1/lyrics/**")
				.filters(f -> f.circuitBreaker(c -> c.setName("appCircuitBreaker").setFallbackUri("forward:/notImplemented")))
				.uri(uriConfiguration.getLyricsServiceEndpoint()))
			.build();
	}

	@RequestMapping("/notImplemented")
	public Mono<String> notImplemented() {
		return Mono.just("Not Implemented");
	}

	@RequestMapping("/fallback")
	public Mono<String> fallback() {
		return Mono.just("fallback");
	}

}
