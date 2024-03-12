package org.hernan.cussi.lyrics.userservice.controller.advice;

import org.hernan.cussi.lyrics.userservice.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class UserNotFoundAdvice {

  @ResponseBody
  @ExceptionHandler(UserNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String userNotFoundHandler(UserNotFoundException ex) {
    return ex.getMessage();
  }
}
