package org.softwaredev.springsecurity.security.authentication.exceptions;

public class InvalidAccessTokenException extends RuntimeException {
  public InvalidAccessTokenException(String message) {
    super(message);
  }
}
