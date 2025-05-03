package org.softwaredev.springsecurity.security.authentication.application;

import lombok.RequiredArgsConstructor;
import org.softwaredev.springsecurity.common.application.EmailService;
import org.softwaredev.springsecurity.common.application.OTPService;
import org.softwaredev.springsecurity.common.domain.http.ApiResponse;
import org.softwaredev.springsecurity.security.authentication.domain.http.RegisterRequest;
import org.softwaredev.springsecurity.security.authentication.domain.http.RegisterResponse;
import org.softwaredev.springsecurity.security.authentication.exceptions.AlreadyRegisteredUserException;
import org.softwaredev.springsecurity.user.domain.entity.Role;
import org.softwaredev.springsecurity.user.domain.entity.User;
import org.softwaredev.springsecurity.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private final UserRepository userRepository;
  private final OTPService otpService;
  private final EmailService emailService;

  public ResponseEntity<ApiResponse<RegisterResponse>> register(RegisterRequest registerRequest) {
    Optional<User> optionalUser = userRepository.findByEmail(registerRequest.email());
    if (optionalUser.isPresent()) {
      throw new AlreadyRegisteredUserException(
          "That email " + registerRequest.email() + " is already registered");
    }
    User user =
        User.builder()
            .name(registerRequest.name())
            .surname(registerRequest.surname())
            .password(registerRequest.password())
            .email(registerRequest.email())
            .role(Role.USER)
            .permissions(new ArrayList<>())
            .createdAt(new Date())
            .lastModifiedAt(new Date())
            .build();

    user = userRepository.save(user);

    // TODO Send OTP Email to User
    String otp = otpService.createOTP();
    emailService.sendOTP(user, otp);

    return new ResponseEntity<>(
        new ApiResponse<>(
            RegisterResponse.builder().message("Email Validation").build(),
            "success",
            200,
            true,
            new Date()),
        HttpStatus.OK);
  }
}
