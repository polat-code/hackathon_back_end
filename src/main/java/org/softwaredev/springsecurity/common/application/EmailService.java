package org.softwaredev.springsecurity.common.application;

import lombok.RequiredArgsConstructor;
import org.softwaredev.springsecurity.common.domain.entity.Email;
import org.softwaredev.springsecurity.user.domain.entity.User;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

  private final JavaMailSender mailSender;
  private final EmailHelperService emailHelperService;

  //@Async
  public void sendOTP(User user, String otp) {
    Email email = emailHelperService.getRegister(user, otp);
    sendEmail(email);
  }

  //@Async
  public void sendEmail(Email email) {
    try {
      SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
      simpleMailMessage.setTo(email.getTo());
      simpleMailMessage.setSubject(email.getSubject());
      simpleMailMessage.setText(email.getBody());
      simpleMailMessage.setFrom("iytechli.email@gmail.com");
      mailSender.send(simpleMailMessage);
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
    }
  }

  /*
  @Async
  public void sendForgetPasswordOTP(User user, String otp) {
    Email email = emailHelperService.getRefreshPasswordOTPEmailObject(user, otp);
    sendEmail(email
  }

   */
}
