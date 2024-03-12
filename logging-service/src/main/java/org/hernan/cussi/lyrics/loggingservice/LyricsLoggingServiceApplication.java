package org.hernan.cussi.lyrics.loggingservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
	info = @Info(
		title = "Lyrics app API Logging",
		version = "1.0",
		description = "Lyrics app API Logging"
	)
)
public class LyricsLoggingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LyricsLoggingServiceApplication.class, args);
	}

}
