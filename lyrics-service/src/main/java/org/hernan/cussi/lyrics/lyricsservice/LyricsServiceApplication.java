package org.hernan.cussi.lyrics.lyricsservice;

import io.micrometer.observation.ObservationFilter;
import io.micrometer.tracing.exporter.SpanExportingPredicate;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.hernan.cussi.lyrics.utils.telemetry.TelemetryUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(
	info = @Info(
		title = "Lyrics app API Lyrics",
		version = "1.0",
		description = "Lyrics app API Lyrics"
	)
)
public class LyricsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LyricsServiceApplication.class, args);
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
