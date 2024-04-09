package org.hernan.cussi.lyrics.authenticationservice.config;

import lombok.Data;
import org.hernan.cussi.lyrics.utils.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;

@Configuration
@Data
public class ApplicationConfig {

  private static final int PASS_STRENGTH = 10;

  @Value("${authentication.api-secret-key}")
  private String apiSecretKey;

  @Bean
  public JwtUtil jwtUtil() {
    return new JwtUtil(apiSecretKey);
  }

  @Bean
  public PasswordEncoder delegatingPasswordEncoder() {
    return new BCryptPasswordEncoder(PASS_STRENGTH, new SecureRandom());
  }

}
