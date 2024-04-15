package org.hernan.cussi.lyrics.authenticationservice.listener;

import lombok.extern.slf4j.Slf4j;
import org.hernan.cussi.lyrics.authenticationservice.model.User;
import org.hernan.cussi.lyrics.authenticationservice.repository.UserRepository;
import org.hernan.cussi.lyrics.utils.message.UserInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@Slf4j
public class UserRegisteredListener {

  @Bean
  public Consumer<UserInfo> userCreated(UserRepository userRepository) {
    return userInfo -> {
      log.info("[userCreated] Received Message {}", userInfo);
      var user = User.builder()
        .email(userInfo.email())
        .password(userInfo.password())
        .build();
      userRepository.save(user);
    };
  }

  @Bean
  public Consumer<UserInfo> userModified(UserRepository userRepository) {
    return userInfo -> {
      log.info("[userModified] Received Message {}", userInfo);

      var userFound = userRepository.findByEmail(userInfo.originalEmail());

      if (userFound.isEmpty()) {
        log.info("[userModified] User cannot be found {}", userInfo.originalEmail());
        return;
      }

      var user = userFound.get();
      user.setEmail(userInfo.email());
      user.setPassword(userInfo.password());

      userRepository.save(user);
    };
  }

  @Bean
  public Consumer<UserInfo> userDeleted(UserRepository userRepository) {
    return userInfo -> {
      log.info("[userDeleted] Received Message {}", userInfo);
      var userFound = userRepository.findByEmail(userInfo.email());

      if (userFound.isEmpty()) {
        log.info("[userDeleted] User cannot be found {}", userInfo.email());
        return;
      }

      userRepository.delete(userFound.get());
    };
  }
}
