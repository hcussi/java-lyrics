package org.hernan.cussi.lyrics.userservice.controller.advice;

import lombok.extern.slf4j.Slf4j;
import org.hernan.cussi.lyrics.userservice.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class UserNotFoundAdvice {

  @ResponseBody
  @ExceptionHandler(UserNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String userNotFoundHandler(UserNotFoundException ex) {
    log.error("User not found", ex);
    return ex.getMessage();
  }
}
