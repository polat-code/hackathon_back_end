package org.softwaredev.springsecurity.common.application;

import org.softwaredev.springsecurity.common.domain.entity.Email;
import org.softwaredev.springsecurity.user.domain.entity.User;
import org.springframework.stereotype.Service;

@Service
public class EmailHelperService {
  public Email getRegister(User user, String otp) {
    String message =
        "Hello, "
            + user.getName()
            + " "
            + user.getSurname()
            + "\nYou can use that code to validate your account: "
            + otp
            + "\n \nDon't forget that the code is expired after 2 minutes";
    return Email.builder().to(user.getEmail()).subject("Email Validation").body(message).build();
  }
}
