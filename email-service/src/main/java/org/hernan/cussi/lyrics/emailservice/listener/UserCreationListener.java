package org.hernan.cussi.lyrics.emailservice.listener;

import lombok.extern.slf4j.Slf4j;
import org.hernan.cussi.lyrics.utils.message.UserInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@Slf4j
public class UserCreationListener {

  @Bean
  public Consumer<UserInfo> userCreated() {
    return userInfo -> log.info("Received Message {}", userInfo);
  }
}
