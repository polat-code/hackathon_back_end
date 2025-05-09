package org.softwaredev.springsecurity.security.authentication.exceptions;

public class CustomJwtException extends RuntimeException {
  public CustomJwtException(String message) {
    super(message);
  }
}
