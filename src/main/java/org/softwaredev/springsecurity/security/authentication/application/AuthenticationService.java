package org.softwaredev.springsecurity.security.authentication.application;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.validation.Valid;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.softwaredev.springsecurity.common.application.DateTimeUtilService;
import org.softwaredev.springsecurity.common.application.EmailService;
import org.softwaredev.springsecurity.common.application.OTPService;
import org.softwaredev.springsecurity.common.domain.http.ApiResponse;
import org.softwaredev.springsecurity.employeePosition.application.EmployeePositionService;
import org.softwaredev.springsecurity.employeePosition.domain.domain.EmployeePosition;
import org.softwaredev.springsecurity.requestedDayOfPermission.domain.entity.RequestedDayOfPermission;
import org.softwaredev.springsecurity.requestedDayOfPermission.repository.RequestedDayOfPermissionsRepository;
import org.softwaredev.springsecurity.reviewedPermission.domain.entity.PermissionStatus;
import org.softwaredev.springsecurity.reviewedPermission.domain.entity.ReviewedPermission;
import org.softwaredev.springsecurity.reviewedPermission.repository.ReviewedPermissionRepository;
import org.softwaredev.springsecurity.security.authentication.domain.http.*;
import org.softwaredev.springsecurity.security.authentication.exceptions.*;
import org.softwaredev.springsecurity.user.application.UserService;
import org.softwaredev.springsecurity.user.domain.entity.User;
import org.softwaredev.springsecurity.user.repository.UserRepository;
import org.softwaredev.springsecurity.user.userSetting.application.UserSettingService;
import org.softwaredev.springsecurity.user.userSetting.domain.entity.UserSetting;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private final UserRepository userRepository;
  private final OTPService otpService;
  private final EmailService emailService;
  private final UserSettingService userSettingService;
  private final UserService userService;
  private final JWTService jwtService;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final DateTimeUtilService dateTimeUtilService;
  private final RequestedDayOfPermissionsRepository requestedDayOfPermissionsRepository;
  private final ReviewedPermissionRepository reviewedPermissionRepository;
  private final EmployeePositionService employeePositionService;

  public ResponseEntity<ApiResponse<RegisterResponse>> register(RegisterRequest registerRequest) {
    Optional<User> optionalUser = userRepository.findByEmail(registerRequest.email());
    if (optionalUser.isPresent()) {
      throw new AlreadyRegisteredUserException(
          "That email " + registerRequest.email() + " is already registered");
    }

    EmployeePosition employeePosition =
        employeePositionService.findById(registerRequest.employeePositionId());

    User user =
        User.builder()
            .name(registerRequest.name())
            .surname(registerRequest.surname())
            .password(passwordEncoder.encode(registerRequest.password()))
            .email(registerRequest.email())
            .role(registerRequest.role())
            .permissions(new ArrayList<>())
            .employeeStartDate(
                dateTimeUtilService.convertOnlyDateToDateObject(
                    registerRequest.employeeStartDate()))
            .employeePosition(employeePosition)
            .createdAt(new Date())
            .lastModifiedAt(new Date())
            .build();

    // TODO Send OTP Email to User
    // String otp = otpService.createOTP();
    // emailService.sendOTP(user, otp);

    UserSetting userSetting =
        UserSetting.builder()
            // .otp(otp)
            .emailVerified(true)
            // .otpCreatedTime(new Date())
            .build();
    userSetting = userSettingService.saveUserSetting(userSetting);

    user.setUserSetting(userSetting);
    user = userRepository.save(user);

    RequestedDayOfPermission requestedDayOfPermission =
        RequestedDayOfPermission.builder()
            .createdAt(
                dateTimeUtilService.convertOnlyDateToDateObject(
                    registerRequest.requestedDayOfPermissionRequest().createdAt()))
            .from(
                dateTimeUtilService.convertOnlyDateToDateObject(
                    registerRequest.requestedDayOfPermissionRequest().from()))
            .to(
                dateTimeUtilService.convertOnlyDateToDateObject(
                    registerRequest.requestedDayOfPermissionRequest().to()))
            .user(user)
            .description(registerRequest.requestedDayOfPermissionRequest().description())
            .build();

    requestedDayOfPermission = requestedDayOfPermissionsRepository.save(requestedDayOfPermission);

    List<RequestedDayOfPermission> requestedDayOfPermissions = new ArrayList<>();
    requestedDayOfPermissions.add(requestedDayOfPermission);
    // Create reviewed
    ReviewedPermission reviewedPermission =
        ReviewedPermission.builder()
            .user(user)
            .requestedDayOfPermissions(requestedDayOfPermissions)
            .status(PermissionStatus.PENDING)
            .usage(registerRequest.reviewedPermissionRequest().usage())
            .remaining(registerRequest.reviewedPermissionRequest().remaining())
            .createdAt(new Date())
            .lastModifiedAt(new Date())
            .build();

    reviewedPermission = reviewedPermissionRepository.save(reviewedPermission);

    return new ResponseEntity<>(
        new ApiResponse<>(
            RegisterResponse.builder().message("User Created").build(),
            "success",
            200,
            true,
            new Date()),
        HttpStatus.OK);
  }

  public ResponseEntity<ApiResponse<EmailValidationResponse>> validateEmail(
      EmailValidationRequest emailValidationRequest) {
    User user = userService.findByEmail(emailValidationRequest.email());
    UserSetting userSetting = user.getUserSetting();

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(userSetting.getOtpCreatedTime());
    calendar.add(Calendar.MINUTE, 5);
    Date expirationDate = calendar.getTime();

    if (expirationDate.before(new Date())) {
      String otp = otpService.createOTP();
      emailService.sendOTP(user, otp);
      userSetting.setOtpCreatedTime(new Date());
      userSetting.setOtp(otp);
      userSetting = userSettingService.saveUserSetting(userSetting);
      throw new ExpiredOTPException("Expired OTP. Email is sent again!");
    }

    if (!emailValidationRequest.otp().equals(userSetting.getOtp())) {
      throw new InvalidOTPException("Invalid OTP");
    }

    userSetting.setOtpCreatedTime(null);
    userSetting.setOtp(null);
    userSetting.setEmailVerified(true);

    userSetting = userSettingService.saveUserSetting(userSetting);

    return new ResponseEntity<>(
        new ApiResponse<>(
            EmailValidationResponse.builder().validated(true).build(),
            "success",
            200,
            true,
            new Date()),
        HttpStatus.OK);
  }

  public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid LoginRequest loginRequest) {
    User user = userService.findByEmail(loginRequest.email());
    UserSetting userSetting = user.getUserSetting();
    if (!userSetting.isEmailVerified()) {
      throw new EmailNotValidatedException("Email is not validated");
    }

    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginRequest.email(), loginRequest.password()));

    String token = jwtService.generateToken(user);

    return new ResponseEntity<>(
        new ApiResponse<>(
            LoginResponse.builder().accessToken(token).build(), "success", 200, true, new Date()),
        HttpStatus.OK);
  }

  public ResponseEntity<ApiResponse<String>> validateAccessToken(HttpHeaders httpHeaders) {
    try {
      String email = jwtService.extractEmailFromHeader(httpHeaders);
      Optional<User> optionalUser = userService.findOptionalByEmail(email);
      if (optionalUser.isPresent()) {
        return new ResponseEntity<>(
            new ApiResponse<>("Valid Access Token", "success", 200, true, new Date()),
            HttpStatus.OK);
      }
      throw new InvalidAccessTokenException("Invalid Access Token");

    } catch (ExpiredJwtException exception) {
      throw new CustomExpiredJwtException("Expired JWT");
    } catch (JwtException exception) {
      throw new CustomJwtException("Invalid JWT");
    }
  }

  public ResponseEntity<ApiResponse<List<RegisterResponse>>> multipleRegister(
      List<RegisterRequest> registerRequest) {
    return null;
  }
}
