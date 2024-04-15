package org.hernan.cussi.lyrics.authenticationservice.listener;

import lombok.extern.slf4j.Slf4j;
import org.hernan.cussi.lyrics.authenticationservice.model.User;
import org.hernan.cussi.lyrics.authenticationservice.repository.UserRepository;
import org.hernan.cussi.lyrics.utils.message.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@Slf4j
public class UserRegisteredListener {

  private final UserRepository userRepository;

  @Autowired
  public UserRegisteredListener(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Bean
  public Consumer<UserInfo> userCreated() {
    return userInfo -> {
      log.info("Received Message {}", userInfo);
      var user = User.builder()
        .email(userInfo.email())
        .password(userInfo.password())
        .build();
      userRepository.save(user);
    };
  }
}
