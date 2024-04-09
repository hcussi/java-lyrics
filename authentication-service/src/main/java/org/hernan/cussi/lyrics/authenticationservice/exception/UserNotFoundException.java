package org.hernan.cussi.lyrics.authenticationservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "User cannot be found with email passed")
public class UserNotFoundException extends Exception {
    public UserNotFoundException() {
        super();
    }
    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    public UserNotFoundException(String message) {
        super(message);
    }
    public UserNotFoundException(Throwable cause) {
        super(cause);
    }
}
