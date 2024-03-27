package org.hernan.cussi.lyrics.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.hernan.cussi.lyrics.userservice.model.User;

@Data
@Builder
public class UserDto {

  @NotBlank(groups = {Save.class, Update.class})
  @Size(min = 2, max = 255, groups = {Save.class, Update.class})
  private String name;

  @NotBlank(groups = {Save.class})
  @Size(min = 8, max = 20, groups = {Save.class})
  private String password;

  @NotBlank(groups = {Save.class, Update.class})
  @Size(min = 2, max = 255, groups = {Save.class, Update.class})
  @Email(regexp = ".+@.+\\..+", groups = {Save.class, Update.class})
  private String email;

  public User build(final String encodedPassword) {
    return User.builder().name(name).password(encodedPassword).email(email).build();
  }

  /**
   * when save validate group
   */
  public interface Save {
  }

  /**
   * when update validate group
   */
  public interface Update {
  }
}
