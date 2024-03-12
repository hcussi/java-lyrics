package org.hernan.cussi.lyrics.authenticationservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

}
