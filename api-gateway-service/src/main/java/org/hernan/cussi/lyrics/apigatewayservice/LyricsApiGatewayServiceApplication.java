package org.hernan.cussi.lyrics.apigatewayservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
	info = @Info(
		title = "Lyrics app API Gateway",
		version = "1.0",
		description = "Lyrics app API Gateway"
	)
)
public class LyricsApiGatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LyricsApiGatewayServiceApplication.class, args);
	}

}
