package org.softwaredev.springsecurity.common.application;

import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OTPService {

  @Value("${application.security.otp.otp_expiration}")
  private long otpExpiration;

  public String createOTP(int length) {
    // Create a SecureRandom instance for generating secure random numbers
    SecureRandom random = new SecureRandom();

    // StringBuilder to store the OTP
    StringBuilder sb = new StringBuilder(length);

    for (int i = 0; i < length; i++) {
      // Generate a random number from 0 to 9
      int randomNumber = random.nextInt(10);
      sb.append(randomNumber);
    }

    return sb.toString();
  }

  public String createOTP() {
    return createOTP(6);
  }

  public boolean isOTPExpired(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.MILLISECOND, (int) otpExpiration);
    return new Date().after(calendar.getTime());
  }
}
