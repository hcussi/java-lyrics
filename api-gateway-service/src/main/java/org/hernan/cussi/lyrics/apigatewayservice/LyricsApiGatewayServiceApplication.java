package org.hernan.cussi.lyrics.apigatewayservice;

import io.micrometer.observation.ObservationFilter;
import io.micrometer.tracing.exporter.SpanExportingPredicate;
import io.netty.resolver.DefaultAddressResolverGroup;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import org.hernan.cussi.lyrics.apigatewayservice.ratelimit.RateLimitConfig;
import org.hernan.cussi.lyrics.apigatewayservice.ratelimit.SimpleClientAddressResolver;
import org.hernan.cussi.lyrics.utils.telemetry.TelemetryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@OpenAPIDefinition(
	info = @Info(
		title = "API Gateway",
		version = "1.0",
		description = "Documentation API Gateway v1.0"
	)
)
@SpringBootApplication
@RestController
@EnableConfigurationProperties(UriConfiguration.class)
public class LyricsApiGatewayServiceApplication {

	@Autowired
	private RateLimitConfig rateLimitConfig;

	public static void main(String[] args) {
		SpringApplication.run(LyricsApiGatewayServiceApplication.class, args);
	}

	@Bean
	public HttpClient httpClient() {
		return HttpClient.create().resolver(DefaultAddressResolverGroup.INSTANCE);
	}

	@Bean
	public RedisRateLimiter redisRateLimiter() {
		return new RedisRateLimiter(
			rateLimitConfig.getDefaultReplenishRate(), // how many requests per second do you want a user to be allowed to do
			rateLimitConfig.getDefaultBurstCapacity(), // the maximum number of requests a user is allowed to do in a single second
			rateLimitConfig.getDefaultRequestedTokens() // How many token we need for each request
		);
	}

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder, UriConfiguration uriConfiguration) {
		return builder.routes()
			/* Users API */
			.route(r -> r.path("/api/users")
				.filters(f -> f
					.rewritePath("/api/users", "/api/v1/users")
					.circuitBreaker(c -> c.setName("user-service-circuit-breaker").setFallbackUri("forward:/fallback"))
					.retry(config -> config.setRetries(3).setMethods(HttpMethod.GET))
					.requestRateLimiter(c -> c.setRateLimiter(redisRateLimiter()))
				)
				.uri(uriConfiguration.getUserServiceEndpoint()))
			.route(r -> r.path( false, "/api/users/**")
				.filters(f -> f
					.rewritePath("/api/users/(?<suburl>.*)", "/api/v1/users/${suburl}")
					.circuitBreaker(c -> c.setName("user-service-circuit-breaker").setFallbackUri("forward:/fallback"))
					.retry(config -> config.setRetries(3).setMethods(HttpMethod.GET))
					.requestRateLimiter(c -> c.setRateLimiter(redisRateLimiter()))
				)
				.uri(uriConfiguration.getUserServiceEndpoint()))
			.route(r -> r.path("/user-service/v3/api-docs").and().method(HttpMethod.GET)
				.uri(uriConfiguration.getUserServiceEndpoint()))
			/* Lyrics API */
			.route(r -> r.path("/api/lyrics")
				.filters(f -> f
					.circuitBreaker(c -> c.setName("lyrics-service-circuit-breaker").setFallbackUri("forward:/notImplemented"))
					.retry(config -> config.setRetries(3).setMethods(HttpMethod.GET))
					.requestRateLimiter(c -> c.setRateLimiter(redisRateLimiter()).setDenyEmptyKey(false).setKeyResolver(new SimpleClientAddressResolver()))
				)
				.uri(uriConfiguration.getLyricsServiceEndpoint()))
			.route(r -> r.path(false, "/api/lyrics/**")
				.filters(f -> f
					.circuitBreaker(c -> c.setName("lyrics-service-circuit-breaker").setFallbackUri("forward:/notImplemented"))
					.retry(config -> config.setRetries(3).setMethods(HttpMethod.GET))
					.requestRateLimiter(c -> c.setRateLimiter(redisRateLimiter()).setDenyEmptyKey(false).setKeyResolver(new SimpleClientAddressResolver()))
				)
				.uri(uriConfiguration.getLyricsServiceEndpoint()))
			.route(r -> r.path("/lyrics-service/v3/api-docs").and().method(HttpMethod.GET)
				.filters(f -> f.requestRateLimiter(c -> c.setRateLimiter(redisRateLimiter()).setDenyEmptyKey(false).setKeyResolver(new SimpleClientAddressResolver())))
				.uri(uriConfiguration.getLyricsServiceEndpoint()))
			/* Authentication API */
			.route(r -> r.path("/authentication-service/v3/api-docs").and().method(HttpMethod.GET)
				.filters(f -> f.requestRateLimiter(c -> c.setRateLimiter(redisRateLimiter()).setDenyEmptyKey(false).setKeyResolver(new SimpleClientAddressResolver())))
				.uri(uriConfiguration.getAuthenticationServiceEndpoint()))
			.build();
	}

	@Operation(hidden = true)
	@RequestMapping("/notImplemented")
	public Mono<String> notImplemented() {
		return Mono.just("Not Implemented");
	}

	@Operation(hidden = true)
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
