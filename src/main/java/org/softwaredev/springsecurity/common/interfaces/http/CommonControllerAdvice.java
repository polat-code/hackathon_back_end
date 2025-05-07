package org.softwaredev.springsecurity.common.interfaces.http;

import java.util.Date;
import org.softwaredev.springsecurity.common.domain.entity.ErrorCodes;
import org.softwaredev.springsecurity.common.domain.http.ApiResponse;
import org.softwaredev.springsecurity.common.exceptions.InvalidDateFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class CommonControllerAdvice {

  @ExceptionHandler(InvalidDateFormatException.class)
  public ResponseEntity<ApiResponse<String>> invalidDateFormatException(
      InvalidDateFormatException ex, WebRequest request) {
    return new ResponseEntity<>(
        new ApiResponse<>(
            "Error", "Invalid Date Format", ErrorCodes.FORBIDDEN.getErrorCode(), false, new Date()),
        HttpStatus.OK);
  }
}
