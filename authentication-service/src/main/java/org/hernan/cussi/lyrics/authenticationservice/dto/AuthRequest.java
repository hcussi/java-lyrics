package org.hernan.cussi.lyrics.authenticationservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AuthRequest {

  @NotBlank
  @Size(min = 2, max = 255)
  @Email(regexp = ".+@.+\\..+")
  private String email;

  @NotBlank
  @Size(min = 8, max = 20)
  private String password;

}
