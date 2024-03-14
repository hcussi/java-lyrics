package org.hernan.cussi.lyrics.apigatewayservice;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import io.micrometer.observation.ObservationFilter;
import io.micrometer.tracing.exporter.SpanExportingPredicate;
import io.netty.resolver.DefaultAddressResolverGroup;
import org.hernan.cussi.lyrics.utils.telemetry.TelemetryUtils;
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

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder, UriConfiguration uriConfiguration) {
		return builder.routes()
			/* Users API */
			.route(r -> r.path("/api/users")
				// .filters(f -> f.addRequestHeader("Hello", "World"))
				// .filters(f -> f.requestRateLimiter(c -> c.setRateLimiter(redisRateLimiter())))
				.filters(f -> f
					.rewritePath("/api/users", "/api/v1/users")
					.circuitBreaker(c -> c.setName("appCircuitBreaker").setFallbackUri("forward:/fallback"))
				)
				.uri(uriConfiguration.getUserServiceEndpoint()))
			.route(r -> r.path( false, "/api/users/**")
				.filters(f -> f
					.rewritePath("/api/users/(?<suburl>.*)", "/api/v1/users/${suburl}")
					.circuitBreaker(c -> c.setName("appCircuitBreaker").setFallbackUri("forward:/fallback"))
				)
				.uri(uriConfiguration.getUserServiceEndpoint()))
			/* Lyrics API */
			.route(r -> r.path("/api/lyrics")
				.filters(f -> f.circuitBreaker(c -> c.setName("appCircuitBreaker").setFallbackUri("forward:/notImplemented")))
				.uri(uriConfiguration.getLyricsServiceEndpoint()))
			.route(r -> r.path(false, "/api/lyrics/**")
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

	@Bean
	ObservationFilter urlObservationFilter() {
		return TelemetryUtils.urlObservationFilter();
	}

	@Bean
	SpanExportingPredicate actuatorSpanExportingPredicate() {
		return TelemetryUtils.actuatorSpanExportingPredicate();
	}

}
