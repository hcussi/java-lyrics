package org.hernan.cussi.lyrics.authenticationservice;

import io.micrometer.observation.ObservationFilter;
import io.micrometer.tracing.exporter.SpanExportingPredicate;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.hernan.cussi.lyrics.utils.telemetry.TelemetryUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@SpringBootApplication
@OpenAPIDefinition(
	info = @Info(
		title = "Lyrics app API Authentication",
		version = "1.0",
		description = "Lyrics app API Authentication"
	)
)
public class LyricsAuthenticationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LyricsAuthenticationServiceApplication.class, args);
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.securityMatchers(matcher -> matcher.requestMatchers(HttpMethod.GET, "/actuator/**"))
			.authorizeHttpRequests(
				registry -> registry
					.requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
			)
			.anonymous(AbstractHttpConfigurer::disable)
			.csrf(AbstractHttpConfigurer::disable);
		return http.build();
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
