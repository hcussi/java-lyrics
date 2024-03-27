package org.hernan.cussi.lyrics.userservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.hernan.cussi.lyrics.userservice.exception.UserNotFoundException;
import org.hernan.cussi.lyrics.userservice.dto.UserDto;
import org.hernan.cussi.lyrics.userservice.model.User;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;

@Tag(name = "User", description = "Users endpoint for CRUD operations")
public interface UserController {

  @Operation(
    summary = "Create endpoint",
    description = "Returns a new user created")
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "200",
      description = "successful operation",
      content = { @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }
    ),
  })
  User createUser(UserDto user);

  @Operation(
    summary = "Get endpoint",
    description = "Returns a user")
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "200",
      description = "successful operation",
      content = { @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }
    ),
    @ApiResponse(
      responseCode = "404",
      description = "user not found"
    )
  })
  EntityModel<User> getUser(String userId) throws UserNotFoundException;

  @Operation(
    summary = "Get endpoint",
    description = "Returns a list of users")
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "200",
      description = "successful operation",
      content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = User.class))) }
    )
  })
  Page<User> getUsers(Integer pageNumber, Integer pageSize, String sort);

  @Operation(
    summary = "Update endpoint",
    description = "Returns user updated")
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "200",
      description = "successful operation",
      content = { @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }
    ),
    @ApiResponse(
      responseCode = "404",
      description = "user not found"
    )
  })
  User updateUser(UserDto userDto, String userId) throws UserNotFoundException;

  @Operation(
    summary = "Delete endpoint",
    description = "Deletes a user")
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "200",
      description = "successful operation"
    ),
    @ApiResponse(
      responseCode = "404",
      description = "user not found"
    )
  })
  void deleteUser(String userId) throws UserNotFoundException;
}
