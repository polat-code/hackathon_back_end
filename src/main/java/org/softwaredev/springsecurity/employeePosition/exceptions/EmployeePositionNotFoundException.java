package org.softwaredev.springsecurity.employeePosition.exceptions;

public class EmployeePositionNotFoundException extends RuntimeException {
  public EmployeePositionNotFoundException(String message) {
    super(message);
  }
}
