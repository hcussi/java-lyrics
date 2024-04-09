package org.hernan.cussi.lyrics.authenticationservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hernan.cussi.lyrics.utils.mongo.model.AbstractModelObject;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Document(collection = "users")
@Data
@NoArgsConstructor
public class User extends AbstractModelObject implements Serializable {

  @Serial
  private static final long serialVersionUID = 3682951088264720920L;

  private String name;

  @JsonIgnore
  private String password;

  private String email;

  @Builder
  public User(final String id, final Integer version, final String name, final String password, final String email) {
    super(id, version, null, null);
    this.name = name;
    this.password = password;
    this.email = email;
  }
}
