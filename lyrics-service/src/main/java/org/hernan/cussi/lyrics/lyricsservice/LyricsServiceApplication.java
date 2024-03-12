package org.hernan.cussi.lyrics.lyricsservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

}
