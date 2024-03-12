package com.hernan.cusi.lyrics.userservice;

import org.hernan.cussi.lyrics.userservice.LyricsUserServiceApplication;
import org.hernan.cussi.lyrics.userservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(classes = LyricsUserServiceApplication.class)
class LyricsUserServiceApplicationTests {

	@MockBean
	private UserRepository userRepository;

	@Test
	void contextLoads() {
	}

}
