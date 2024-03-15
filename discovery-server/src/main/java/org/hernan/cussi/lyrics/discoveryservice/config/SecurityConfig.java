package org.hernan.cussi.lyrics.discoveryservice.config;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Profile("!test")
@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      .securityMatchers(matcher -> matcher.requestMatchers(HttpMethod.GET, "/actuator/**"))
      .authorizeHttpRequests(
        registry -> registry
          .requestMatchers(EndpointRequest.to(HealthEndpoint.class)).permitAll()
      )
      .anonymous(AbstractHttpConfigurer::disable)
      .authorizeHttpRequests((authz) -> authz.anyRequest().authenticated())
      .httpBasic(withDefaults())
      .csrf(AbstractHttpConfigurer::disable);
    return http.build();
  }
}
