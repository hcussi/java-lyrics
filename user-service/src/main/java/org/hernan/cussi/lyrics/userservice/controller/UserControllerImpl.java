package org.hernan.cussi.lyrics.userservice.controller;

import org.hernan.cussi.lyrics.userservice.business.UserBusiness;
import org.hernan.cussi.lyrics.userservice.dto.UserDto;
import org.hernan.cussi.lyrics.userservice.exception.UserNotFoundException;
import org.hernan.cussi.lyrics.userservice.model.User;
import org.hernan.cussi.lyrics.utils.aop.HandleRestRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/users")
public class UserControllerImpl implements UserController {

  private final UserBusiness userBusiness;

  @Autowired
  public UserControllerImpl(UserBusiness userBusiness) {
    this.userBusiness = userBusiness;
  }


  @PostMapping(
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  @HandleRestRequest
  public User createUser(@RequestBody @Validated(UserDto.Save.class) UserDto user) {
    return userBusiness.createUser(user);
  }

  @GetMapping(
    value = "/{id}",
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  @HandleRestRequest
  public EntityModel<User> getUser(@PathVariable("id") String userId) throws UserNotFoundException {
    var user = userBusiness.getUser(userId);
    return EntityModel.of(user,
      linkTo(methodOn(UserControllerImpl.class).getUser(userId)).withSelfRel());
      //linkTo(methodOn(UserControllerImpl.class).getUsers()).withRel("users"));
  }

  @GetMapping(
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  @HandleRestRequest
  public Page<User> getUsers(
    @RequestParam(value = "page", required = false) final Integer pageNumber,
    @RequestParam(value = "size", required = false) final Integer pageSize,
    @RequestParam(value = "sort", required = false) final String sort
  ) {
    return userBusiness.getUsers(pageNumber, pageSize, sort);
  }

  @PutMapping(
    value = "/{id}",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  @HandleRestRequest
  public User updateUser(@RequestBody @Validated(UserDto.Update.class) UserDto userDto, @PathVariable("id") String userId) throws UserNotFoundException {
    return userBusiness.updateUser(userId, userDto);
  }

  @DeleteMapping(
    value = "/{id}"
  )
  @HandleRestRequest
  public void deleteUser(@PathVariable("id") String userId) throws UserNotFoundException {
    userBusiness.deleteUser(userId);
  }

}
