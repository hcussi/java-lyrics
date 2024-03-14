package org.hernan.cussi.lyrics.discoveryservice;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@SpringBootApplication
@EnableEurekaServer
public class LyricsDiscoveryServiceApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(LyricsDiscoveryServiceApplication.class).web(WebApplicationType.SERVLET).run(args);
	}

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
