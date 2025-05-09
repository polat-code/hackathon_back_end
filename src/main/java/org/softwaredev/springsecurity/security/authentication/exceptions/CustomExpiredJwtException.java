package org.softwaredev.springsecurity.security.authentication.exceptions;

public class CustomExpiredJwtException extends RuntimeException {
  public CustomExpiredJwtException(String message) {
    super(message);
  }
}
