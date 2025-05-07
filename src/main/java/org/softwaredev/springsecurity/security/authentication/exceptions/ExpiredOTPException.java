package org.softwaredev.springsecurity.security.authentication.exceptions;

public class ExpiredOTPException extends RuntimeException {
  public ExpiredOTPException(String message) {
    super(message);
  }
}
