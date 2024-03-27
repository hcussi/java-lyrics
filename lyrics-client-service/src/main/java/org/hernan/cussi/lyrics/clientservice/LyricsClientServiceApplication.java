package org.hernan.cussi.lyrics.clientservice;

import io.micrometer.observation.ObservationFilter;
import io.micrometer.tracing.exporter.SpanExportingPredicate;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.hernan.cussi.lyrics.utils.telemetry.TelemetryUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
@OpenAPIDefinition(
	info = @Info(
		title = "Lyrics API Client",
		version = "1.0",
		description = "Lyrics API Client"
	)
)
public class LyricsClientServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LyricsClientServiceApplication.class, args);
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
