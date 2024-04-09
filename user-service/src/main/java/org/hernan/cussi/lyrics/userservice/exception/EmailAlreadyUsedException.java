package org.hernan.cussi.lyrics.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Email already used")
public class EmailAlreadyUsedException extends RuntimeException {

  public EmailAlreadyUsedException() {
    super();
  }
  public EmailAlreadyUsedException(String message, Throwable cause) {
    super(message, cause);
  }
  public EmailAlreadyUsedException(String message) {
    super(message);
  }
  public EmailAlreadyUsedException(Throwable cause) {
    super(cause);
  }

}
