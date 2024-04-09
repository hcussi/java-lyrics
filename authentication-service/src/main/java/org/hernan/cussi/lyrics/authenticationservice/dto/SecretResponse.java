package org.hernan.cussi.lyrics.authenticationservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SecretResponse {

  private final String secretKey;

  public SecretResponse(final String secretKey) {
    this.secretKey = secretKey;
  }
}
