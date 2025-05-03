package org.softwaredev.springsecurity.security.authentication.interfaces.http;

import org.softwaredev.springsecurity.common.domain.entity.ErrorCodes;
import org.softwaredev.springsecurity.common.domain.http.ApiResponse;
import org.softwaredev.springsecurity.security.authentication.exceptions.AlreadyRegisteredUserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class AuthenticationControllerAdvice {

  @ExceptionHandler(AlreadyRegisteredUserException.class)
  public ResponseEntity<ApiResponse<String>> companyNotFoundException(
      AlreadyRegisteredUserException ex, WebRequest webRequest) {
    return new ResponseEntity<>(
        new ApiResponse<>(
            "Error", ex.getMessage(), ErrorCodes.NOT_VALID.getErrorCode(), false, new Date()),
        HttpStatus.OK);
  }
}
