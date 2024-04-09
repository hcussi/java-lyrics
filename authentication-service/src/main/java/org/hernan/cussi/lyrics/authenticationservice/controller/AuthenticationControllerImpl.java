package org.hernan.cussi.lyrics.authenticationservice.controller;

import org.hernan.cussi.lyrics.authenticationservice.business.AuthenticationBusiness;
import org.hernan.cussi.lyrics.authenticationservice.dto.AuthRequest;
import org.hernan.cussi.lyrics.authenticationservice.dto.AuthResponse;
import org.hernan.cussi.lyrics.authenticationservice.exception.InvalidUserCredentialsException;
import org.hernan.cussi.lyrics.utils.aop.HandleRestRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("api/v1")
public class AuthenticationControllerImpl implements AuthenticationController {

  private final AuthenticationBusiness authenticationBusiness;

  @Autowired
  public AuthenticationControllerImpl(AuthenticationBusiness authenticationBusiness) {
    this.authenticationBusiness = authenticationBusiness;
  }

  @PostMapping("/authentication")
  @HandleRestRequest
  @Override
  public EntityModel<AuthResponse> authenticate(@RequestBody @Validated AuthRequest authRequest) {
    try {
      var token = authenticationBusiness.getToken(authRequest);

      AuthResponse response = new AuthResponse(token);

      return EntityModel.of(
        response,
        linkTo(methodOn(AuthenticationControllerImpl.class).authenticate(authRequest)).withSelfRel()
      );
    }
    catch (Exception ex) {
      throw new InvalidUserCredentialsException("Invalid email or password");
    }

  }

}
