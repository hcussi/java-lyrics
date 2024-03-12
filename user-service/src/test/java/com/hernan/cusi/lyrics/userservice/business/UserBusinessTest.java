package com.hernan.cusi.lyrics.userservice.business;

import com.hernan.cusi.lyrics.userservice.util.PaginationUtil;
import org.hernan.cussi.lyrics.userservice.business.UserBusiness;
import org.hernan.cussi.lyrics.userservice.exception.UserNotFoundException;
import org.hernan.cussi.lyrics.userservice.input.UserInput;
import org.hernan.cussi.lyrics.userservice.model.User;
import org.hernan.cussi.lyrics.userservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserBusinessTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  private UserBusiness userBusiness;

  @Test
  public void createUser_ok() {
    when(userRepository.save(any(User.class))).thenReturn(
      User.builder().name("test").password("{bcrypt}$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG").email("test@gmail.com").build()
    );
    var user = userBusiness.createUser(
      UserInput.builder().name("test").password("12345678").email("test@gmail.com").build()
    );

    assertEquals("test", user.getName());
    assertEquals("test@gmail.com", user.getEmail());
    assertEquals("{bcrypt}$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG", user.getPassword());
  }

  @Test
  public void updateUser_ok() throws UserNotFoundException {
    when(userRepository.findById(anyString())).thenReturn(
      Optional.of(User.builder().name("test").email("test@gmail.com").build())
    );
    when(userRepository.save(any(User.class))).thenReturn(
      User.builder().name("test").email("test@gmail.com").build()
    );
    var user = userBusiness.updateUser(
      "5ca4bbc7a2dd94ee5816238c",
      UserInput.builder().name("test").email("test@gmail.com").build()
    );

    assertEquals("test", user.getName());
    assertEquals("test@gmail.com", user.getEmail());
  }

  @Test
  public void getUser_ok() throws UserNotFoundException {
    when(userRepository.findById(anyString())).thenReturn(
      Optional.of(User.builder().name("test").email("test@gmail.com").build())
    );
    var user = userBusiness.getUser("5ca4bbc7a2dd94ee5816238c");

    assertEquals("test", user.getName());
    assertEquals("test@gmail.com", user.getEmail());
  }

  @Test
  public void deleteUser_ok() throws UserNotFoundException {
    when(userRepository.findById(anyString())).thenReturn(
      Optional.of(User.builder().name("test").email("test@gmail.com").build())
    );
    userBusiness.deleteUser("5ca4bbc7a2dd94ee5816238c");
  }

  @Test
  public void getUsers_ok() throws UserNotFoundException {
    var pageable = PageRequest.of(0, 5, Sort.Direction.ASC, "name");
    when(userRepository.findAll(any(PageRequest.class))).thenReturn(
      PaginationUtil.paginateList(pageable, List.of(
        User.builder().name("test1").email("test1@gmail.com").build(),
        User.builder().name("test2").email("test2@gmail.com").build()
      ))
    );
    var userPage = userBusiness.getUsers(0, 5, "name");

    assertEquals(0, userPage.getNumber());
    assertEquals(5, userPage.getSize());
    assertEquals(Sort.by(Sort.Direction.ASC, "name"), userPage.getSort());
    assertEquals(2, userPage.getTotalElements());
    assertEquals("test1", userPage.getContent().getFirst().getName());
    assertEquals("test2", userPage.getContent().getLast().getName());
  }

}
