package org.softwaredev.springsecurity.security.authentication.exceptions;

public class EmailPasswordAuthenticationException extends RuntimeException {
  public EmailPasswordAuthenticationException(String message) {
    super(message);
  }
}
