package org.hernan.cussi.lyrics.authenticationservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.hernan.cussi.lyrics.authenticationservice.dto.AuthRequest;
import org.hernan.cussi.lyrics.authenticationservice.dto.AuthResponse;
import org.springframework.hateoas.EntityModel;

@Tag(name = "Authentication", description = "Authentication endpoint to obtain access to the API")
public interface AuthenticationController {

  @Operation(
    summary = "Generate JWT token",
    description = "Generate JWT token using user credentials")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "successful operation")
  })
  EntityModel<AuthResponse> authenticate(AuthRequest authRequest);

}
