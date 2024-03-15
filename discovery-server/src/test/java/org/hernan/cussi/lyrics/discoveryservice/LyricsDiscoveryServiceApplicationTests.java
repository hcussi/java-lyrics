package org.hernan.cussi.lyrics.discoveryservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = { LyricsDiscoveryServiceApplication.class } )
@EnableAutoConfiguration(exclude= {
	ReactiveUserDetailsServiceAutoConfiguration.class,
	UserDetailsServiceAutoConfiguration.class,
})
@ActiveProfiles(profiles = "test")
class LyricsDiscoveryServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}
