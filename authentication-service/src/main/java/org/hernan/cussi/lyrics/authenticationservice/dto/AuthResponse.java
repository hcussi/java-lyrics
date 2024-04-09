package org.hernan.cussi.lyrics.authenticationservice.dto;

import lombok.Data;

@Data
public class AuthResponse {

  private final String token;

  public AuthResponse(final String token) {
    this.token = token;
  }
}
