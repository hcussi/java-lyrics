package org.hernan.cussi.lyrics.authenticationservice.config;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http
      .authorizeHttpRequests(registry ->
        registry
          .requestMatchers(EndpointRequest.to(HealthEndpoint.class)).permitAll()
          .requestMatchers("/actuator/**").permitAll()
          .requestMatchers("/api/v1/**").hasAuthority("SCOPE_read:users").anyRequest().authenticated()
      )
      .anonymous(AbstractHttpConfigurer::disable)
      .csrf(AbstractHttpConfigurer::disable)
      .oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer.jwt(Customizer.withDefaults()))
      // this disables session creation on Spring Security
      .sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .build();
  }

}
