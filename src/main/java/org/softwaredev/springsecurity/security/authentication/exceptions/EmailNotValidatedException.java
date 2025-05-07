package org.softwaredev.springsecurity.security.authentication.exceptions;

public class EmailNotValidatedException extends RuntimeException {
  public EmailNotValidatedException(String message) {
    super(message);
  }
}
