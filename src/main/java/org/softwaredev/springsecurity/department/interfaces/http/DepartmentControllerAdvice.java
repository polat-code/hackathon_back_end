package org.softwaredev.springsecurity.department.interfaces.http;

import java.util.Date;
import org.softwaredev.springsecurity.common.domain.entity.ErrorCodes;
import org.softwaredev.springsecurity.common.domain.http.ApiResponse;
import org.softwaredev.springsecurity.department.exceptions.DepartmentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class DepartmentControllerAdvice {

  @ExceptionHandler(DepartmentNotFoundException.class)
  public ResponseEntity<ApiResponse<String>> departmentNotFoundException(
      DepartmentNotFoundException ex, WebRequest request) {
    return new ResponseEntity<>(
        new ApiResponse<>(
            "Error", ex.getMessage(), ErrorCodes.NOT_FOUND.getErrorCode(), false, new Date()),
        HttpStatus.OK);
  }
}
