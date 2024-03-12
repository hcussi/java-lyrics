package org.hernan.cussi.lyrics.userservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;

@SpringBootApplication
@EnableAspectJAutoProxy
@OpenAPIDefinition(
	info = @Info(
		title = "Lyrics API Users",
		version = "1.0",
		description = "Lyrics API Users"
	)
)
public class LyricsUserServiceApplication {

	private static final int PASS_STRENGTH = 10;

	public static void main(String[] args) {
		SpringApplication.run(LyricsUserServiceApplication.class, args);
	}

	/**
   * <a href="https://reflectoring.io/spring-security-password-handling/">Handling Passwords with Spring Boot and Spring Security</a>
   * @return password encoder bean
   */
	@Bean
	public PasswordEncoder delegatingPasswordEncoder() {
		return new BCryptPasswordEncoder(PASS_STRENGTH, new SecureRandom());
	}

}
