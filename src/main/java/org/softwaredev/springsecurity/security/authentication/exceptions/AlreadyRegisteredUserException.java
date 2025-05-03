package org.softwaredev.springsecurity.security.authentication.exceptions;

public class AlreadyRegisteredUserException extends RuntimeException {
  public AlreadyRegisteredUserException(String message) {
    super(message);
  }
}
