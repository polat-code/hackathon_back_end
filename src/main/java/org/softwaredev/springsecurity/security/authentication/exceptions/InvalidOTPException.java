package org.softwaredev.springsecurity.security.authentication.exceptions;

public class InvalidOTPException extends RuntimeException {
  public InvalidOTPException(String message) {
    super(message);
  }
}
