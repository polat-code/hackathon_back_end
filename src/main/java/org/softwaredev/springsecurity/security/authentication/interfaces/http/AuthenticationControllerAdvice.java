package org.softwaredev.springsecurity.security.authentication.interfaces.http;

import java.util.Date;
import org.softwaredev.springsecurity.common.domain.entity.ErrorCodes;
import org.softwaredev.springsecurity.common.domain.http.ApiResponse;
import org.softwaredev.springsecurity.security.authentication.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class AuthenticationControllerAdvice {

  @ExceptionHandler(AlreadyRegisteredUserException.class)
  public ResponseEntity<ApiResponse<String>> alreadyRegisteredUserException(
      AlreadyRegisteredUserException ex, WebRequest webRequest) {
    return new ResponseEntity<>(
        new ApiResponse<>(
            "Error", ex.getMessage(), ErrorCodes.NOT_ACCEPTABLE.getErrorCode(), false, new Date()),
        HttpStatus.OK);
  }

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ApiResponse<String>> userNotFoundException(
      UserNotFoundException ex, WebRequest webRequest) {
    return new ResponseEntity<>(
        new ApiResponse<>(
            "Error", ex.getMessage(), ErrorCodes.NOT_FOUND.getErrorCode(), false, new Date()),
        HttpStatus.OK);
  }

  @ExceptionHandler(ExpiredOTPException.class)
  public ResponseEntity<ApiResponse<String>> expiredOTPException(
      ExpiredOTPException ex, WebRequest webRequest) {
    return new ResponseEntity<>(
        new ApiResponse<>(
            "Error", ex.getMessage(), ErrorCodes.EXPIRED.getErrorCode(), false, new Date()),
        HttpStatus.OK);
  }

  @ExceptionHandler(InvalidOTPException.class)
  public ResponseEntity<ApiResponse<String>> invalidOTPException(
      InvalidOTPException ex, WebRequest webRequest) {
    return new ResponseEntity<>(
        new ApiResponse<>(
            "Error", ex.getMessage(), ErrorCodes.NOT_VALID.getErrorCode(), false, new Date()),
        HttpStatus.OK);
  }

  @ExceptionHandler(EmailNotValidatedException.class)
  public ResponseEntity<ApiResponse<String>> emailNotValidatedException(
      EmailNotValidatedException ex, WebRequest webRequest) {
    return new ResponseEntity<>(
        new ApiResponse<>(
            "Error", ex.getMessage(), ErrorCodes.NOT_VALID.getErrorCode(), false, new Date()),
        HttpStatus.OK);
  }

  @ExceptionHandler(EmailPasswordAuthenticationException.class)
  public ResponseEntity<ApiResponse<String>> emailPasswordAuthenticationException(
      EmailPasswordAuthenticationException ex, WebRequest webRequest) {
    return new ResponseEntity<>(
        new ApiResponse<>(
            "Error", ex.getMessage(), ErrorCodes.NOT_ACCEPTABLE.getErrorCode(), false, new Date()),
        HttpStatus.OK);
  }

  @ExceptionHandler(InvalidAccessTokenException.class)
  public ResponseEntity<ApiResponse<String>> invalidAccessTokenException(
      InvalidAccessTokenException ex, WebRequest request) {
    return new ResponseEntity<>(
        new ApiResponse<>(
            "Error", ex.getMessage(), ErrorCodes.UNAUTHORIZED.getErrorCode(), false, new Date()),
        HttpStatus.OK);
  }

  @ExceptionHandler(CustomExpiredJwtException.class)
  public ResponseEntity<ApiResponse<String>> customExpiredJwtException(
      CustomExpiredJwtException ex, WebRequest request) {
    return new ResponseEntity<>(
        new ApiResponse<>(
            "Error", ex.getMessage(), ErrorCodes.EXPIRED.getErrorCode(), false, new Date()),
        HttpStatus.OK);
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<ApiResponse<String>> authenticationException(
          AuthenticationException ex, WebRequest request) {
    return new ResponseEntity<>(
            new ApiResponse<>(
                    "Error",
                    "Authentication Exception : Username or password is invalid",
                    ErrorCodes.FORBIDDEN.getErrorCode(),
                    false,
                    new Date()),
            HttpStatus.OK);
  }

  @ExceptionHandler(CustomJwtException.class)
  public ResponseEntity<ApiResponse<String>> customJwtException(
      CustomJwtException ex, WebRequest request) {
    return new ResponseEntity<>(
        new ApiResponse<>(
            "Error", ex.getMessage(), ErrorCodes.UNAUTHORIZED.getErrorCode(), false, new Date()),
        HttpStatus.OK);
  }
}
