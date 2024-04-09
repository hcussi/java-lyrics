package org.hernan.cussi.lyrics.authenticationservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Invalid email or password")
public class InvalidUserCredentialsException extends RuntimeException {
    public InvalidUserCredentialsException() {
        super();
    }
    public InvalidUserCredentialsException(String message, Throwable cause) {
        super(message, cause);
    }
    public InvalidUserCredentialsException(String message) {
        super(message);
    }
    public InvalidUserCredentialsException(Throwable cause) {
        super(cause);
    }
}
