package org.hernan.cussi.lyrics.userservice.business;

import lombok.extern.slf4j.Slf4j;
import org.hernan.cussi.lyrics.userservice.dto.UserDto;
import org.hernan.cussi.lyrics.userservice.exception.UserNotFoundException;
import org.hernan.cussi.lyrics.userservice.model.User;
import org.hernan.cussi.lyrics.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserBusiness {

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  private final NotificationBusiness notificationBusiness;

  @Autowired
  public UserBusiness(UserRepository userRepository, PasswordEncoder passwordEncoder, NotificationBusiness notificationBusiness) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.notificationBusiness = notificationBusiness;
  }

  public User createUser(final UserDto userDto) {
    var user = userDto.build(passwordEncoder.encode(userDto.getPassword()));
    log.info("Creating new user {}", user);

    this.notificationBusiness.notifyUserCreation(user);

    return this.userRepository.save(user);
  }

  public User updateUser(String userId, final UserDto userDto) throws UserNotFoundException {
    var user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    user.setName(userDto.getName());
    user.setEmail(userDto.getEmail());
    log.info("Updating new user {}", user);
    return this.userRepository.save(user);
  }

  public void deleteUser(final String userId) throws UserNotFoundException {
    var user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    log.info("Deleting user {}", user);
    this.userRepository.delete(user);
  }

  public User getUser(final String userId) throws UserNotFoundException {
    log.info("Getting user {}", userId);
    return this.userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
  }

  public Page<User> getUsers(final Integer pageNumber, final Integer pageSize, final String sort) {
    var pageNumberValue = 0;
    var pageSizeValue = 10;
    if(pageNumber != null) {
      pageNumberValue = pageNumber;
    }
    if(pageSize != null) {
      pageSizeValue = pageSize;
    }
    var pageable = PageRequest.of(pageNumberValue, pageSizeValue);

    if (sort != null) {
      // with sorting
      pageable = PageRequest.of(pageNumberValue, pageSizeValue, Sort.Direction.ASC, sort);
    }
    log.info("Getting users {}", pageable);
    return userRepository.findAll(pageable);
  }
}
